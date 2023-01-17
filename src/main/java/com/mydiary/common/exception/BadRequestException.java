package com.mydiary.common.exception;

public class BadRequestException extends RuntimeException {


    private static final long serialVersionUID = 3368511469034514704L;

    public BadRequestException() {
        super("잘못된 요청입니다.");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
