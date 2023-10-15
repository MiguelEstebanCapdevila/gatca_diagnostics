package com.api.gatca.services.impl;

import com.api.gatca.models.AnalysisData;
import com.api.gatca.models.entities.Analysis;
import com.api.gatca.services.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceAnalysisImpl implements KafkaProducerService<Analysis> {

    private final KafkaTemplate<String, AnalysisData> kafkaTemplate;
    //TODO move to properties to be able to change it whitout rebuilding
    private static final String TOPIC_NAME = "analysis";

    @Autowired
    public KafkaProducerServiceAnalysisImpl(KafkaTemplate<String, AnalysisData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    @Override
    public void sendMessageToTopic(Analysis message, String topic) {

    }
}
