package com.api.gatca.models.request;

import com.api.gatca.models.entities.AnalysisFiles;
import com.api.gatca.models.enums.AnalysisType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRequest {

    private String patientName;
    private AnalysisType type;
    @Builder.Default
    private List<AnalysisFiles> analysisFiles = new ArrayList<>();

}
