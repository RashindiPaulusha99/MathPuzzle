package com.uob.mathpuzzle.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorMessageResponseDTO {
    private boolean success;
    private int status;
    private String msg;
}