package com.titan.controller.mapper;

public interface BiMapper <T,R>{
    T toRest(R r);
    R toModel(T t);
}
