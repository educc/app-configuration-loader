package com.ecacho.demoannotation;

import com.ecacho.demoannotation.annotation.AppValue;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class AppConfig {

  Map<String, Object> properties;
  Map<String, Function> resolvers;
  Reflections reflections;

  public static final String RESOLVER_STRING = "RESOLVER_STRING";
  public static final String RESOLVER_INT = "RESOLVER_INT";

  private static AppConfig instance;

  private AppConfig(Properties props) {
    reflections = new Reflections(new ConfigurationBuilder()
        .setUrls(ClasspathHelper.forPackage("com.ecacho.demoannotation"))
        .setScanners(
            new SubTypesScanner(),
            new TypeAnnotationsScanner(),
            new FieldAnnotationsScanner()
        ));
    resolvers = defaultResolvers();
    properties = parseProps(props);
  }

  private Map<String, Object> parseProps(Properties properties) {
    Map<String, Object> result = new HashMap<>();
    Set<Field> ids =
        reflections.getFieldsAnnotatedWith(AppValue.class);

    for (Field field : ids) {
      AppValue annotation = field.getAnnotation(AppValue.class);

      String name = properties.getProperty(annotation.name());
      if (name == null) {
        throw new RuntimeException("Property is required: " + annotation.name());
      }

      Function fn = resolvers.get(annotation.resolver());
      if (fn == null) {
        throw new RuntimeException("Resolver doesn't exists: " + annotation.resolver());
      }

      result.put(annotation.name(), fn.apply(name));
    }

    return result;
  }

  private Map<String, Function> defaultResolvers() {
    Map<String, Function> result = new HashMap<>();

    result.put(RESOLVER_STRING, it -> {
      return it.toString();
    });
    result.put(RESOLVER_INT, it -> {
      return Integer.parseInt(it.toString());
    });
    return result;
  }

  public static void createInstance(Properties properties){
    if(instance == null){
      instance = new AppConfig(properties);
    }
  }

  public static AppConfig getInstance() {
    return instance;
  }

  public Object get(String name){
    return properties.get(name);
  }
}
