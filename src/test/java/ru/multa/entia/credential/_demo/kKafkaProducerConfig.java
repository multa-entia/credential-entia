package ru.multa.entia.credential._demo;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class kKafkaProducerConfig {

    private final static String KAFKA_SERVER = "localhost:9092,localhost:9093,localhost:9094";

    public Map<String, Object> producerConfig() {
        return new HashMap<>(){{
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        }};
    }

    public ProducerFactory<Long, kUserDTO> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    public KafkaTemplate<Long, kUserDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
