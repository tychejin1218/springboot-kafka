package com.example.springbootapachkafka.common.config;

import com.example.springbootapachkafka.peroperties.KafkaProducerProperties;
import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@AllArgsConstructor
@Configuration
public class KafkaProducerConfig {

  private final KafkaProducerProperties kafkaProducerProperties;

  @Bean
  public KafkaProducer<String, String> kafkaProducerBean() {

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

    return new KafkaProducer<>(props);
  }
}
