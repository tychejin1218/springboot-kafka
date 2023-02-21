package com.example.springbootkafkaproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public void sendData(String topic, String data) {

    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        log.info("onSuccess data[{}], result:[{}]", data, result.getRecordMetadata().offset());
      }

      @Override
      public void onFailure(Throwable e) {
        log.error("onFailure data:[{}]", data, e);
      }
    });
  }
}
