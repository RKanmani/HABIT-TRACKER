package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StyledButton extends JButton {
  private Color bg, hover;
  private boolean isHovered = false;

  public StyledButton(String text, Color bg, Color hover) {
    super(text);
    this.bg = bg;
    this.hover = hover;
    setFocusPainted(false);
    setBorderPainted(false);
    setContentAreaFilled(false);
    setForeground(Color.WHITE);
    setFont(new Font("Arial", Font.BOLD, 13));
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        isHovered = true;
        repaint();
      }

      public void mouseExited(MouseEvent e) {
        isHovered = false;
        repaint();
      }
    });
  }

  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(isHovered ? hover : bg);
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    g2.dispose();
    super.paintComponent(g);
  }
}
