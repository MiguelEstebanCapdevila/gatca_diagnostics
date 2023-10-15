FROM broadinstitute/gatk:latest


# Update package list and install samtools
RUN apt-get update && apt-get install -y samtools && apt-get install -y bwa && apt-get install -y openjdk-8-jre  && apt-get install -y fastqc




# Add the SNPeff directory to the PATH
ENV PATH="/app/snpEff:${PATH}"
# Set the SNPeff database path (replace with your desired database)
ENV SNPEFF_HOME=/app/snpEff
ENV SNPEFF_DATABASE=/app/data
# Create a directory for SNPeff data (for databases)


# Download and install Picard Tools (replace the URL with the desired Picard Tools version)
ADD https://github.com/broadinstitute/picard/releases/download/3.1.0/picard.jar /usr/local/bin/picard.jar

# Set an environment variable to specify the Java executable
ENV JAVA_CMD java



ENV APPS_ROOT /app
RUN mkdir -p ${APPS_ROOT}
RUN mkdir /app/data



#RUN mkdir ${APPS_ROOT}/reference_data
#RUN ls ${APPS_ROOT}/reference_data

#COPY container_alive.sh ${APPS_ROOT}
#THIS ONLY TO MAINTAIN ALIVE THE CONTAINER
# Set execute permission on the script
#RUN chmod +x ${APPS_ROOT}/container_alive.sh
# Run the empty script when the container starts
#CMD ["/app/container_alive.sh"]

#wrapper SH TEST
COPY wrapper.sh ${APPS_ROOT}
RUN chmod +x ${APPS_ROOT}/wrapper.sh 
#preparation
COPY preparation-test.sh ${APPS_ROOT}
RUN chmod +x ${APPS_ROOT}/preparation-test.sh 
#pipeline
COPY germline_variants_pipeline-test.sh ${APPS_ROOT}
RUN chmod +x ${APPS_ROOT}/germline_variants_pipeline-test.sh 
CMD ["/app/wrapper.sh"]

#test replica esto
# Create a directory to store reference data if it doesn't exist
#mkdir -p reference_data

# Move 'hg38.fa' to the reference_data directory
#mv hg38.fa reference_data/

# Navigate to the reference_data directory
#cd reference_data

## creating index for fasta file

#samtools faidx GRCh38.fa 





###############################################
#using https://github.com/bwa-mem2/bwa-mem2 release 'bwa-mem2-2.2.1_x64-linux' 
#RUN mkdir -p ${APPS_ROOT}/bwa
#COPY  bwa-mem2-2.2.1_x64-linux ${APPS_ROOT}/bwa


###############################################
#COPY FASTQ, JUST FOR TESTING, USING VOLUME IS BETTER

#COPY 54309S1_1.fastq  ${APPS_ROOT}/data


#ENV BWA_VERSION 0.7.17
#ENV BWA_HOME ${APPS_ROOT}/bwa/${BWA_VERSION}
#ENV PATH ${BWA_HOME}:${PATH}


