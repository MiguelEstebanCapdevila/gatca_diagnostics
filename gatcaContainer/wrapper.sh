#!/bin/bash

# Execute the first script
${APPS_ROOT}/preparation-test.sh

# Execute the second script
${APPS_ROOT}/germline_variants_pipeline-test.sh /app/data/54309S1.fastq