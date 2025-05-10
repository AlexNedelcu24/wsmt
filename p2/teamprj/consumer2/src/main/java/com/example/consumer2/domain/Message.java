package com.example.consumer2.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 202505101L;

    @JsonProperty
    private String operation;

    @JsonProperty
    private String payload;

    @Override
    public String toString() {
        return "Message{" +
                "operation='" + operation + '\'' +
                ", message='" + payload + '\'' +
                '}';
    }
}
