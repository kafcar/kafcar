package org.rockem.kafcar.produce;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ProduceMessageRequest {

    private String topic;
    private String value;
}
