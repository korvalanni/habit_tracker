package com.example.habitstracker.exceptions;

/**
 * Использовать в случае, когда не смогли найти нужный маппер
 */
public class MapperNotFound extends RepresentableException {
    /**
     * @param typeFrom Имя типа из которого делаем преобразование
     * @param typeTo   Имя типа в который делаем преобразование
     */
    public MapperNotFound(String typeFrom, String typeTo) {
        super("Mapper not found! Can't map from type " + typeFrom + " to type " + typeTo, ErrorCodes.MAPPER_NOT_FOUND);
    }
}
