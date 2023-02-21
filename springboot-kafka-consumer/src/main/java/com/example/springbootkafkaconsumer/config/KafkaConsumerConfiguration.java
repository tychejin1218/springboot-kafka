package com.example.springbootkafkaconsumer.config;

import com.example.springbootkafkaconsumer.properties.KafkaConsumerProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Slf4j
@AllArgsConstructor
@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

  private final KafkaConsumerProperties kafkaConsumerProperties;

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {

    String ip = "";
    try {
      ip = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      log.error("UnknownHostException", e);
    }

    String clientIdConfig = kafkaConsumerProperties.getClientId() + ":" + ip;
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        kafkaConsumerProperties.getBootstrapServers());
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        kafkaConsumerProperties.getKeySerializer());
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        kafkaConsumerProperties.getValueSerializer());
    configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
        kafkaConsumerProperties.isEnableAutoCommitConfig());
    configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
        kafkaConsumerProperties.getAutoOffSetReset());
    configProps.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG,
        kafkaConsumerProperties.isAllowAutoCreateTopics());
    configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
        kafkaConsumerProperties.getSecurityProtocolConfig());
    configProps.put(ConsumerConfig.CLIENT_ID_CONFIG, clientIdConfig);
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.getGroupId());

    if (!SecurityProtocol.PLAINTEXT.name.equals(
        kafkaConsumerProperties.getSecurityProtocolConfig())
        && kafkaConsumerProperties.getSecurityProtocolConfig().startsWith("SASL")) {
      final String jassTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule "
          + "required username='%s' password='%s';";
      configProps.put(SaslConfigs.SASL_MECHANISM, kafkaConsumerProperties.getSaslMechanism());
      configProps.put(SaslConfigs.SASL_JAAS_CONFIG,
          String.format(jassTemplate, kafkaConsumerProperties.getUsername(),
              kafkaConsumerProperties.getPassword()));
    }

    return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
        new StringDeserializer());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(consumerFactory());
    factory.getContainerProperties()
        .setAckMode(ContainerProperties.AckMode.valueOf(kafkaConsumerProperties.getAckMode()));

    factory.setBatchListener(false);

    if (kafkaConsumerProperties.getConcurrency() > 0) {
      factory.setConcurrency(kafkaConsumerProperties.getConcurrency());
    }

    if (kafkaConsumerProperties.getPollTimeout() > 0) {
      factory.getContainerProperties()
          .setPollTimeout(kafkaConsumerProperties.getPollTimeout());
    }

    return factory;
  }
}
