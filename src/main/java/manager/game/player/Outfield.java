package manager.game.player;

import lombok.AccessLevel;
import manager.game.team.Team;
import manager.game.utils.Value;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Outfield extends Player {

    @Setter(AccessLevel.NONE)
    private int finishing, marking, dribbling, longShots, velocity, stamina;

    private Position currentPosition;
    private final Position position;


    public Outfield(int id, String name, int height, int weight, int velocity, int agility, int stamina,
                    int passing, int finishing, int marking, int dribbling, int technique, int longShots,
                    int impulsion, Position position, double price, double salary, Team currentTeam) {

        super(id, name, height, weight, agility, passing, impulsion, technique, price, salary, currentTeam);
        this.velocity = velocity;
        this.finishing = finishing;
        this.marking = marking;
        this.dribbling = dribbling;
        this.longShots = longShots;
        this.position = position;
        this.stamina = stamina;

    }

    public void addFinishing(int finishing) {
        this.finishing += finishing;
    }
    public void addMarking(int marking) {
        this.marking += marking;
    }
    public void addDribbling(int dribbling) {
        this.dribbling += dribbling;
    }
    public void addLongShots(int longShots) {
        this.longShots += longShots;
    }
    public void addStamina(int stamina) {
        this.stamina += stamina;
    }

    @Override
    protected int jumpReach(){
        int minStamina = 12;
        return super.jumpReach() - staminaPenalty(minStamina);
    }

    @Override
    public void injuryRisk(){
        int minStamina = 12;
        double injuryChance = Math.random() * 20 + (double)getWeight() / 10 + staminaPenalty(minStamina);
        if (injuryChance > 20){
            enterInjury();
        }
    }

    @Override
    public double competence(){
        return switch (position) {
            case DEFENSE -> defensiveCompetence();
            case MIDFIELD -> midfieldCompetence();
            case ATTACK -> attackCompetence();
        };
    }

    @Override
    public double inGameCompetence(){
        double multiplier = 1;
        int minStamina = 14;

        switch (currentPosition) {
            case DEFENSE: {
                multiplier = position.getMultiplier().get("DEFENSE");
                break;
            }
            case MIDFIELD: {
                multiplier = position.getMultiplier().get("MIDFIELD");
                break;
            }
            case ATTACK: {
                multiplier = position.getMultiplier().get("ATTACK");
                break;
            }
        }
        return Math.random() * 3 + multiplier * competence() - staminaPenalty(minStamina);
    }

    private int speed(){
        int fullSpeed = Value.normalize(velocity + (int)Math.round(0.3 * (20-getWeight()) + 0.7 * getHeight()), 40);
        int minStamina = 14;
        return fullSpeed - staminaPenalty(minStamina);
    }

    private int strength(){
        int fullStrength = (int)Math.round(0.3 * getHeight() + 0.7 * getWeight());
        int minStamina = 14;
        return fullStrength - staminaPenalty(minStamina);
    }

    private int staminaPenalty(int penalty){
        return stamina < penalty ? (int)Math.round((double)(penalty - stamina) / 3) : 0;
    }

    private double defensiveCompetence(){
        return Value.normalize(marking + getTechnique() + jumpReach() + strength() + getAgility() + getPassing() + speed(), 140.0);
    }

    private double midfieldCompetence(){
        return Value.normalize(marking + dribbling + longShots + getTechnique() + jumpReach() + strength() + getAgility() + getPassing() + speed(), 180.0);
    }

    private double attackCompetence(){
        return Value.normalize( finishing + dribbling + longShots + getTechnique() + jumpReach() + strength() + getAgility() + getPassing() + speed(), 200.0);
    }

}
