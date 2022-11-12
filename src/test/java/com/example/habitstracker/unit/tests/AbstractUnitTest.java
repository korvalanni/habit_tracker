package com.example.habitstracker.unit.tests;

import com.example.habitstracker.mappers.Mapper;
import com.example.habitstracker.services.MapperService;
import org.mockito.Mockito;

/**
 * Бзаовый класс для тестов, в которых требуется настройка {@link MapperService}
 */
public abstract class AbstractUnitTest {
    protected MapperService mapperService;

    /**
     * Настроить мок для класса {@link MapperService}
     *
     * @param fromClass Дескриптор типа из которого будут происходить преобразования
     * @param toClass Дескриптор типа в которого будут происходить преобразования
     * @param mapper Маппер, который будет использоваться
     * @param <F> Тип из которого будут происходить преобразования
     * @param <T> Тип в которого будут происходить преобразования
     */
    protected <F, T> void setupMapperService(Class<F> fromClass, Class<T> toClass, Mapper<F, T> mapper) {
        Mockito.doAnswer((i) -> {
            mapper.map(i.getArgument(0), i.getArgument(1));
            return null;
        }).when(mapperService).transform(Mockito.any(fromClass), Mockito.any(toClass));
    }
}
