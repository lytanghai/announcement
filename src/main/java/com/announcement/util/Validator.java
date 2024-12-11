package com.announcement.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Validator {

    public static <T> void validateKeys(Map<String,Object> request, T clazz) throws Exception {

        for (String key : request.keySet()) {
            List<String> extractKey = extractFields(clazz);
            if(extractKey.contains(key)) {
                continue;
            }
        }
    }

    public static List<String> extractFields(Object obj) {
        List<String> fieldValue = new ArrayList<>();
        Field[] fields = ((Class<?>) obj).getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if(!fieldName.isBlank() && !fieldName.isEmpty()) {
                fieldValue.add(fieldName);
            }
        }
        return fieldValue;
    }

}
