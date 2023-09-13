#Dockerfile 
#Usa una imagen base que tenga las herramientas y dependencias necesarias.
FROM ubuntu:latest

# Instala las dependencias necesarias.
RUN apt-get update && \
    apt-get install -y default-jdk && \
    apt-get install -y bwa && \
    apt-get install -y samtools

# Establece el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copia los archivos necesarios al contenedor.
COPY data /app/data
COPY hg38 /app/hg38
COPY tools /app/tools
COPY scripts /app/scripts

# Otorga permisos de ejecución a los scripts.
RUN chmod +x /app/scripts/gatk_mock.sh

# Ejecuta el script cuando el contenedor se inicie.
CMD ["./scripts/gatk_mock.sh"]
