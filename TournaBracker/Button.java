import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;

public class Button extends JButton {

    private final int cornerRadius = 10;

    public Button(String text) {
        super(text);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setMargin(new Insets(10, 10, 10, 10));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setBackground(this.getBackground());
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), cornerRadius, cornerRadius);

        FontMetrics fm = g.getFontMetrics();
        Rectangle stringBounds = fm.getStringBounds(this.getText(), g).getBounds();
        int textX = (this.getWidth() - stringBounds.width) / 2;  // Horizontally center the text
        int textY = (this.getHeight() - stringBounds.height) / 2 + fm.getAscent(); 

        g.drawString(this.getText(), textX, textY);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Set border color
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2.dispose();
    }

    @Override
    public void setContentAreaFilled(boolean b) {
        // Do nothing to prevent default behavior
    }
}