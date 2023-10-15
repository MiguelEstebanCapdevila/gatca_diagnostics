package com.api.gatca.controllers;


import com.api.gatca.models.AnalysisData;
import com.api.gatca.models.entities.Analysis;
import com.api.gatca.models.request.AnalysisRequest;
import com.api.gatca.services.KafkaProducerService;
import com.api.gatca.services.impl.AnalysisServiceImpl;
import com.api.gatca.services.impl.KafkaProducerServiceAnalysisImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final KafkaProducerServiceAnalysisImpl kafkaProducerServiceAnalysisImpl;
    private final AnalysisServiceImpl analysisService;
    public AnalysisController(KafkaProducerService<Analysis> kafkaProducerService, AnalysisServiceImpl analysisService){
        this.kafkaProducerServiceAnalysisImpl = (KafkaProducerServiceAnalysisImpl) kafkaProducerService;
        this.analysisService = analysisService;
    }

    //TODO esto va a ser un chrono y va a poder ser también invocable desde un controller
    //pero pasando el id del analysis
   /* @PostMapping("/process")
    public ResponseEntity<AnalysisData> processAnalysis(@RequestBody AnalysisData analysisData) {
        try {
            kafkaProducerServiceAnalysisImpl.sendAnalysisData(analysisData);
            return new ResponseEntity<>(analysisData, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PostMapping("/create")
    public ResponseEntity<Analysis> createAnalysis(@RequestBody  AnalysisRequest analysisRequest){
        try {
            var analysisCreated = analysisService.create(analysisRequest);
            if(analysisCreated != null) return new ResponseEntity<>(analysisCreated, HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Analysis> deleteAnalysis(int analysisId){
        try {
            var analysisDeleted = analysisService.delete(analysisId);
            if(analysisDeleted != null) new ResponseEntity<>(analysisDeleted, HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }


}
