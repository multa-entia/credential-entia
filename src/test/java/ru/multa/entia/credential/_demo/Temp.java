package ru.multa.entia.credential._demo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import ru.multa.entia.fakers.impl.Faker;

import java.util.concurrent.atomic.AtomicReference;

public class Temp {

    @SneakyThrows
    @Test
    void start() {

        AtomicReference<kConsumer> reference = new AtomicReference<>();

        Runnable p = () -> {
            KafkaTemplate<Long, kUserDTO> template = new kKafkaProducerConfig().kafkaTemplate();
            kMsgController controller = new kMsgController(template);

            for (int i = 0; i < 2; i++) {
                controller.send(Faker.long_().random(), kUserDTO.random());
            }
        };

        Runnable c = () -> {
            kKafkaConsumerConfig config = new kKafkaConsumerConfig();

            KafkaListenerContainerFactory<?> factory = config.kafkaListenerContainerFactory();
            kConsumer consumer = config.createAndStartConsumer();
            reference.set(consumer);
        };

        new Thread(c).start();
        new Thread(p).start();

        Thread.sleep(1_000);

        System.out.println("counter: " + reference.get().getCounter());
    }



    // TODO: del
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

----------------------------




-----------------------------------



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
---------------------------------


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





 */
}
