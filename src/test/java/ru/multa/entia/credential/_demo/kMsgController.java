package ru.multa.entia.credential._demo;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

public class kMsgController {

    private final KafkaTemplate<Long, kUserDTO> template;

    public kMsgController(KafkaTemplate<Long, kUserDTO> template) {
        this.template = template;
    }

    public void send(final Long id, final kUserDTO msg) {
        template
                .send(kConsumer.TOPIC_NAME, id, msg)
                .thenAccept(this::onSuccess)
                .exceptionally(this::onError)
                .thenRun(this::onRun);
        template.flush();
    }

    private void onSuccess(SendResult<Long, kUserDTO> longkUserDTOSendResult) {
        System.out.println("ON SUCCESS " + longkUserDTOSendResult);
    }

    private Void onError(Throwable throwable) {
        System.out.println("ON ERROR");
        return null;
    }

    private void onRun() {
        System.out.println("ON RUN");
    }
}
