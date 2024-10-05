import java.util.ArrayList;
import java.util.Random;

public class Combinations {

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

}