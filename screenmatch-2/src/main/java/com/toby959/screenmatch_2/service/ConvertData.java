package com.toby959.screenmatch_2.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData{

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public <T> T getData(String json, Class<T> element) {
            try {
                return objectMapper.readValue(json, element);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
}

