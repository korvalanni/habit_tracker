package com.example.habitstracker.mappers;

/**
 * Базовый класс для всех конвертеров
 *
 * @param <F> Тип из которого надо конвертировать объект
 * @param <T> Тип в который надо конвертировать объект
 */
public abstract class Mapper<F, T> {
    private final Class<F> fromType;
    private final Class<T> toType;

    /**
     * @param fromType Дескриптор типа из которого надо конвертировать объект
     * @param toType   Дескриптор типа в который надо конвертировать объект
     */
    public Mapper(Class<F> fromType, Class<T> toType) {
        this.fromType = fromType;
        this.toType = toType;
    }

    /**
     * Произвести преобразование
     *
     * @param from Откуда берем данные
     * @param to   Куда кладем. Не должен реализовывать паттерн неизменяемого класса
     */
    public abstract void map(F from, T to);

    public Class<F> getFromType() {
        return fromType;
    }

    public Class<T> getToType() {
        return toType;
    }
}
