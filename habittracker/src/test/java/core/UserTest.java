package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

  @Test
  void testAddAndRemoveHabit() {
    User user = new User("Alice");

    Habit habit = new Habit("Study", "Daily");
    user.addHabit(habit);

    assertEquals(habit, user.getHabitById(habit.getId()));

    // Remove by habit ID
    user.removeHabit(habit.getId());
    assertNull(user.getHabitById(habit.getId()));
  }
}
