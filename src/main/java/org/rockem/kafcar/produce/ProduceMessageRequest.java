package org.rockem.kafcar.produce;

import com.fasterxml.jackson.databind.util.JSONPObject;
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
