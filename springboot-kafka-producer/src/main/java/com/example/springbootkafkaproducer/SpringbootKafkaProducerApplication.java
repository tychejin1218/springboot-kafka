package com.example.springbootkafkaproducer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class SpringbootKafkaProducerApplication implements CommandLineRunner {

  private static String TOPIC_NAME = "test";

  private final KafkaTemplate<String, String> customKafkaTemplate;

  public SpringbootKafkaProducerApplication(
      KafkaTemplate<String, String> customKafkaTemplate) {
    this.customKafkaTemplate = customKafkaTemplate;
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringbootKafkaProducerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 10; i++) {
      customKafkaTemplate.send(TOPIC_NAME, "test" + i);
    }
    System.exit(0);
  }
}
