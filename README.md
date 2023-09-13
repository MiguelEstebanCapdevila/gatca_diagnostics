# gatca_diagnostics
This code analyze and create reports for genetic tests
In this code, we are going to deploy a pipeline, which is able to compute a FASTQ file an transform it in a complete understandable report useful for genetists. 
We are going to be able to compute locally different reports. 
We will start with carrier test. For computing carrier test we have to execute with pythob the follow scripts 
For the testing we are going to use the follow sra-explorer acession 


K8S 

for creating a cluster on GKE on must to deploy the follow scrips 
gcloud container clusters get-credentials autopilot-cluster-1 --zone us-central1 --project mercurial-bruin-268216
kubectl apply -f k8s-config.yaml
kubectl exec -it nombre-pod -- /bin/bash -c "/app/run.sh"

DOCKERFILE

docker tag pipeline_mock miguelestebancapdevila/pipeline_mock:v1
for updating Dockerfile image in GCP:
docker build -t gcr.io/mercurial-bruin-268216/pipeline_mock:latest .
gcloud auth configure-docker
docker push gcr.io/mercurial-bruin-268216/pipeline_mock:latest
kubectl set image deployment/secuencing-deployment secuencing-container=gcr.io/mercurial-bruin-268216/pipeline_mock:latest

Be aware to eliminate the current image if you are not versioning them in GCP and in local: 
gcloud container images delete gcr.io/mercurial-bruin-268216/pipeline_mock:latest
docker image rm gcr.io/mercurial-bruin-268216/pipeline_mock:latest


To unpack the *.tar.gz files:
    tar xvzf <file>.tar.gz
To uncompress the fa.gz files:
    gunzip <file>.fa.gz


REDUCE SAMPLE 
If the sample is too big and one can not compute the file. Try to reduce the file with the following scripts:
head -20000 SRR23019314_Whole_exome_sequence_of_a_patient_with_Harlequin_Ichthyosis_1.fastq > test_1.fastq
gzip SRR23019314_Whole_exome_sequence_of_a_patient_with_Harlequin_Ichthyosis_1.fastq



CHECK THE NUMBER OF LINES 
wc -l SRR*
