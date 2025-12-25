package core;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Habit implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private String name;
  private String frequency;
  private int streak;
  private LocalDate lastCompleted;
  private boolean completedToday;
  private int totalCompletions;
  private String category;
  private int priority;
  private String notes;

  public Habit(String name, String frequency) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.frequency = frequency;
    this.streak = 0;
    this.lastCompleted = null;
    this.completedToday = false;
    this.totalCompletions = 0;
    this.category = "General";
    this.priority = 2;
    this.notes = "";
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFrequency() {
    return frequency;
  }

  public void setFrequency(String frequency) {
    this.frequency = frequency;
  }

  public int getStreak() {
    return streak;
  }

  public LocalDate getLastCompleted() {
    return lastCompleted;
  }

  public boolean isCompletedToday() {
    // Check if completed today by comparing lastCompleted with current date
    LocalDate today = LocalDate.now();
    return lastCompleted != null && lastCompleted.equals(today);
  }

  public int getTotalCompletions() {
    return totalCompletions;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void markComplete() {
    LocalDate today = LocalDate.now();

    // Check if already completed today
    if (lastCompleted != null && lastCompleted.equals(today)) {
      return; // Already completed today, do nothing
    }

    // Check if this continues the streak
    if (lastCompleted != null && lastCompleted.plusDays(1).equals(today)) {
      streak++;
    } else if (lastCompleted == null || !lastCompleted.equals(today)) {
      // Either first completion or streak broken
      streak = 1;
    }

    lastCompleted = today;
    totalCompletions++;
  }

  public int getProgress() {
    return Math.min(100, (totalCompletions * 10));
  }

  public String getPriorityString() {
    return priority == 1 ? "High" : priority == 2 ? "Medium" : "Low";
  }
}