package com.uob.mathpuzzle.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameException extends RuntimeException{

    private int status;
    private String message;

    public GameException(String msg) {
        super(msg);
        this.message = msg;
    }

    public GameException(int status, String msg) {
        super(msg);
        this.status = status;
        this.message = msg;
    }

}
