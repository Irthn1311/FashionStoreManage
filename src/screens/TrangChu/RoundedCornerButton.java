package screens.TrangChu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

// Class for Rounded Corner Button
public class RoundedCornerButton extends JButton {
    public RoundedCornerButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    public RoundedCornerButton() {
        super();
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int radius = 10; // Default
            Border currentBorder = getBorder();
            if (currentBorder instanceof RoundedBorder) {
                radius = ((RoundedBorder) currentBorder).getCornerRadius();
            }

            float arc = radius * 2.0f;

            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc));

            super.paintComponent(g);

        } finally {
            g2d.dispose();
        }
    }
} 