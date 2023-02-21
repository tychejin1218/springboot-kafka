package com.example.springbootkafkaconsumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerListener implements AcknowledgingMessageListener<String, String> {

  @Override
  @KafkaListener(
      topics = "${kafka.topic}",
      groupId = "${kafka.consumer.group-id}",
      containerFactory = "kafkaListenerContainerFactory")
  public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
    log.info("onMessage data:[{}]", data.toString());
    acknowledgment.acknowledge();
  }
}
