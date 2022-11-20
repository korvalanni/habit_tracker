package com.example.habitstracker.exceptions;

import com.example.habitstracker.constants.ExceptionTextConstants;

/**
 * Использовать в случае, когда не смогли найти нужный маппер
 */
public class MapperNotFound extends RepresentableException {
    /**
     * @param typeFrom Имя типа из которого делаем преобразование
     * @param typeTo   Имя типа в который делаем преобразование
     */
    public MapperNotFound(String typeFrom, String typeTo) {
        super(ExceptionTextConstants.MAPPER_NOT_FOUND.formatted(typeFrom, typeTo), ErrorCodes.MAPPER_NOT_FOUND);
    }
}
