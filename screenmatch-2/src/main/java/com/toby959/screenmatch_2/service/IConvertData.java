package com.toby959.screenmatch_2.service;

public interface IConvertData {

    <T> T getData(String json, Class<T> element);
}
