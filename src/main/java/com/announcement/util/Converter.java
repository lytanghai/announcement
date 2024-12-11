package com.announcement.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

@NoArgsConstructor
public class Converter {

    public static Map<String, Object> convertJsonStringToMap(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(jsonString, new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static <T extends Object> T convert(Map<String, Object> map, T clazz) throws NoSuchFieldException, IllegalAccessException {

        for(Map.Entry<String, Object> entry : map.entrySet()) {
            Field field = clazz.getClass().getDeclaredField(entry.getKey());
            field.setAccessible(true);
            field.set(clazz, entry.getValue());
        }

        return clazz;
    }

}
