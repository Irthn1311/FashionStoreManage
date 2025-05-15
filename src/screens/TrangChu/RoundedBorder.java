package screens.TrangChu;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder implements Border {
    private int cornerRadius;
    private Color borderColor;
    private int borderThickness;
    private Insets insets;

    public RoundedBorder(int cornerRadius, Color borderColor, int borderThickness, int uniformContentPadding) {
        this.cornerRadius = cornerRadius;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        int insetVal = borderThickness + uniformContentPadding;
        this.insets = new Insets(insetVal, insetVal, insetVal, insetVal);
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return (Insets) this.insets.clone();
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(this.borderColor);
            g2d.setStroke(new BasicStroke(this.borderThickness));

            float arc = this.cornerRadius * 2.0f;
            float halfThickness = (float)this.borderThickness / 2.0f;

            Shape borderShape = new RoundRectangle2D.Float(
                x + halfThickness,
                y + halfThickness,
                width - this.borderThickness,
                height - this.borderThickness,
                arc,
                arc
            );
            g2d.draw(borderShape);
        } finally {
            g2d.dispose();
        }
    }
} 