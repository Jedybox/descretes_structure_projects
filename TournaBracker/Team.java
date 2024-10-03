import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.Color;

public class Team extends JPanel {
    private String name;

    public Team(String name) {
        this.name = name;

        Random rand = new Random();

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        this.setBackground(new Color(r, g, b));

        r = rand.nextFloat();
        g = rand.nextFloat();
        b = rand.nextFloat();

        this.setForeground(new Color(r, g, b));

        JLabel label = new JLabel(this.name);
        this.add(label);
        this.setSize(90, 50);
        this.setOpaque(true);
        this.setVisible(true);
    } 

    public String getTeamName() {
        return this.name;
    }
}