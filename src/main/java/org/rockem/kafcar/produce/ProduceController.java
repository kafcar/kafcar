package org.rockem.kafcar.produce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProduceController {

    private transient final KafkaTemplate<String, String> kafkaTemplate;
    private transient final ObjectMapper objectMapper = new ObjectMapper();

    public ProduceController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/api/v1/produce")
    public void produceMessage(@RequestBody ProduceMessageRequest request) throws JsonProcessingException {
        kafkaTemplate.send(request.getTopic(), objectMapper.writeValueAsString(request.getValue()));
    }
}
