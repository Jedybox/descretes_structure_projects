import java.util.ArrayList;
import java.util.Random;

public class Linear {

    public static int posiblePermutations(int amountOfTeams) {
        if (amountOfTeams == 0) 
            return 1;
        
        return amountOfTeams * posiblePermutations(amountOfTeams - 1);
    }

    public static ArrayList<Team> generatePermutuation(ArrayList<Team> teams) {
        ArrayList<Team> matches = new ArrayList<Team>();
        ArrayList<Team> copy = new ArrayList<Team>(teams);

        Random rand = new Random();

        while (copy.size() > 0) {
            int index = rand.nextInt(copy.size());
            matches.add(copy.get(index));
            copy.remove(index);
        }

        return matches;
    }

}