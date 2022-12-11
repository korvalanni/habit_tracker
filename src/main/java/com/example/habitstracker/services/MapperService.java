package com.example.habitstracker.services;

import com.example.habitstracker.exceptions.MapperNotFound;
import com.example.habitstracker.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Сервис, который находит по объектам нужный маппер и выполняет конвертацию объектов
 */
@Component
public class MapperService {
    @Autowired
    ApplicationContext applicationContext;

    /**
     * Конвертировать объект одного типа в объект другого типа
     *
     * @param from Объект из которого надо перенести данные
     * @param to   Объект в который надо перенести данные
     */
    @SuppressWarnings({"unchecked"})
    public void transform(Object from, Object to) {
        applicationContext
                .getBeansOfType(Mapper.class)
                .values()
                .stream()
                .filter(item ->
                        item.getFromType().isAssignableFrom(from.getClass())
                                && item.getToType().isAssignableFrom(to.getClass()))
                .findFirst()
                .ifPresentOrElse(mapper -> mapper.map(from, to), () -> {
                    throw new MapperNotFound(from.getClass().getTypeName(), to.getClass().getTypeName());
                });
    }
}

