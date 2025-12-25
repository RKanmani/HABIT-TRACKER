package core;

import exceptions.InvalidHabitException;
import java.util.*;
import java.io.*;

public class HabitTrackerBackend {
  private User user;
  private FileManager fileManager;
  private HabitValidator validator;
  private static final String DATA_FILE = "habits_data.ser";

  public HabitTrackerBackend() {
    this.fileManager = new FileManager();
    this.validator = new HabitValidator();
    loadData();
  }

  // ===== ADD THIS NEW CONSTRUCTOR HERE =====
  public HabitTrackerBackend(boolean loadData) {
    this.fileManager = new FileManager();
    this.validator = new HabitValidator();
    if (loadData) {
      loadData();
    } else {
      user = new User("TestUser");
    }
  }
  // =========================================

  private void loadData() {
    try {
      user = fileManager.load(DATA_FILE);
    } catch (Exception e) {
      user = new User("DefaultUser");
    }
  }

  public void saveData() {
    try {
      fileManager.save(user, DATA_FILE);
    } catch (IOException e) {
      System.err.println("Error saving data: " + e.getMessage());
    }
  }

  public void addHabit(String name, String frequency, String category, int priority) throws InvalidHabitException {
    validator.validate(name, frequency);
    validator.validateCategory(category);
    validator.validatePriority(priority);

    Habit habit = new Habit(name, frequency);
    habit.setCategory(category);
    habit.setPriority(priority);
    user.addHabit(habit);
    saveData();
  }

  public void updateHabit(String id, String name, String freq, String cat, int pri) throws InvalidHabitException {
    Habit h = user.getHabitById(id);
    if (h == null) {
      throw new InvalidHabitException("Habit not found!");
    }

    validator.validate(name, freq);
    validator.validateCategory(cat);
    validator.validatePriority(pri);

    h.setName(name);
    h.setFrequency(freq);
    h.setCategory(cat);
    h.setPriority(pri);
    saveData();
  }

  public void deleteHabit(String id) throws InvalidHabitException {
    Habit h = user.getHabitById(id);
    if (h == null) {
      throw new InvalidHabitException("Habit not found!");
    }
    user.removeHabit(id);
    saveData();
  }

  public void markComplete(String id) throws InvalidHabitException {
    Habit h = user.getHabitById(id);
    if (h == null) {
      throw new InvalidHabitException("Habit not found!");
    }

    // Check if already completed today
    if (h.isCompletedToday()) {
      throw new InvalidHabitException("Habit already completed today!");
    }

    h.markComplete();
    user.addPoints(10);
    saveData();
  }

  public void addNotes(String id, String notes) throws InvalidHabitException {
    Habit h = user.getHabitById(id);
    if (h == null) {
      throw new InvalidHabitException("Habit not found!");
    }
    h.setNotes(notes);
    saveData();
  }

  public List<Habit> getAllHabits() {
    return new ArrayList<>(user.getHabits()); // Return copy to prevent external modification
  }

  // ===== ADD THIS NEW METHOD HERE =====
  public Habit getHabitById(String id) throws InvalidHabitException {
    Habit h = user.getHabitById(id);
    if (h == null) {
      throw new InvalidHabitException("Habit not found!");
    }
    return h;
  }
  // ====================================

  public User getUser() {
    return user;
  }
}