package exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidHabitExceptionTest {

  @Test
  void testExceptionMessage() {
    InvalidHabitException ex = new InvalidHabitException("Invalid habit");
    assertEquals("Invalid habit", ex.getMessage());
  }
}
