package com.api.gatca.schedulingtasks;

import com.api.gatca.models.entities.Analysis;
import com.api.gatca.models.enums.AnalysisStatus;
import com.api.gatca.services.AnalysisService;
import com.api.gatca.services.KafkaProducerService;
import com.api.gatca.services.ReportService;
import com.api.gatca.services.impl.AnalysisServiceImpl;
import com.api.gatca.services.impl.KafkaProducerServiceAnalysisImpl;
import com.api.gatca.services.impl.ReportServiceImpl;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class AnalysisProcess {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AnalysisProcess.class);
    private final ReportServiceImpl reportService;
    private final AnalysisServiceImpl analysisService;
    private final KafkaProducerServiceAnalysisImpl kafkaProducerServiceAnalysisImpl;
    public AnalysisProcess(AnalysisService analysisService, KafkaProducerService<Analysis> kafkaProducerService,
                           ReportService reportService){
        this.analysisService = (AnalysisServiceImpl) analysisService;
        this.kafkaProducerServiceAnalysisImpl = (KafkaProducerServiceAnalysisImpl) kafkaProducerService;
        this.reportService = (ReportServiceImpl) reportService;
    }

    @Scheduled(fixedRate = 5000)
    public void processAnalysis() {
        /*
        States of analysis:
        CREATED: status created when new analysis created, enqueue kafka message and start docker to try to process the message
        FINISHED: pass, everything is finished.
        PROCESSING: Do nothing, analysis is in process
        PROCESSED: kafka processor receive a message in analysis queue, we need to create the report
        ERROR: kafka processor receive a message in error_analysis queue
         */

        //retrieve all analysis
        var analysisList = analysisService.getAll().stream()
                .filter(a -> a.getStatus() != AnalysisStatus.ERROR &&
                        a.getStatus() != AnalysisStatus.PROCESSING  &&
                        a.getStatus() != AnalysisStatus.FINISHED )
                .toList();

        //for each created enqueue message to kafka

        analysisList.stream().filter(a -> a.getStatus() == AnalysisStatus.CREATED)
                .forEach(a -> {
                    kafkaProducerServiceAnalysisImpl.sendMessageToTopic(a,"analysis_queue");
                    a.setStatus(AnalysisStatus.PROCESSING);
                    a.setDate(new Date());
                    analysisService.update(a);
                });

        //for each processed
        analysisList.stream().filter(a -> a.getStatus() == AnalysisStatus.PROCESSED)
                .forEach(a -> {
                    try {
                        reportService.generateReport(a);
                    } catch (DocumentException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });

    }
}
