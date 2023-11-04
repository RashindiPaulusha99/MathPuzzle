package com.uob.mathpuzzle.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommonResponseDTO<T> {

    private boolean success;
    private String message;
    private T body;

    public CommonResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CommonResponseDTO(boolean success, String message, T body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }

    public CommonResponseDTO(boolean success, T body) {
        this.success = success;
        this.body = body;
    }
}



