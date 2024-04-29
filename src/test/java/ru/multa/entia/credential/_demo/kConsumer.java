package ru.multa.entia.credential._demo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class kConsumer {

    public static final String TOPIC_NAME = "test-topic";

    private final AtomicBoolean keepConsuming = new AtomicBoolean(true);
    private final Map<String, Object> properties;

    @Getter
    private int counter;

    public kConsumer(final Map<String, Object> properties) {
        this.properties = properties;
    }

    public void start () {
        Runnable r = () -> {
            try(KafkaConsumer<Long, kUserDTO> consumer = new KafkaConsumer<>(properties)) {
                consumer.subscribe(List.of(TOPIC_NAME));
                while (keepConsuming.get()) {
                    ConsumerRecords<Long, kUserDTO> records = consumer.poll(Duration.ofMillis(250));
                    System.out.println("!!! : " + records);
                    if (!records.isEmpty()){
                        for (ConsumerRecord<Long, kUserDTO> record : records) {
                            System.out.println("### " + record);
                            counter++;
//                            commitOffset(record.offset(), record.partition(), TOPIC_NAME, consumer);
//                            consumer.commitAsync(this::callback);
                        }
                    }
                }
            }
        };
        new Thread(r).start();
    }

    private void callback(Map<TopicPartition, OffsetAndMetadata> topicPartitionOffsetAndMetadataMap, Exception e) {
        System.out.println(topicPartitionOffsetAndMetadataMap);
        System.out.println(e == null ? "" : e.toString());
    }

    //    @PreDestroy
    public void shutdown() {
        keepConsuming.set(false);
    }

    public static void commitOffset(long offset, int partition, String topic, KafkaConsumer<Long, kUserDTO> consumer){
        OffsetAndMetadata offsetMeta = new OffsetAndMetadata(++offset, "");
        HashMap<TopicPartition, OffsetAndMetadata> offsetMap = new HashMap<>() {{
            put(new TopicPartition(topic, partition), offsetMeta);
        }};
        consumer.commitAsync(offsetMap, (map, e) -> {
            if (e != null){
                for (TopicPartition key : map.keySet()) {
                    log.info("kinaction_error topic {}, offset {}", key.topic(), map.get(key).offset());
                }
            } else {
                for (TopicPartition key : map.keySet()) {
                    log.info("kinaction_info topic {}, offset {}", key.topic(), map.get(key).offset());
                }
            }
        });
    }
}
