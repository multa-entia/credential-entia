package ru.multa.entia.credential;

public class Temp {
/*

    @Test
    void run() throws InterruptedException {
        Thread producerThread = new Thread(() -> {
            Properties properties = new Properties();
            properties.put(
                    "bootstrap.servers",
                    "localhost:9092,localhost:9093,localhost:9094"
            );
            properties.put(
                    "key.serializer",
                    "org.apache.kafka.common.serialization.StringSerializer"
            );
            properties.put(
                    "value.serializer",
                    "org.apache.kafka.common.serialization.StringSerializer"
            );

            new HelloWorldProducer(properties).run();
        });

        Thread consumerThread = new Thread(() -> {
            Properties properties = new Properties();
            properties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
            properties.put("group.id", "kinaction_helloconsumer");
            properties.put("enable.auto.commit", "true");
            properties.put("auto.commit.interval.ms", "1000");
            properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

            HelloWorldConsumer consumer = new HelloWorldConsumer(properties);
//            Runtime.getRuntime().addShutdownHook(new Thread(consumer::shutdown));
            consumer.consume();
            Runtime.getRuntime().addShutdownHook(new Thread(consumer::shutdown));
        });

        producerThread.start();
        consumerThread.start();

        Thread.sleep(5_000);
    }

-----------------------------

package kpn.kafka_java_spring;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Address {
    private String country;
    private String city;
    private String street;
    private Long homeNumber;
    private Long flatNumber;
}

----------------------------

package kpn.kafka_java_spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Consumer {

    private static final String TOPIC_NAME = "msg";

    private final AtomicBoolean keepConsuming = new AtomicBoolean(true);
    private final Map<String, Object> properties;

    public Consumer(final Map<String, Object> properties) {
        this.properties = properties;
    }

    public void start() {
        Runnable r = () -> {
            try(KafkaConsumer<Long, UserDto> consumer = new KafkaConsumer<>(properties)){
                consumer.subscribe(List.of(TOPIC_NAME));

                while (keepConsuming.get()){
                    ConsumerRecords<Long, UserDto> records = consumer.poll(Duration.ofMillis(250));
                    for (ConsumerRecord<Long, UserDto> record : records) {
                        log.info(" +-+ {}", record);
                    }
                }
            }
        };

        // TODO: 10.06.2023 !!!
        System.out.println("---------------");
        new Thread(r).start();
        System.out.println("++++++++++++++++++");
    }

    @PreDestroy
    public void shutdown() {
        keepConsuming.set(false);
    }

    //
//    public class ASyncCommit {
//
//        private static final Logger log = LoggerFactory.getLogger(ASyncCommit.class);
//        private static final String TOPIC_NAME = "kinaction_views";
//
//        private volatile boolean keepConsuming = true;
//
//        public static void main(String[] args) {
//
//            final ASyncCommit aSyncCommit = new ASyncCommit();
//            aSyncCommit.consume(properties);
//            Runtime.getRuntime().addShutdownHook(new Thread(aSyncCommit::shutdown));
//        }
//
//        private void consume(Properties properties){
//            try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)){
//                consumer.assign(List.of(
//                        new TopicPartition(TOPIC_NAME, 1),
//                        new TopicPartition(TOPIC_NAME, 2)
//                ));
//
//                while (keepConsuming){

//                }
//            }
//        }
//
//        public static void commitOffset(long offset, int partition, String topic, KafkaConsumer<String, String> consumer){
//            OffsetAndMetadata offsetMeta = new OffsetAndMetadata(++offset, "");
//            HashMap<TopicPartition, OffsetAndMetadata> offsetMap = new HashMap<>() {{
//                put(new TopicPartition(topic, partition), offsetMeta);
//            }};
//            consumer.commitAsync(offsetMap, (map, e) -> {
//                if (e != null){
//                    for (TopicPartition key : map.keySet()) {
//                        log.info("kinaction_error topic {}, offset {}", key.topic(), map.get(key).offset());
//                    }
//                } else {
//                    for (TopicPartition key : map.keySet()) {
//                        log.info("kinaction_info topic {}, offset {}", key.topic(), map.get(key).offset());
//                    }
//                }
//            });
//        }
//
//        private void shutdown(){
//            keepConsuming = false;
//        }
//    }


}


-----------------------------------

package kpn.kafka_java_spring;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

//    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer = "localhost:9092";

    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Long, UserDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, UserDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Consumer consumer(){
        Consumer consumer = new Consumer(consumerConfigs());
        consumer.start();
        return consumer;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        System.out.println(" ---- kafkaServer: " + kafkaServer + ", kafkaGroupId: " + kafkaGroupId);
        return new HashMap<>(){{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        }};
    }
}

-----------------------------------

package kpn.kafka_java_spring;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@SpringBootApplication
public class KafkaJavaSpringApplication {

	// TODO: 08.05.2023 move into sep. class
//	@KafkaListener(topics = "msg")
//	public void msgListener(final String msg) {
//		System.out.println("msgListener: " + msg);
//	}

	// TODO: 10.06.2023 !!!
//	@KafkaListener(topics = "msg")
//	public void orderListener(ConsumerRecord<Long, UserDto> record){
//		System.out.println(record.partition());
//		System.out.println(record.key());
//		System.out.println(record.value());
//	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaJavaSpringApplication.class, args);
	}

}
----------------------------------------

package kpn.kafka_java_spring;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

// 0
@Configuration
public class KafkaProducerConfig {

    private final String kafkaServer = "localhost:9092";

    @Bean
    public Map<String, Object> producerConfigs(){
        return new HashMap<>(){{
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        }};
    }

    @Bean
    public ProducerFactory<Long, UserDto> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, UserDto> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}


----------------------------------


package kpn.kafka_java_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("msg")
public final class MsgController {

    // 3
    @Autowired
    private KafkaTemplate<Long, UserDto> kafkaTemplate;

    @PostMapping
    public void sendMsg(Long msgId, final UserDto msg){

        Address address = new Address("R", "M", "V", 4L, 1L);
        UserDto userDto = new UserDto();
        userDto.setAddress(address);
        userDto.setName("some.name");
        userDto.setAge(33L);

        msgId = (long) new Random().nextInt();

        System.out.println("MsgController::sendOrder ID: " + msgId + ", MSG: " + userDto);
        ListenableFuture<SendResult<Long, UserDto>> future = kafkaTemplate.send("msg", msgId, userDto);
        future.addCallback(this::printSuccess, this::printFail);
        kafkaTemplate.flush();
    }

    public void printSuccess(final Object value){
        System.out.println("+++++");
        System.out.println(value);
        System.out.println("+++++");
    }

    public void printFail(final Object value){
        System.out.println("-----");
        System.out.println(value);
        System.out.println("-----");
    }

//    // 2
//    @Autowired
//    private KafkaTemplate<Long, String> kafkaTemplate;
//
//    @PostMapping
//    public void sendMsg(final Long msgId, final String msg){
//        System.out.println("MsgController::sendOrder ID: " + msgId + ", MSG: " + msg);
//        ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send("msg", msgId, msg);
//        future.addCallback(this::printSuccess, this::printFail);
//        kafkaTemplate.flush();
//    }
//
//    public void printSuccess(final Object value){
//        System.out.println("+++++");
//        System.out.println(value);
//        System.out.println("+++++");
//    }
//
//    public void printFail(final Object value){
//        System.out.println("-----");
//        System.out.println(value);
//        System.out.println("-----");
//    }


//    // 1
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @PostMapping
//    public void sendMsg(final String msgId, final String msg){
//        System.out.println("MsgController::sendOrder ID: " + msgId + ", MSG: " + msg);
//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("msg", msgId, msg);
//        future.addCallback(this::printSuccess, this::printFail);
//        kafkaTemplate.flush();
//    }
//
//    public void printSuccess(final Object value){
//        System.out.println("+++++");
//        System.out.println(value);
//        System.out.println("+++++");
//    }
//
//    public void printFail(final Object value){
//        System.out.println("-----");
//        System.out.println(value);
//        System.out.println("-----");
//    }

    // 0
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//    @PostMapping
//    public void sendOrder(final String msgId, final String msg){
//        System.out.println("MsgController::sendOrder ID: " + msgId + ", MSG: " + msg);
//        kafkaTemplate.send("msg", msgId, msg);
//    }
}

-----------------------------

package kpn.kafka_java_spring;

import lombok.Data;

@Data
public final class UserDto {
    private Long age;
    private String name;
    private Address address;
}



 */
}
