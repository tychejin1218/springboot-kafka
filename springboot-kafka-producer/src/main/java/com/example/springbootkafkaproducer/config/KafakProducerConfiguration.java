package com.example.springbootkafkaproducer.config;

import com.example.springbootkafkaproducer.properties.KafkaProducerProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Slf4j
@AllArgsConstructor
@Configuration
public class KafakProducerConfiguration {

  private final KafkaProducerProperties kafkaProducerProperties;

  @Bean
  public ProducerFactory<String, String> producerFactory() {

    String ip = "";
    try {
      ip = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      log.error("UnknownHostException", e);
    }

    String clientIdConfig = kafkaProducerProperties.getClientId() + ":" + ip;
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        kafkaProducerProperties.getBootstrapServers());
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        kafkaProducerProperties.getKeySerializer());
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        kafkaProducerProperties.getValueSerializer());
    configProps.put(ProducerConfig.ACKS_CONFIG,
        kafkaProducerProperties.getAckConfig());
    configProps.put(ProducerConfig.CLIENT_ID_CONFIG, clientIdConfig);
    configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
        kafkaProducerProperties.getSecurityProtocolConfig());
    if (!SecurityProtocol.PLAINTEXT.name.equals(
        kafkaProducerProperties.getSecurityProtocolConfig())
        && kafkaProducerProperties.getSecurityProtocolConfig().startsWith("SASL")) {
      final String jassTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule "
          + "required username='%s' password='%s';";
      configProps.put(SaslConfigs.SASL_MECHANISM, kafkaProducerProperties.getSaslMechanism());
      configProps.put(SaslConfigs.SASL_JAAS_CONFIG,
          String.format(jassTemplate, kafkaProducerProperties.getUsername(),
              kafkaProducerProperties.getPassword()));
    }

    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
