package com.api.gatca.models.entities;

import com.api.gatca.models.enums.AnalysisStatus;
import com.api.gatca.models.enums.AnalysisType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "analysis")
public class Analysis
 {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     @Column(name = "patient_name")
     private String patientName;

     @Enumerated(EnumType.STRING)
     private AnalysisType type;

     private Date date;

     @Enumerated(EnumType.STRING)
     private AnalysisStatus status;

     @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL)
     @JsonManagedReference // This annotation indicates the "owning" side of the relationship
     @Builder.Default
     private List<AnalysisFiles> analysisFiles = new ArrayList<>();

}