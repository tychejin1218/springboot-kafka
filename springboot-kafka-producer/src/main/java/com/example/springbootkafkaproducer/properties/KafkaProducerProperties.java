package com.example.springbootkafkaproducer.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class KafkaProducerProperties {

  @Value("${kafka.topic}")
  private String topic;

  @Value("${kafka.producer.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafka.producer.key-serializer}")
  private Class<?> keySerializer;

  @Value("${kafka.producer.value-serializer}")
  private Class<?> valueSerializer;

  @Value("${kafka.producer.ack-config}")
  private String ackConfig;

  @Value("${kafka.producer.security-protocol-config}")
  private String securityProtocolConfig;

  @Value("${kafka.producer.sasl-mechanism}")
  private String saslMechanism;

  @Value("${kafka.producer.username}")
  private String username;

  @Value("${kafka.producer.password}")
  private String password;

  @Value("${kafka.producer.client-id}")
  private String clientId;
}
