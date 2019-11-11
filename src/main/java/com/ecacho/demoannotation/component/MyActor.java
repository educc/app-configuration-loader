package com.ecacho.demoannotation.component;

import com.ecacho.demoannotation.AppConfig;
import com.ecacho.demoannotation.annotation.AppValue;
import com.ecacho.demoannotation.annotation.Injector;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MyActor {

  String name;

  @AppValue(name ="app.myactor.version", resolver = AppConfig.RESOLVER_INT)
  int version;

  @AppValue(name ="app.myactor.default-specie")
  String specie;

  public MyActor(String name) {
    this.name = name;
    Injector.inject(this);
  }
}
