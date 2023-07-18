# First have installed BWA, GATK, and Picard

# Step 1: Align reads to reference genome using BWA
bwa mem -M -t 4 reference.fasta input.fastq > aligned.sam

# Step 2: Convert SAM to BAM and sort
samtools view -bS aligned.sam | samtools sort -o sorted.bam -

# Step 3: Mark duplicates using Picard
java -jar picard.jar MarkDuplicates I=sorted.bam O=dedup.bam M=metrics.txt

# Step 4: Index the BAM file
samtools index dedup.bam

# Step 5: Perform indel realignment using GATK
java -jar GenomeAnalysisTK.jar -T RealignerTargetCreator -R 
reference.fasta -I dedup.bam -o targets.intervals
java -jar GenomeAnalysisTK.jar -T IndelRealigner -R reference.fasta -I 
dedup.bam -targetIntervals targets.intervals -o realigned.bam

# Step 6: Perform base-quality recalibration using GATK
java -jar GenomeAnalysisTK.jar -T BaseRecalibrator -R reference.fasta -I 
realigned.bam -knownSites dbsnp.vcf -o recal_data.table
java -jar GenomeAnalysisTK.jar -T PrintReads -R reference.fasta -I 
realigned.bam -BQSR recal_data.table -o recalibrated.bam

# Step 7: Call variants using GATK UnifiedGenotyper
java -jar GenomeAnalysisTK.jar -T UnifiedGenotyper -R reference.fasta -I 
input.bam -o raw_variants.vcf

# Step 8: Perform variant annotation using GATK VariantAnnotation
java -jar GenomeAnalysisTK.jar -T VariantAnnotator -R reference.fasta -V 
raw_variants.vcf -o annotated_variants.vcf

# Step 9: Filter low-quality variants using GATK VariantFiltration
java -jar GenomeAnalysisTK.jar -T VariantFiltration -R reference.fasta -V 
annotated_variants.vcf -o filtered_variants.vcf --filterExpression "QUAL < 
100.0 || QD < 5.0" --filterName "LOW_QUALITY"

# Step 10: Perform further annotation using SeattleSeqAnnotation
java -jar SeattleSeqAnnotation.jar -annotateVariants -v 
filtered_variants.vcf -dbase cache_database -includeDbSNP -includeClinical 
-includePolyPhen -includeGERP -includeCADD -includeGrantham 
-includeProteinInteractions -includeMicroRNAs -includeNHLBI -includeExAC 
-o annotated_filtered_variants.vcf
