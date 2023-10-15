#!/bin/bash

# Check if the input file argument is provided
if [ $# -lt 1 ]; then
    echo "Usage: $0 <input_file>"
    exit 1
fi

# Input file path provided as the first argument
input_file="$1"

# Extract the sample name from the input file basename
sample_name=$(basename "$input_file" | cut -d '_' -f 1)

# Define paths and variables
reference_data_dir="/app/reference_data"
snpeff_home="/app/snpEff"

# Alignment: BWA - MEM
echo "Step 1: BWA alignment has started for sample ${sample_name}."
bwa_mem_command="bwa mem -K 100000000 -Y -R \"@RG\\tID:${sample_name}\\tLB:${sample_name}\\tPL:illumina\\tPM:nextseq\\tSM:${sample_name}\" ${reference_data_dir}/GRCh38.fa ${input_file} > ${sample_name}_aligned.sam"
eval $bwa_mem_command

# Sort + Duplicates: Picard - MarkDuplicatesSpark
echo "Step 2: Picard MarkDuplicatesSpark has started for sample ${sample_name}."
mark_duplicates_command="gatk MarkDuplicatesSpark -I ${sample_name}_aligned.sam -M ${sample_name}_dedup_metrics.txt -O ${sample_name}_sorted_dedup.bam"
eval $mark_duplicates_command

# Alignment metrics: GATK4, samtools
echo "Step 3: Alignment metrics calculation has started for sample ${sample_name}."
alignment_metrics_command="gatk MarkDuplicatesSpark -I ${sample_name}_sorted_dedup.bam -O ${sample_name}_alignment_metrics.txt"
eval $alignment_metrics_command

insert_size_metrics_command="gatk CollectInsertSizeMetrics -I ${sample_name}_sorted_dedup.bam -O ${sample_name}_insert_metrics.txt -H ${sample_name}_insert_size_histogram.pdf"
eval $insert_size_metrics_command

samtools_depth_command="samtools depth -a ${sample_name}_sorted_dedup.bam > ${sample_name}_depth_out.txt"
eval $samtools_depth_command

# Call variants: GATK4 - HaplotypeCaller
echo "Step 4: HaplotypeCaller has started for sample ${sample_name}."
haplotype_caller_command="gatk HaplotypeCaller -R reference_data/GRCh38.fa -I ${sample_name}_sorted_dedup.bam -O ${sample_name}_raw_variants_1.vcf"
eval $haplotype_caller_command

# Split SNPs and INDELs: GATK4
echo "Step 5: Variants selection has started for sample ${sample_name}."
split_snps_command="gatk SelectVariants -R reference_data/GRCh38.fa -V ${sample_name}_raw_variants_1.vcf -select-type SNP -O ${sample_name}_raw_snps_1.vcf"
eval $split_snps_command

split_indels_command="gatk SelectVariants -R reference_data/GRCh38.fa -V ${sample_name}_raw_variants_1.vcf -select-type INDEL -O ${sample_name}_raw_indels_1.vcf"
eval $split_indels_command

# Filter SNPs: GATK4
echo "Step 6: SNP filtering has started for sample ${sample_name}."
filter_snps_command="gatk VariantFiltration -R reference_data/GRCh38.fa -V ${sample_name}_raw_snps_1.vcf -O ${sample_name}_filtered_snps_1.csv.vcf -filter-name 'QD_filter' -filter 'QD < 2.0' -filter-name 'FS_filter' -filter 'FS > 60.0' -filter-name 'MQ_filter' -filter 'MQ < 40.0' -filter-name 'SOR_filter' -filter 'SOR > 4.0' -filter-name 'MQRankSum_filter' -filter 'MQRankSum < -12.5' -filter-name 'ReadPosRankSum_filter' -filter 'ReadPosRankSum < -8.0'"
eval $filter_snps_command

# Filter INDELs: GATK4
echo "Step 7: INDELS filtering has started for sample ${sample_name}."
filter_indels_command="gatk VariantFiltration -R reference_data/GRCh38.fa -V ${sample_name}_raw_indels_1.vcf -O ${sample_name}_filtered_indels_1.csv.vcf -filter-name 'QD_filter' -filter 'QD < 2.0' -filter-name 'FS_filter' -filter 'FS > 200.0' -filter-name 'SOR_filter' -filter 'SOR > 10.0'"
eval $filter_indels_command

# Remove filtered variants: GATK4
echo "Step 8: Removal of filtered varians has started for sample ${sample_name}."
exclude_filtered_snps_command="gatk SelectVariants --exclude-filtered -O ${sample_name}_bqsr_snps_1.vcf -V ${sample_name}_filtered_snps_1.csv.vcf"
eval $exclude_filtered_snps_command

exclude_filtered_indels_command="gatk SelectVariants --exclude-filtered -O ${sample_name}_bqsr_indels_1.vcf -V ${sample_name}_filtered_indels_1.csv.vcf"
eval $exclude_filtered_indels_command

# Base Quality Score Recalibration (BSQR): GATK4
echo "Step 9: BSQR has started for sample ${sample_name}."
base_recalibrator_command="gatk BaseRecalibrator -R reference_data/GRCh38.fa -I ${sample_name}_sorted_dedup.bam --known-sites ${sample_name}_bqsr_snps_1.vcf --known-sites ${sample_name}_bqsr_indels_1.vcf -O ${sample_name}_recal_data.table"
eval $base_recalibrator_command

# Apply BSQR: GATK4
echo "Step 10: Apply BSQR has started for sample ${sample_name}."
apply_bqsr_command="gatk ApplyBQSR -R reference_data/GRCh38.fa -I ${sample_name}_sorted_dedup.bam -bqsr ${sample_name}_recal_data.table -O ${sample_name}_recal_reads.bam"
eval $apply_bqsr_command

# Base Quality Score Recalibration #2 (BSQR2): GATK4
echo "Step 11: ROUND2 BSQR has started for sample ${sample_name}."
base_recalibrator2_command="gatk BaseRecalibrator -R reference_data/GRCh38.fa -I ${sample_name}_recal_reads.bam --known-sites ${sample_name}_bqsr_snps_1.vcf --known-sites ${sample_name}_bqsr_indels_1.vcf -O ${sample_name}_post_recal_data.table"
eval $base_recalibrator2_command

# Covariates report
echo "Step 12: Covariates analysis has started for sample ${sample_name}."
analyze_covariates_command="gatk AnalyzeCovariates -before ${sample_name}_recal_data.table -after ${sample_name}_post_recal_data.table -plots ${sample_name}_recal_plot.pdf"
eval $analyze_covariates_command

## Call Variants #2: GATK4 - HaplotypeCaller
echo "Step 13: ROUND2 HaplotypeCaller has started for sample ${sample_name}."
haplotype_caller2_command="gatk HaplotypeCaller -R reference_data/GRCh38.fa -I ${sample_name}_recal_reads.bam -O ${sample_name}_raw_variants_2_recal.vcf"
eval $haplotype_caller2_command

# Split SNPs and INDELs #2: GATK4
echo "Step 14: ROUND2 variants selection has started for sample ${sample_name}."
split_snps2_command="gatk SelectVariants -R reference_data/GRCh38.fa -V ${sample_name}_raw_variants_2_recal.vcf -select-type SNP -O ${sample_name}_raw_snps_2_recal.vcf"
eval $split_snps2_command

split_indels2_command="gatk SelectVariants -R reference_data/GRCh38.fa -V ${sample_name}_raw_variants_2_recal.vcf -select-type INDEL -O ${sample_name}_raw_indels_2_recal.vcf"
eval $split_indels2_command

# Filter SNPs #2: GATK4
echo "Step 15: ROUND2 SNPs filtering has started for sample ${sample_name}."
filter_snps2_command="gatk VariantFiltration -R reference_data/GRCh38.fa -V ${sample_name}_raw_snps_2_recal.vcf -O ${sample_name}_filtered_snps_2.vcf -filter-name 'QD_filter' -filter 'QD < 2.0' -filter-name 'FS_filter' -filter 'FS > 60.0' -filter-name 'MQ_filter' -filter 'MQ < 40.0' -filter-name 'SOR_filter' -filter 'SOR > 4.0' -filter-name 'MQRankSum_filter' -filter 'MQRankSum < -12.5' -filter-name 'ReadPosRankSum_filter' -filter 'ReadPosRankSum < -8.0'"
eval $filter_snps2_command

# Filter INDELs #2: GATK4
echo "Step 16: ROUND2 INDELs filtering has started for sample ${sample_name}."
filter_indels2_command="gatk VariantFiltration -R reference_data/GRCh38.fa -V ${sample_name}_raw_indels_2_recal.vcf -O ${sample_name}_filtered_indels_2.vcf -filter-name 'QD_filter' -filter 'QD < 2.0' -filter-name 'FS_filter' -filter 'FS > 200.0' -filter-name 'SOR_filter' -filter 'SOR > 10.0'"
eval $filter_indels2_command

# Annotation #1: snpEFF
echo "Step 17: HaplotypeCaller has started for sample ${sample_name}."
snp_eff_command="java -jar ${snpeff_home}/snpEff.jar -v GRCh38.13 ${sample_name}_filtered_snps_2.vcf > ${sample_name}_filtered_snps_annotated.vcf"
eval $snp_eff_command

echo "Analysis for ${sample_name} completed."



