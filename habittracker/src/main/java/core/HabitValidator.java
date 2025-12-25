package core;

import exceptions.InvalidHabitException;

public class HabitValidator implements Validator {
  private static final String[] VALID_FREQUENCIES = { "Daily", "Weekly", "Monthly" };
  private static final String[] VALID_CATEGORIES = { "Health", "Fitness", "Learning", "Work", "Personal", "General" };

  @Override
  public boolean validate(String name, String frequency) throws InvalidHabitException {
    if (name == null || name.trim().isEmpty()) {
      throw new InvalidHabitException("Habit name cannot be empty!");
    }

    if (name.length() > 100) {
      throw new InvalidHabitException("Habit name is too long (max 100 characters)!");
    }

    boolean validFreq = false;
    for (String f : VALID_FREQUENCIES) {
      if (f.equals(frequency)) {
        validFreq = true;
        break;
      }
    }

    if (!validFreq) {
      throw new InvalidHabitException("Frequency must be Daily, Weekly, or Monthly!");
    }

    return true;
  }

  public boolean validateCategory(String category) throws InvalidHabitException {
    if (category == null || category.trim().isEmpty()) {
      throw new InvalidHabitException("Category cannot be empty!");
    }

    for (String c : VALID_CATEGORIES) {
      if (c.equals(category)) {
        return true;
      }
    }

    throw new InvalidHabitException("Invalid category! Must be: Health, Fitness, Learning, Work, Personal, or General");
  }

  public boolean validatePriority(int priority) throws InvalidHabitException {
    if (priority < 1 || priority > 3) {
      throw new InvalidHabitException("Priority must be between 1 (High) and 3 (Low)!");
    }
    return true;
  }
}