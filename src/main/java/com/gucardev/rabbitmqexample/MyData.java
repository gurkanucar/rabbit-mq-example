package com.gucardev.rabbitmqexample;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyData {
  private UUID id;
  private String name;

  public MyData(String name) {
    this.id = UUID.fromString("23178dc9-5aa6-492b-8c7e-504ced896412"); // UUID.randomUUID();
    this.name = name;
  }
}
