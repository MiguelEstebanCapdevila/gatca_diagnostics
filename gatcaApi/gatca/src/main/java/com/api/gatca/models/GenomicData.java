package com.api.gatca.models;

import lombok.*;

import java.io.Serializable;

//@Data  // Generates getters, setters, toString, equals, and hashCode methods
//@AllArgsConstructor  // Generates an all-args constructor
//@NoArgsConstructor  // Generates a no-args constructor
public class GenomicData implements Serializable {

     public GenomicData(int chromosoma, int position, String reference, String alternance, int quality) {
          this.chromosoma = chromosoma;
          this.position = position;
          this.reference = reference;
          this.alternance = alternance;
          this.quality = quality;
     }

     public GenomicData() {
     }
     public int chromosoma;
     public int position;

     public String reference;

     public String alternance;

     public int quality;

     public int getChromosoma() {
          return chromosoma;
     }

     public int getPosition() {
          return position;
     }

     public String getReference() {
          return reference;
     }

     public String getAlternance() {
          return alternance;
     }

     public int getQuality() {
          return quality;
     }

     public void setChromosoma(int chromosoma) {
          this.chromosoma = chromosoma;
     }

     public void setPosition(int position) {
          this.position = position;
     }

     public void setReference(String reference) {
          this.reference = reference;
     }

     public void setAlternance(String alternance) {
          this.alternance = alternance;
     }

     public void setQuality(int quality) {
          this.quality = quality;
     }
}
