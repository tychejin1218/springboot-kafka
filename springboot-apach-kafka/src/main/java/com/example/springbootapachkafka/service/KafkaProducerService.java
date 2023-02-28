package com.example.springbootapachkafka.service;

import com.example.springbootapachkafka.common.config.KafkaCallback;
import com.example.springbootapachkafka.peroperties.KafkaProducerProperties;
import java.util.List;
import java.util.Properties;
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

  private final KafkaProducer<String, String> kafkaProducerBean;

  private final KafkaProducerProperties kafkaProducerProperties;

  /**
   * KafkaProducer를 빈으로 등록했을 때
   */
  public void sendDataBeanRegistration(String topic, String data) {
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
    kafkaProducerBean.send(record, new KafkaCallback());
  }

  /**
   * KafkaProducer를 빈으로 등록하지 않을 때
   */
  public void sendDataBeanNotRegistration(String topic, String data) {

    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        kafkaProducerProperties.getBootstrapServers());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        kafkaProducerProperties.getKeySerializer());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        kafkaProducerProperties.getValueSerializer());
    props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerProperties.getAckConfig());
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
        kafkaProducerProperties.getSecurityProtocolConfig());

    try (Producer<String, String> producer = new KafkaProducer<>(props)) {

      ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
      producer.send(record, new KafkaCallback());

    } catch (Exception e) {
      log.error("sendData:[{}]", data, e);
    }
  }

  /**
   * KafkaProducer를 빈으로 등록하지 않을 때
   */
  public void sendDatasBeanNotRegistration(String topic, List<String> datas) {

    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        kafkaProducerProperties.getBootstrapServers());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        kafkaProducerProperties.getKeySerializer());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        kafkaProducerProperties.getValueSerializer());
    props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerProperties.getAckConfig());
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
        kafkaProducerProperties.getSecurityProtocolConfig());

    try (Producer<String, String> producer = new KafkaProducer<>(props)) {
      for (String data : datas) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
        producer.send(record, new KafkaCallback());
      }
    } catch (Exception e) {
      log.error("sendData:[{}]", datas.toString(), e);
    }
  }
}
