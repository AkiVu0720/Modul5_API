package com.product02.model.mapper;

public interface IMapperGeneric<E,T,V> {
    E requestToEntity(T t);
    V EntityToResponse(E e);

}
