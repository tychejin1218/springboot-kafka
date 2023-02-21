package com.example.springbootapachkafka.service;

import com.example.springbootapachkafka.common.config.KafkaCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

  private final KafkaProducer<String, String> kafkaProducer;

  public void sendData(String topic, String data) {
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
    kafkaProducer.send(record, new KafkaCallback());
  }
}
