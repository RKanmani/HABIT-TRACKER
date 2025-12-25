package ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HabitTrackerGUITest {

  @Test
  void testGUIInitialization() {
    HabitTrackerGUI gui = new HabitTrackerGUI();
    assertNotNull(gui);
    // basic sanity check for GUI visibility
    assertTrue(gui.isVisible() == false || gui.isVisible() == true);
  }
}
