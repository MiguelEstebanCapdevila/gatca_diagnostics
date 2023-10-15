package com.api.gatca.services.impl;

import com.api.gatca.models.entities.Analysis;
import com.api.gatca.models.entities.AnalysisFiles;
import com.api.gatca.models.enums.AnalysisStatus;
import com.api.gatca.models.request.AnalysisRequest;
import com.api.gatca.repositories.AnalysisRepository;
import com.api.gatca.services.AnalysisService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;

    //TODO add validations
    @Override
    public Analysis create(AnalysisRequest analysisRequest) {
        try{
            var newAnalysis = new Analysis();
            newAnalysis.setDate(new Date());
            newAnalysis.setPatientName(analysisRequest.getPatientName());
            newAnalysis.setType(analysisRequest.getType());
            newAnalysis.setStatus(AnalysisStatus.CREATED);

            // Create a new list to hold the AnalysisFiles
            List<AnalysisFiles> analysisFilesList = new ArrayList<>();

            for (AnalysisFiles fileRequest : analysisRequest.getAnalysisFiles()) {
                AnalysisFiles analysisFile = new AnalysisFiles();
                analysisFile.setUrl(fileRequest.getUrl());
                analysisFile.setType(fileRequest.getType());
                analysisFile.setAnalysis(newAnalysis); // Set the analysis for each file

                analysisFilesList.add(analysisFile);
            }

            // Set the AnalysisFiles list in the Analysis
            newAnalysis.setAnalysisFiles(analysisFilesList);


            return analysisRepository.save(newAnalysis);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Analysis delete(int id) {
        try {
            var analysisExist = analysisRepository.findById((long) id);
            analysisExist.ifPresent(analysisRepository::delete);
            return analysisExist.orElseThrow();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Analysis update(Analysis analysis) {
        // Check if the analysis with the given ID exists
        Optional<Analysis> optionalAnalysis = analysisRepository.findById(analysis.getId());

        if (optionalAnalysis.isPresent()) {
            // Update the existing analysis with the new data
            Analysis existingAnalysis = optionalAnalysis.get();
            existingAnalysis.setStatus(analysis.getStatus()); // Update any other fields as needed

            // Save the updated analysis to the database
            return analysisRepository.save(existingAnalysis);
        } else {
            // Handle the case where the analysis with the given ID doesn't exist
            throw new EntityNotFoundException("Analysis with ID " + analysis.getId() + " not found");
        }
    }


    @Override
    public Analysis processAnalysis() {
        return null;
    }

    @Override
    public List<Analysis> getAll() {
       return analysisRepository.findAll();
    }




}
