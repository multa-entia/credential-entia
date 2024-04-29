package ru.multa.entia.credential._demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.multa.entia.fakers.impl.Faker;

import java.util.HashMap;
import java.util.Map;

public class kKafkaConsumerConfig {

    private static final String KAFKA_SERVER = "localhost:9092,localhost:9093,localhost:9094";
//    private static final String KAFKA_SERVER = "localhost:9092";
    private static final String GROUP_ID = "test-group-";

    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, kUserDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    public ConsumerFactory<Long, kUserDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    public kConsumer createAndStartConsumer() {
        kConsumer consumer = new kConsumer(consumerConfigs());
        consumer.start();
        return consumer;
    }

    public Map<String, Object> consumerConfigs() {
        return new HashMap<>(){{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID + Faker.str_().random());
//            put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//            put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
//            put(ConsumerConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 1000);
        }};
    }
}
