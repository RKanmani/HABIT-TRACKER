package ui;

import core.*;
import exceptions.InvalidHabitException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class HabitTrackerGUI extends JFrame {
  private HabitTrackerBackend backend;
  private DefaultTableModel tableModel;
  private JTable table;
  private JTextField nameField;
  private JComboBox<String> freqCombo, catCombo, priCombo, filterCombo;
  private JLabel[] statLabels = new JLabel[5];

  public HabitTrackerGUI() {
    backend = new HabitTrackerBackend();
    initUI();
    loadTable();
  }

  private void initUI() {
    setTitle("Habit Tracker Pro - Ultimate Edition");
    setSize(1200, 750);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel main = new JPanel(new BorderLayout(10, 10));
    main.setBackground(new Color(106, 17, 203));
    main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    // HEADER
    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(new Color(106, 17, 203));
    JLabel title = new JLabel("Habit Tracker Pro", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 26));
    title.setForeground(Color.WHITE);
    header.add(title, BorderLayout.NORTH);

    // STATS PANEL
    JPanel stats = new JPanel(new GridLayout(1, 5, 10, 0));
    stats.setBackground(new Color(106, 17, 203));
    stats.setPreferredSize(new Dimension(0, 90));
    Color[] colors = { new Color(255, 107, 107), new Color(78, 205, 196),
        new Color(255, 160, 122), new Color(155, 89, 182), new Color(52, 152, 219) };
    String[] labels = { "Total", "Today", "Best Streak", "Points", "Progress" };
    for (int i = 0; i < 5; i++) {
      statLabels[i] = new JLabel("0", SwingConstants.CENTER);
      JPanel card = new JPanel(new BorderLayout());
      card.setBackground(colors[i]);
      statLabels[i].setFont(new Font("Arial", Font.BOLD, 24));
      statLabels[i].setForeground(Color.WHITE);
      JLabel lbl = new JLabel(labels[i], SwingConstants.CENTER);
      lbl.setFont(new Font("Arial", Font.PLAIN, 12));
      lbl.setForeground(Color.WHITE);
      card.add(statLabels[i], BorderLayout.CENTER);
      card.add(lbl, BorderLayout.SOUTH);
      stats.add(card);
    }
    header.add(stats, BorderLayout.SOUTH);
    main.add(header, BorderLayout.NORTH);

    // CENTER PANEL
    JPanel center = new JPanel(new BorderLayout(10, 10));
    center.setBackground(Color.WHITE);
    center.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    // INPUT PANEL
    JPanel input = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    input.setBackground(new Color(240, 242, 245));
    input.add(new JLabel("Name:"));
    nameField = new JTextField(15);
    input.add(nameField);
    input.add(new JLabel("Frequency:"));
    freqCombo = new JComboBox<>(new String[] { "Daily", "Weekly", "Monthly" });
    input.add(freqCombo);
    input.add(new JLabel("Category:"));
    catCombo = new JComboBox<>(new String[] { "Health", "Fitness", "Learning", "Work", "Personal", "General" });
    input.add(catCombo);
    input.add(new JLabel("Priority:"));
    priCombo = new JComboBox<>(new String[] { "High", "Medium", "Low" });
    input.add(priCombo);

    StyledButton addBtn = new StyledButton("Add", new Color(46, 213, 115), new Color(39, 174, 96));
    addBtn.setPreferredSize(new Dimension(100, 35));
    addBtn.addActionListener(e -> addHabit());
    input.add(addBtn);

    center.add(input, BorderLayout.NORTH);

    // FILTER PANEL
    JPanel filter = new JPanel(new FlowLayout(FlowLayout.LEFT));
    filter.setBackground(Color.WHITE);
    filter.add(new JLabel("Filter:"));
    filterCombo = new JComboBox<>(
        new String[] { "All", "Health", "Fitness", "Learning", "Work", "Personal", "General" });
    filterCombo.addActionListener(e -> filterHabits());
    filter.add(filterCombo);
    center.add(filter, BorderLayout.SOUTH);

    // TABLE PANEL
    String[] cols = { "ID", "Name", "Category", "Freq", "Priority", "Streak", "Last", "Progress" };
    tableModel = new DefaultTableModel(cols, 0) {
      public boolean isCellEditable(int r, int c) {
        return false;
      }
    };
    table = new JTable(tableModel);
    table.setFont(new Font("Arial", Font.PLAIN, 13));
    table.setRowHeight(32);
    table.getTableHeader().setBackground(new Color(52, 73, 94));
    table.getTableHeader().setForeground(Color.WHITE);
    table.setSelectionBackground(new Color(52, 152, 219));
    center.add(new JScrollPane(table), BorderLayout.CENTER);

    main.add(center, BorderLayout.CENTER);

    // BUTTON PANEL
    JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
    btns.setBackground(new Color(106, 17, 203));
    addButton(btns, "Complete", new Color(52, 152, 219), e -> markComplete());
    addButton(btns, "Update", new Color(241, 196, 15), e -> updateHabit());
    addButton(btns, "Notes", new Color(52, 206, 206), e -> addNotes());
    addButton(btns, "Stats", new Color(155, 89, 182), e -> showStats());
    addButton(btns, "Delete", new Color(231, 76, 60), e -> deleteHabit());
    addButton(btns, "Refresh", new Color(100, 100, 100), e -> loadTable());
    main.add(btns, BorderLayout.SOUTH);

    setContentPane(main);
  }

  private void addButton(JPanel p, String txt, Color c, ActionListener a) {
    StyledButton b = new StyledButton(txt, c, c.darker());
    b.setPreferredSize(new Dimension(110, 38));
    b.addActionListener(a);
    p.add(b);
  }

  // ==== Table & Stats Handling ====
  private void loadTable() {
    tableModel.setRowCount(0);
    for (Habit h : backend.getAllHabits()) {
      addRow(h);
    }
    updateStats();
  }

  private void addRow(Habit h) {
    tableModel.addRow(new Object[] {
        h.getId().substring(0, 8),
        h.getName(),
        h.getCategory(),
        h.getFrequency(),
        h.getPriorityString(),
        h.getStreak() + "d",
        h.getLastCompleted() != null ? h.getLastCompleted().toString() : "Never",
        h.getProgress() + "%"
    });
  }

  private void updateStats() {
    List<Habit> habits = backend.getAllHabits();
    statLabels[0].setText(String.valueOf(habits.size()));
    statLabels[1].setText(String.valueOf(habits.stream().filter(Habit::isCompletedToday).count()));
    statLabels[2].setText(habits.stream().mapToInt(Habit::getStreak).max().orElse(0) + "d");
    statLabels[3].setText(String.valueOf(backend.getUser().getTotalPoints()));
    int avg = habits.isEmpty() ? 0 : habits.stream().mapToInt(Habit::getProgress).sum() / habits.size();
    statLabels[4].setText(avg + "%");
  }

  private String getIdFromRow(int row) {
    if (row < 0 || row >= tableModel.getRowCount()) {
      return null;
    }
    String shortId = (String) tableModel.getValueAt(row, 0);
    for (Habit h : backend.getAllHabits()) {
      if (h.getId().startsWith(shortId)) {
        return h.getId();
      }
    }
    return null;
  }

  // ==== GUI Methods ====
  private void addHabit() {
    try {
      backend.addHabit(
          nameField.getText().trim(),
          (String) freqCombo.getSelectedItem(),
          (String) catCombo.getSelectedItem(),
          priCombo.getSelectedIndex() + 1);
      nameField.setText("");
      loadTable();
      JOptionPane.showMessageDialog(this, "Habit added successfully!");
    } catch (InvalidHabitException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void markComplete() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Please select a habit first!", "No Selection", JOptionPane.WARNING_MESSAGE);
      return;
    }

    String id = getIdFromRow(row);
    if (id == null) {
      JOptionPane.showMessageDialog(this, "Could not find habit!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      backend.markComplete(id);
      loadTable();
      JOptionPane.showMessageDialog(this, "Completed! +10 Points", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (InvalidHabitException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void updateHabit() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Please select a habit first!", "No Selection", JOptionPane.WARNING_MESSAGE);
      return;
    }

    String id = getIdFromRow(row);
    if (id == null) {
      JOptionPane.showMessageDialog(this, "Could not find habit!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Habit h = backend.getUser().getHabitById(id);
    if (h == null) {
      JOptionPane.showMessageDialog(this, "Habit not found!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Create update dialog
    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
    JTextField nameF = new JTextField(h.getName(), 20);
    JComboBox<String> freqF = new JComboBox<>(new String[] { "Daily", "Weekly", "Monthly" });
    freqF.setSelectedItem(h.getFrequency());
    JComboBox<String> catF = new JComboBox<>(
        new String[] { "Health", "Fitness", "Learning", "Work", "Personal", "General" });
    catF.setSelectedItem(h.getCategory());
    JComboBox<String> priF = new JComboBox<>(new String[] { "High", "Medium", "Low" });
    priF.setSelectedItem(h.getPriorityString());

    panel.add(new JLabel("Name:"));
    panel.add(nameF);
    panel.add(new JLabel("Frequency:"));
    panel.add(freqF);
    panel.add(new JLabel("Category:"));
    panel.add(catF);
    panel.add(new JLabel("Priority:"));
    panel.add(priF);

    int result = JOptionPane.showConfirmDialog(this, panel, "Update Habit", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
      try {
        int priIndex = priF.getSelectedIndex() + 1;
        backend.updateHabit(
            h.getId(),
            nameF.getText().trim(),
            (String) freqF.getSelectedItem(),
            (String) catF.getSelectedItem(),
            priIndex);
        loadTable();
        JOptionPane.showMessageDialog(this, "Habit updated successfully!");
      } catch (InvalidHabitException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void addNotes() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Please select a habit first!", "No Selection", JOptionPane.WARNING_MESSAGE);
      return;
    }

    String id = getIdFromRow(row);
    if (id == null) {
      JOptionPane.showMessageDialog(this, "Could not find habit!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Habit h = backend.getUser().getHabitById(id);
    if (h == null) {
      JOptionPane.showMessageDialog(this, "Habit not found!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String notes = JOptionPane.showInputDialog(this, "Enter Notes:", h.getNotes());
    if (notes != null) {
      try {
        backend.addNotes(h.getId(), notes);
        loadTable();
        JOptionPane.showMessageDialog(this, "Notes saved successfully!");
      } catch (InvalidHabitException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void showStats() {
    updateStats();
    List<Habit> habits = backend.getAllHabits();

    StringBuilder stats = new StringBuilder();
    stats.append("=== HABIT TRACKER STATISTICS ===\n\n");
    stats.append("Total Habits: ").append(habits.size()).append("\n");
    stats.append("Completed Today: ").append(habits.stream().filter(Habit::isCompletedToday).count()).append("\n");
    stats.append("Best Streak: ").append(habits.stream().mapToInt(Habit::getStreak).max().orElse(0)).append(" days\n");
    stats.append("Total Points: ").append(backend.getUser().getTotalPoints()).append("\n");
    int avg = habits.isEmpty() ? 0 : habits.stream().mapToInt(Habit::getProgress).sum() / habits.size();
    stats.append("Average Progress: ").append(avg).append("%\n\n");
    stats.append("Total Completions: ").append(habits.stream().mapToInt(Habit::getTotalCompletions).sum()).append("\n");

    JOptionPane.showMessageDialog(this, stats.toString(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
  }

  private void deleteHabit() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Please select a habit first!", "No Selection", JOptionPane.WARNING_MESSAGE);
      return;
    }

    String id = getIdFromRow(row);
    if (id == null) {
      JOptionPane.showMessageDialog(this, "Could not find habit!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this habit?", "Confirm Delete",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      try {
        backend.deleteHabit(id);
        loadTable();
        JOptionPane.showMessageDialog(this, "Habit deleted successfully!");
      } catch (InvalidHabitException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void filterHabits() {
    String filter = (String) filterCombo.getSelectedItem();
    tableModel.setRowCount(0);
    for (Habit h : backend.getAllHabits()) {
      if ("All".equals(filter) || h.getCategory().equals(filter)) {
        addRow(h);
      }
    }
    // Note: Stats still show all habits, not just filtered ones
    // This is intentional - filter is for display only
  }
}