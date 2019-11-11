package com.ecacho.demoannotation;

import com.ecacho.demoannotation.component.MyActor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

  public static void main(String[] args) throws IOException {
    Properties properties = new Properties();
    properties.load(new FileReader("app.properties"));

    AppConfig.createInstance(properties);

    MyActor actor1 = new MyActor("edu");

    MyActor actor2 = new MyActor("denis");
    actor2.setVersion(2);

    System.out.println(actor1);
    System.out.println(actor2);
  }
}
