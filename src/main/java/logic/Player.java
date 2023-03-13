package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class for the game crosswise. In This class a player consists out of his name, his hand, his activity status
 * and whether he is an AI or a human player.
 *
 * @author Florian Kern/cgt104661
 */

public class Player {

    /**
     * The name of the player.
     */
    private String playerName;

    /**
     * The hand of the player.
     */
    private List<Tokens> playerHand;

    /**
     * Flag to determine if a player is active or not.
     */
    private boolean isActive;

    /**
     * Flag to determine if a player is human or an AI.
     */
    private boolean isAi;

    /**
     * The team of the player.
     */
    private Team team;


    /**
     * Constructor for testing.
     *
     * @param playerName - The name of the player
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.playerHand = new ArrayList<>();
    }


    /**
     * Constructor for constructing a player for a game of Crosswise.
     *
     * @param playerName - The name of the player
     * @param team       - The team the player plays for
     * @param isActive   - Is the player an active player?
     * @param isAi       - Does a human or an AI play?
     */
    public Player(String playerName, Team team, boolean isActive, boolean isAi) {
        this.playerName = playerName;
        this.playerHand = new ArrayList<>();
        this.team = team;
        this.isActive = isActive;
        this.isAi = isAi;
    }


    /**
     * Constructor for constructing a player if a game gets loaded, also used for testing.
     *
     * @param playerName - The name of the player
     * @param team       - The team the player plays for
     * @param isActive   - Is the player an active player?
     * @param isAi       - Does a human or an AI play?
     * @param playerHand - Hand of the player containing ordinal values for the tokens
     */
    public Player(String playerName, Team team, boolean isActive, boolean isAi, int[] playerHand) {
        this.playerName = playerName;
        this.isActive = isActive;
        this.isAi = isAi;
        this.team = team;
        ArrayList<Tokens> hand = new ArrayList<>();
        // Adding tokens to hand using their ordinal values
        for (int j : playerHand) {
            hand.add(Tokens.ordinalToToken(j));
        }
        this.playerHand = hand;
    }


    /**
     * Method gets the name of the player
     *
     * @return The name of the player.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Gets the team of the player.
     *
     * @return - The team of the player
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Checks if a player is active or not.
     *
     * @return True if the player is active, false if not.
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Checks if the player is an AI or not.
     *
     * @return - True, if AI, false if not
     */
    public boolean isAi() {
        return this.isAi;
    }


    /**
     * Removes a token from the hand of the player.
     *
     * @param token - The token
     */
    void removeFromHand(Tokens token) {
        this.playerHand.remove(token);
    }

    /**
     * Add a token to the hand of the player.
     *
     * @param token - The token
     */
    void addToHand(Tokens token) {
        this.playerHand.add(token);
    }

    /**
     * Method gets the hand of the player.
     *
     * @return The hand of the player.
     */
    public List<Tokens> getPlayerHand() {
        return new ArrayList<>(this.playerHand);
    }

    /**
     * Initializes the hand of the player with tokens.
     *
     * @param tokenPouch - The pouch containing the tokens of the game
     */
    void fillHand(TokenPouch tokenPouch) {
        while (this.playerHand.size() < GameCrosswise.HANDSIZE) {
            this.playerHand.add(tokenPouch.getToken());
        }
    }


}
