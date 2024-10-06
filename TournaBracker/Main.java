import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Main extends JFrame implements ActionListener {
    private ArrayList<Team> teams = new ArrayList<>();
    private Button bracket, inputTeams, clear;
    private JScrollPane teamsScrollPane;
    private JPanel teamsPanel, bracketPanel;
    private JLabel permutaionLabel;

    Main() {
        this.setTitle("Match Maker");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.BLACK);

        // Title Label
        JLabel titleLabel = new JLabel("Match Maker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // Adjust gaps for better visibility
        mainPanel.setBackground(Color.LIGHT_GRAY); // Light gray background for visibility
        mainPanel.setPreferredSize(new Dimension(400, 500)); // Reasonable size for the center panel

        this.bracket = new Button("Generate");
        this.inputTeams = new Button("Input Teams");
        this.clear = new Button("Clear Teams");

        bracket.addActionListener(this);
        inputTeams.addActionListener(this);
        clear.addActionListener(this);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // GridLayout with padding
        buttonPanel.add(bracket);
        buttonPanel.add(inputTeams);
        buttonPanel.add(clear);
        buttonPanel.setVisible(true);
        buttonPanel.setSize(100, 150);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        
        permutaionLabel = new JLabel("0");

        JPanel permutationPanel = new JPanel();
        permutationPanel.setLayout(new GridLayout(2, 1, 10, 10));
        permutationPanel.setBackground(Color.LIGHT_GRAY);
        permutationPanel.setPreferredSize(new Dimension(100, 150));
        permutationPanel.setVisible(true);
        this.permutaionLabel = new JLabel();
        permutationPanel.add(new JLabel("Combitations:"));
        permutationPanel.add(permutaionLabel);

        // Sidebar Panel
        JPanel sidPanel = new JPanel();
        sidPanel.setLayout(new GridLayout(3, 1, 10, 10)); // GridLayout with padding
        sidPanel.setBackground(Color.LIGHT_GRAY);
        sidPanel.setPreferredSize(new Dimension(100, 150)); // Set a reasonable size for the sidebar
        sidPanel.setVisible(true);
        sidPanel.add(buttonPanel);
        sidPanel.add(permutationPanel);

        bracketPanel = new JPanel();
        bracketPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bracketPanel.setPreferredSize(new Dimension(400, 400));

        // Add components
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(sidPanel, BorderLayout.WEST);

        this.teamsPanel = new JPanel();
        this.teamsPanel.setBackground(Color.GRAY);
        this.teamsPanel.setPreferredSize(new Dimension(100, 50));
        this.teamsPanel.setLayout(new BoxLayout(teamsPanel, BoxLayout.X_AXIS));

        this.teamsScrollPane = new JScrollPane(teamsPanel);
        this.teamsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        mainPanel.add(teamsScrollPane, BorderLayout.NORTH);
        mainPanel.add(bracketPanel, BorderLayout.CENTER);

        // Ensure everything is visible
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bracket) 
            generateBracket();
        else if (e.getSource() == inputTeams) 
            addTeam();
        else if (e.getSource() == clear) 
            clearTeams();
    }

    private void generateBracket() {
        if (this.teams.size() < 2) {
            JOptionPane.showMessageDialog(this, "No teams to generate matches", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Match> matches = Combinations.generateMatches(this.teams);
        this.bracketPanel.removeAll();

        for (Match match : matches) 
            this.bracketPanel.add(match);

        this.bracketPanel.revalidate();
        this.bracketPanel.repaint();
    }

    private void addTeam() {
        String name = "";

        while (name.isEmpty()) 
            name = JOptionPane.showInputDialog("Enter team name:");

        if (name != null) {
            for (Team team : this.teams) {
                if (team.getTeamName().equals(name)) {
                    JOptionPane.showMessageDialog(this, "Team already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Team newTeam = new Team(name);

            this.teams.add(newTeam);
            int newWidth = this.teams.size() * 200;
            this.teamsPanel.setPreferredSize(new Dimension(newWidth, 50));
            this.teamsPanel.removeAll();

            for (Team team : this.teams) 
                this.teamsPanel.add(team);

            this.teamsPanel.revalidate();
            this.teamsPanel.repaint();
        }

        this.bracketPanel.removeAll();
        this.bracketPanel.revalidate();
        this.bracketPanel.repaint();
        
        if (this.teams.size() > 1) {
            int perm = this.teams.size() % 2 == 0 ? Combinations.posibleCombinations(this.teams.size()) : Combinations.posibleCombinations(this.teams.size() - 1);
            this.permutaionLabel.setText(Integer.toString(perm));
            this.permutaionLabel.revalidate();
            this.permutaionLabel.repaint();
        }
            
    }

    private void clearTeams() {
        this.permutaionLabel.setText("0");
        this.teams.clear();
        this.teamsPanel.removeAll();
        this.teamsPanel.revalidate();
        this.teamsPanel.repaint();
        this.teamsPanel.setPreferredSize(new Dimension(100, 50));
        this.bracketPanel.removeAll();
        this.bracketPanel.revalidate();
        this.bracketPanel.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }
}