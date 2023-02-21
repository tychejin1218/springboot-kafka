package com.example.springbootapachkafka.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.springbootapachkafka.peroperties.KafkaProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class KafkaServiceTest {

  @Autowired
  private KafkaProducerService kafkaProducerService;

  @Autowired
  private KafkaProducerProperties kafkaProducerProperties;

  @DisplayName("sendData_Kafka Producer 전송 테스트")
  @Test
  void testSendData() {

    String topic = kafkaProducerProperties.getTopic();
    String data = "Hello Kafka!";

    long startTime = System.currentTimeMillis();
    log.info("Start Time={}", startTime);

    for (int a = 0; a < 1000; a++) {
      kafkaProducerService.sendData(topic, data);
    }

    long currentTime = System.currentTimeMillis();
    log.info("End Time={}", currentTime);
    log.info("Time Taken={}", (currentTime - startTime) / 1000);
  }
}
