package com.api.gatca.models.entities;


import com.api.gatca.models.enums.AnalysisFileType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "analysis_files")
public class AnalysisFiles
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;

    @Enumerated(EnumType.STRING)
    private AnalysisFileType type;

    @ManyToOne
    @JoinColumn(name = "analysis_id")
    @JsonBackReference // This annotation prevents serialization of the "analysis" field
    private Analysis analysis;



}
