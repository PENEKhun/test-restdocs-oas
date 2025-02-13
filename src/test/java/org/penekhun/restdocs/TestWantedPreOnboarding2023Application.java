package org.penekhun.restdocs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestWantedPreOnboarding2023Application {

  public static void main(String[] args) {
    SpringApplication.from(PenekApplication::main)
        .with(TestWantedPreOnboarding2023Application.class).run(args);
  }

}
