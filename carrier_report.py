import pandas as pd
import matplotlib.pyplot as plt
from Bio import SeqIO
import pandas as pd




fastq_file = "ruta/del/archivo.fastq"
records = SeqIO.parse(fastq_file, "fastq")

for record in records:
    sequence_id = record.id
    sequence = str(record.seq)
    
    # Realiza las operaciones necesarias con los datos de la secuencia
    # para el diagnóstico de carrier test
    # ...

def realizar_diagnostico(sequence):
    # Realiza el diagnóstico de carrier test con la secuencia dada
    # ...
    return resultado_diagnostico

for record in records:
    sequence_id = record.id
    sequence = str(record.seq)
    
    resultado_diagnostico = realizar_diagnostico(sequence)
    
    # Agrega el resultado al informe
    # ...


# Crear un DataFrame para almacenar los resultados
informe = pd.DataFrame(columns=["ID", "Diagnóstico"])

for record in records:
    sequence_id = record.id
    sequence = str(record.seq)
    
    resultado_diagnostico = realizar_diagnostico(sequence)
    
    # Agregar los resultados al DataFrame
    informe = informe.append({"ID": sequence_id, "Diagnóstico": resultado_diagnostico}, ignore_index=True)

# Guardar el informe en un archivo CSV
informe.to_csv("ruta/del/informe.csv", index=False)









# Datos de ejemplo
informe = pd.DataFrame({
    "ID": ["Secuencia1", "Secuencia2", "Secuencia3", "Secuencia4"],
    "Diagnóstico": ["Portador", "No portador", "Portador", "No portador"]
})

# Generar gráfico de barras
plt.figure(figsize=(8, 6))
informe["Diagnóstico"].value_counts().plot(kind="bar", color="skyblue")
plt.title("Carrier Test - Resultados")
plt.xlabel("Diagnóstico")
plt.ylabel("Cantidad")
plt.xticks(rotation=0)
plt.tight_layout()
plt.show()

# Guardar informe en archivo CSV
informe.to_csv("ruta/del/informe.csv", index=False)



