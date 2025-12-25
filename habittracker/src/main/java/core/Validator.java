package core;

import exceptions.InvalidHabitException;

public interface Validator {
  boolean validate(String name, String frequency) throws InvalidHabitException;

  // Add this helper method for your test
  static boolean isValidName(String name) {
    return name != null && !name.trim().isEmpty();
  }
}
