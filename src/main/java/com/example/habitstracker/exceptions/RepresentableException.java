package com.example.habitstracker.exceptions;

/**
 * Исключение, которое можно сконвертировать в dto и отправить в ответе на клиент
 */
public abstract class RepresentableException extends RuntimeException {
    private final ErrorCodes code;

    /**
     * @param info Информация об ошибке
     * @param code Код ошибки
     */
    public RepresentableException(String info, ErrorCodes code) {
        super(info);
        this.code = code;
    }

    public String getCode() {
        return code.getCode();
    }
}
