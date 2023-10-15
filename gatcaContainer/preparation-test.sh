#!/bin/bash



# Navigate to the reference_data directory
cd /app/reference_data

## creating index for fasta file

#samtools faidx GRCh38.fa 

# Create BWA index for hg38.fa
#bwa index GRCh38.fa

#/usr/local/bin/picard.jar
# Create a sequence dictionary using Picard tools
#java -jar /usr/local/bin/picard.jar CreateSequenceDictionary -R /app/reference_data/GRCh38.fa -O /app/reference_data/GRCh38.dict


# Download and unzip SNPeff
#curl -v -L 'https://snpeff.blob.core.windows.net/versions/snpEff_latest_core.zip' > snpEff_latest_core.zip
#unzip snpEff_latest_core.zip

# Run SNPeff's download command
#sometimes fails we should see how to retry if fails its a connection problem not related to us
#java -jar snpEff/snpEff.jar download -v GRCh38.p13
#java -jar snpEff/snpEff.jar databases


#snpEff download -v GRCh38.13