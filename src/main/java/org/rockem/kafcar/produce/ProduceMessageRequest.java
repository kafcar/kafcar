package org.rockem.kafcar.produce;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ProduceMessageRequest {

    private String topic;
    private Map<String, Object> value;
}
