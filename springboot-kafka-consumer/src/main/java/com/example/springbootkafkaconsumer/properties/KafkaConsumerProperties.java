package com.example.springbootkafkaconsumer.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class KafkaConsumerProperties {

  @Value("${kafka.topic}")
  private String topic;

  @Value("${kafka.consumer.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafka.consumer.key-serializer}")
  private Class<?> keySerializer;

  @Value("${kafka.consumer.value-serializer}")
  private Class<?> valueSerializer;

  @Value("${kafka.consumer.enable-auto-commit-config}")
  private boolean enableAutoCommitConfig;

  @Value("${kafka.consumer.auto-offset-reset-config}")
  private String autoOffSetReset;

  @Value("${kafka.consumer.allow-auto-create-topics}")
  private boolean allowAutoCreateTopics;

  @Value("${kafka.consumer.security-protocol-config}")
  private String securityProtocolConfig;

  @Value("${kafka.consumer.client-id}")
  private String clientId;

  @Value("${kafka.consumer.group-id}")
  private String groupId;

  @Value("${kafka.consumer.sasl-mechanism}")
  private String saslMechanism;

  @Value("${kafka.consumer.username}")
  private String username;

  @Value("${kafka.consumer.password}")
  private String password;

  @Value("${kafka.consumer.ack-mode}")
  private String ackMode;

  @Value("${kafka.consumer.concurrency}")
  private int concurrency;

  @Value("${kafka.consumer.poll-timeout}")
  private int pollTimeout;
}
