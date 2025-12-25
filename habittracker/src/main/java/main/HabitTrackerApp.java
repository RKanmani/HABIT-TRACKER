package main;

import javax.swing.*;
import ui.HabitTrackerGUI;

public class HabitTrackerApp {
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
    }
    SwingUtilities.invokeLater(() -> new HabitTrackerGUI().setVisible(true));
  }
}
