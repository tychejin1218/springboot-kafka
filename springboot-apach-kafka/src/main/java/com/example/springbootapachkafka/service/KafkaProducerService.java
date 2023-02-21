package com.example.springbootapachkafka.service;

import com.example.springbootapachkafka.peroperties.KafkaProducerProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

  private final KafkaProducerProperties kafkaProducerProperties;

  public void sendData(String topic, String data) {

    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBootstrapServers());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getKeySerializer());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getValueSerializer());
    props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerProperties.getAckConfig()); // 0, 1, all(-1)
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
        kafkaProducerProperties.getSecurityProtocolConfig());

    try (Producer<String, String> producer = new KafkaProducer<>(props)) {
      if (null != producer) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
        producer.send(record);
      }
    } catch (IllegalStateException e) {
      log.error("sendData data:[{}]", data, e);
    }
  }
}
