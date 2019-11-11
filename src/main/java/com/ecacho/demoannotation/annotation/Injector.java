package com.ecacho.demoannotation.annotation;

import java.lang.reflect.Field;

public class Injector {
  public static void inject(Object instance) {
    Field[] fields = instance.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(AppValue.class)) {
        AppValue set = field.getAnnotation(AppValue.class);
        field.setAccessible(true); // should work on private fields
        try {
          com.ecacho.demoannotation.AppConfig config = com.ecacho.demoannotation.AppConfig.getInstance();
          field.set(instance, config.get(set.name()));
        } catch (Exception e) {
          e.printStackTrace();
      }
      }
    }
  }
}
