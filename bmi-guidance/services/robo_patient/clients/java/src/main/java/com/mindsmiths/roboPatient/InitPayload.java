package com.mindsmiths.roboPatient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitPayload implements Serializable {
    private String chatId;
    private Integer age, height;
    private Double lowWeight, highWeight;
}
