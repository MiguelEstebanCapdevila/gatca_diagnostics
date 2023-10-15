#!/bin/bash

# This script automates the setup of reference genome (hg38) and database for snpEff analysis.

## Download hg38 from NCBI

# Download hg38 genome from NCBI
# Link: https://www.ncbi.nlm.nih.gov/datasets/genome/GCF_000001405.26/
# GENBANK only
# Place the downloaded 'hg38.fa' file in the same directory as this script.

## Create reference dir and move fasta file into it

# Create a directory to store reference data if it doesn't exist
mkdir -p reference_data

# Move 'hg38.fa' to the reference_data directory
mv hg38.fa reference_data/

# Navigate to the reference_data directory
cd reference_data

## creating index for fasta file

samtools faidx GRCh38.fa 

## create bwa index

# Create BWA index for hg38.fa
bwa index hg38.fa

## create dictionary: Picard

# Create a sequence dictionary using Picard tools
java -jar picard.jar CreateSequenceDictionary \
  R=hg38.fa \
  O=hg38.dict

## Download database for snpeff

# Define the location of snpEff home directory (ensure $snpeff_home is set)
snpeff_home=/path/to/snpEff

# Download and install the GRCh38.p13 database for snpEff
java -jar $snpeff_home/snpEff.jar download -v GRCh38.13
