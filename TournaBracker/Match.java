import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Match extends JPanel{

    private Team team1;
    private Team team2;

    Match(Team team1, Team team2){
        this.team1 = team1;
        this.team2 = team2;

        this.setSize(200, 50);
        this.setOpaque(false);
        this.setVisible(true);

        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.add(team1);
        
        JLabel vsLabel = new JLabel(" vs ");

        JPanel vs = new JPanel();
        vs.setSize(20, 50);
        vs.setOpaque(false);
        vs.add(vsLabel);
        vs.setVisible(true);
        this.add(vs);

        this.add(team2);
    }

    Match(Team team) {
        this.setSize(200, 50);
        this.setOpaque(false);
        this.setVisible(true);

        this.setLayout(new BorderLayout());
        team.setOpaque(true);
        this.add(team, BorderLayout.CENTER);

        JLabel bye = new JLabel("ByStanding");
        this.add(bye, BorderLayout.EAST);
    }
    
    public Team getTeam1() {
        return this.team1;
    }

    public Team getTeam2() {
        return this.team2;
    }
}
