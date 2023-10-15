package com.api.gatca.services;

import com.api.gatca.models.AnalysisData;

public interface KafkaProducerService<T> {
    //void sendAnalysisData(AnalysisData message);
    void sendMessageToTopic(T message, String topic);
}
