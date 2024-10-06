<div style="background-color: #1e1e1e; padding: 20px; border-radius: 10px; color: white;">
    <h1>Match Maker</h1>
  <p>This is a Java project that generates random combinations of matches for a tournament.</p>  
  <p>Match Maker is built using:</p>
  <p>
    <img src="https://img.shields.io/badge/JSwing-%23FF7800.svg?style=flat&logo=Java&logoColor=white" alt="JSwing">
    <img src="https://img.shields.io/badge/Java-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white" alt="Java">
  </p>
</div>

<br>

## Features
    The his program only consist one GUI page.
    

#### Code for adding teams
---

```java
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
        this.combinationsLabel.setText(Integer.toString(perm));
        this.combinationsLabel.revalidate();
        this.combinationsLabel.repaint();
    }        
}
```
#### What it does: 
    This method adds a team in the list (ArryList) of teams that are going to be matched to each other. 

    As the user adds a teams it will display it to the teamsPanel and changes the possible combinaiton.

---

<br>

#### Code for generating matches
---
~~~ java
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
~~~

#### What it does
    This code does not generate the matches but a static method named generateMatches from a class named Combinations.

    The first thing it does if check if the amount is less than two. If it does its will show a dialog message after that the method will stop other wise it will call the generateMatches and store it's returns to a variable which is then displayed.
---

<br>

#### Code for clearing displayed teams and possible Combinations
~~~ java
private void clearTeams() {
    this.combinationsLabel.setText("0");
    this.teams.clear();
    this.teamsPanel.removeAll();
    this.teamsPanel.revalidate();
    this.teamsPanel.repaint();
    this.teamsPanel.setPreferredSize(new Dimension(100, 50));
    this.bracketPanel.removeAll();
    this.bracketPanel.revalidate();
    this.bracketPanel.repaint();
}
~~~

#### What it does 
    It will remove the displayed teams, generated matches and possible combinations.

---

### Code for calculating possible Combinabinations
~~~java
private static int factorial(int n) {
    if (n == 0) 
        return 1;
    
    return n * factorial(n - 1);
}

private static int factorial(int n, int k) {
    if (n == k) 
        return 1;
    
    return n * factorial(n - 1, k);
}

public static int posibleCombinations(int amountOfTeams) {
    if (amountOfTeams == 0) 
        return 1;
    
    return factorial(amountOfTeams, 2) / factorial(amountOfTeams - 2);
}
~~~

#### What it does
    These methods only calculates the possible combinations of inputed teams by the user. By using the formula of calculating combinations [n! / (n-k)!] we can predict the amount of different possible combinations/matches.

### Code for generateMatches method
~~~java
public static ArrayList<Match> generateMatches(ArrayList<Team> teams) {
    ArrayList<Match> matches = new ArrayList<Match>();
    ArrayList<Team> copy = new ArrayList<Team>(teams);
    Match byStander = null;
    Random rand = new Random();

    if (copy.size() % 2 != 0) {
        int index = rand.nextInt(copy.size());
        byStander = new Match(copy.get(index));
        copy.remove(index);
    }

    while (copy.size() > 0) {
        int index1 = rand.nextInt(copy.size());
        Team team1 = copy.get(index1);
        copy.remove(index1);

        int index2 = rand.nextInt(copy.size());
        Team team2 = copy.get(index2);
        copy.remove(index2);

        Match match = new Match(team1, team2);
        matches.add(match);
    }
    
    if (byStander != null) 
        matches.add(byStander);

    return matches;
}
~~~

#### What it does 
    It generates matches by randomly picking teams from the inputted teams. If the amount of teams is odd it first randomly pick a single team from the teams to labelled as bystander 