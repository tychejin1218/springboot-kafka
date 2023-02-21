package com.example.springbootapachkafka.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.util.ObjectUtils;

@Slf4j
public class KafkaCallback implements Callback {

  @Override
  public void onCompletion(RecordMetadata metadata, Exception exception) {
    if (!ObjectUtils.isEmpty(metadata)) {
      log.info("onCompletion partition:{}, offset:{}", metadata.partition(), metadata.offset());
    } else {
      log.error("onCompletion partition:{}, offset:{}", metadata.partition(), metadata.offset(),
          exception);
    }
  }
}
