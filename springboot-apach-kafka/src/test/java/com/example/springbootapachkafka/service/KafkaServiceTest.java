package com.example.springbootapachkafka.service;

import com.example.springbootapachkafka.peroperties.KafkaProducerProperties;
import java.util.Arrays;
import java.util.List;
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

  @DisplayName("sendDataBeanRegistration_KafkaProducer를 빈으로 등록했을 때 전송 테스트")
  @Test
  void testSendDataBeanRegistration() {

    String topic = kafkaProducerProperties.getTopic();
    String data = "Hello Kafka!";

    long startTime = System.currentTimeMillis();
    log.info("Start Time={}", startTime);

    for (int a = 0; a < 1000; a++) {
      kafkaProducerService.sendDataBeanRegistration(topic, data);
    }

    long currentTime = System.currentTimeMillis();
    log.info("End Time={}", currentTime);
    log.info("Time Taken={}", (currentTime - startTime) / 1000);
  }

  @DisplayName("sendDataBeanNotRegistration_KafkaProducer를 빈으로 등록하지 않을 때 전송 테스트")
  @Test
  void testSendDataBeanNotRegistration() {

    String topic = kafkaProducerProperties.getTopic();
    String data = "Hello Kafka!";

    long startTime = System.currentTimeMillis();
    log.info("Start Time={}", startTime);

    for (int a = 0; a < 1000; a++) {
      kafkaProducerService.sendDataBeanNotRegistration(topic, data);
    }

    long currentTime = System.currentTimeMillis();
    log.info("End Time={}", currentTime);
    log.info("Time Taken={}", (currentTime - startTime) / 1000);
  }

  @DisplayName("sendDatasBeanNotRegistration_KafkaProducer를 빈으로 등록하지 않을 때 전송 테스트")
  @Test
  void testSendDatasBeanNotRegistration() {

    String topic = kafkaProducerProperties.getTopic();
    List<String> datas = Arrays.asList(
        "Hello Kafka!", "Hello Kafka!", "Hello Kafka!", "Hello Kafka!", "Hello Kafka!"
        , "Hello Kafka!", "Hello Kafka!", "Hello Kafka!", "Hello Kafka!", "Hello Kafka!");

    long startTime = System.currentTimeMillis();
    log.info("Start Time={}", startTime);

    for (int a = 0; a < 100; a++) {
      kafkaProducerService.sendDatasBeanNotRegistration(topic, datas);
    }

    long currentTime = System.currentTimeMillis();
    log.info("End Time={}", currentTime);
    log.info("Time Taken={}", (currentTime - startTime) / 1000);
  }
}
