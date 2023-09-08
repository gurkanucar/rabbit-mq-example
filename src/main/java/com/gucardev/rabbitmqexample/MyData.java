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
    this.id = UUID.randomUUID();
    this.name = name;
  }
}
