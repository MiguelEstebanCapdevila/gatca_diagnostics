package com.api.gatca.services;

import com.api.gatca.models.entities.Analysis;
import com.api.gatca.models.enums.AnalysisStatus;
import com.api.gatca.models.request.AnalysisRequest;

import java.util.List;

public interface AnalysisService {
    public Analysis create(AnalysisRequest analysis);
    public Analysis delete(int id);

    public Analysis processAnalysis();

    public List<Analysis> getAll();

    public Analysis update(Analysis analysis);
}
