package com.mydiary.common.exception;

public class NotFoundException extends RuntimeException {


    private static final long serialVersionUID = -1212148892627857512L;

    public NotFoundException() {
        super("존재하지 않는 페이지입니다.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
