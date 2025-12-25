package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HabitTest {

    @Test
    void testHabitConstructorAndGetters() {
        Habit habit = new Habit("Exercise", "Daily");
        assertEquals("Exercise", habit.getName());
        assertEquals("Daily", habit.getFrequency());
        assertEquals("General", habit.getCategory());
        assertEquals(2, habit.getPriority());
        assertEquals(0, habit.getStreak());
        assertEquals(0, habit.getTotalCompletions());
        assertFalse(habit.isCompletedToday());
    }

    @Test
    void testMarkCompleteAndProgress() {
        Habit habit = new Habit("Read", "Daily");
        habit.markComplete();

        assertEquals(1, habit.getStreak());
        assertEquals(1, habit.getTotalCompletions());
        assertTrue(habit.isCompletedToday());
        assertEquals(10, habit.getProgress());
    }
}
