package logic;

/**
 * Class for saving data of the player.
 *
 * @author Florian Kern/cgt104661
 */
public class PlayerData {

    /**
     * Name of the player
     */
    private String name;

    /**
     * Activity status of the player
     */
    private boolean isActive;

    /**
     * Information regarding if the player is an AI or not
     */
    private boolean isAi;

    /**
     * Hand of the player
     */
    private int[] playerHand;

    /**
     * Team of the player
     */
    private Team team;

    /**
     * Constructor. Saves the name, activity staus, AI status. the hand and the team of the player.
     *
     * @param name - Name of the player
     * @param isActive - activity status
     * @param isAi - Is the player an AI?
     * @param playerHand - Hand of the player
     * @param team - Team of the player
     */
    public PlayerData(String name, boolean isActive, boolean isAi, int[] playerHand, Team team) {
        this.name = name;
        this.isActive = isActive;
        this.isAi = isAi;
        this.playerHand = playerHand;
        this.team = team;
    }

    /**
     * Gets the name of the player.
     *
     * @return Name of the player.
     */
    public String getName() {
        return this.name;
    }


    /**
     * Gets the team of the player.
     *
     * @return The team of the player.
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Gets the activity status of the player.
     *
     * @return Activity status of the player.
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Getter for telling if the player is an AI.
     *
     * @return True if AI, false if not.
     */
    public boolean isAi() {
        return this.isAi;
    }

    /**
     * Gets the hand of the player containing ordinal values of tokens
     *
     * @return Hand of the player containing ordinal values of the tokens.
     */
    public int[] getPlayerHand() {
        return this.playerHand;
    }


}
