package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseUser implements Serializable {
  private static final long serialVersionUID = 1L;
  protected String username;
  protected List<Habit> habits;
  protected int totalPoints;

  public BaseUser(String username) {
    this.username = username;
    this.habits = new ArrayList<>();
    this.totalPoints = 0;
  }

  public abstract void displayInfo();

  public String getUsername() {
    return username;
  }

  public List<Habit> getHabits() {
    return habits;
  }

  public int getTotalPoints() {
    return totalPoints;
  }

  public void addPoints(int points) {
    this.totalPoints += points;
  }
}
