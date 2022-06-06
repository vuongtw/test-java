package hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GreeterTests {

  @Test
  @DisplayName("sayHello returns 'Hello world!'")
  void checkSayHelloReturn() {
    Greeter greeter = new Greeter();
    assertEquals("Hello world!", greeter.sayHello());
  }
}
