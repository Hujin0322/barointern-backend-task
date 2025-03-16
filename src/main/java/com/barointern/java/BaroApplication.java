package com.barointern.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BaroApplication {

  public static void main(String[] args) {
    SpringApplication.run(BaroApplication.class, args);
  }

}
