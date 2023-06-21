import java.util.ArrayList;
import java.util.List;

public class Team {

    //characters on the team
    private final List<Character> members;
    private final List<Character> graveyard = new ArrayList<>();

    public Team(List<Character> members){
        this.members = members;
    }

    /*
    One team battles another team. It is one round. Each member on the first team attacks each member on the other team.
    Before each attack, the other team member is checked to see if they are alive. If they are, they are attacked. If
    their health after the attack is 0, they are added to the "graveyard", and removed from the victimTeam at the end
    of the battle.
     */

    public boolean battle(Team defenderTeam){
        List<Character> defenders = defenderTeam.getMembers().stream().toList();
        List<Character> attackers = this.getMembers();
        
        // Loops over each victim and checks if they are dead (in the graveyard). If they are not, they take damage
        // equal to the attackers total attack power.
        for(Character defender: defenders)
            for(Character attacker: attackers) {
                if(defender.isAlive() && defender.takeDamage(attacker.getAttack()) == 0) {
                    String weaponText = attacker.getClass().getSuperclass().equals(Survivor.class) ?
                        " using a " + ((Survivor) attacker).getWeapon() : "";
                    System.out.printf("%s killed %s%s.%n",
                        attacker.getName(), defender.getName(), weaponText);
                    defenderTeam.moveToGraveyard(defender);
                }
            }
        
        return defenderTeam.getSize() == 0;
    }

    public int getSize(){
        return members.size();
    }
    
    public boolean moveToGraveyard(Character character) {
        this.graveyard.add(character);
        return this.getMembers().remove(character);
    }
    
    public List<Character> getGraveyard() {
        return this.graveyard;
    }

    public List<Character> getMembers(){
        return this.members;
    }
}
