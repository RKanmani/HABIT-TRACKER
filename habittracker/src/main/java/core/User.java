package core;

public class User extends BaseUser {
  private static final long serialVersionUID = 1L;

  public User(String username) {
    super(username);
  }

  @Override
  public void displayInfo() {
    System.out.println("User: " + username + " | Habits: " + habits.size() + " | Points: " + totalPoints);
  }

  public void addHabit(Habit habit) {
    habits.add(habit);
  }

  public void removeHabit(String habitId) {
    habits.removeIf(h -> h.getId().equals(habitId));
  }

  public Habit getHabitById(String id) {
    return habits.stream().filter(h -> h.getId().equals(id)).findFirst().orElse(null);
  }
}
