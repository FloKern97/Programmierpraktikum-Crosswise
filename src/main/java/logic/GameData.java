package logic;

/**
 * Class for storing data of the game.
 *
 * @author Florian Kern/cgt104661
 */
public class GameData {

    /**
     * Data of the players
     */
    private PlayerData[] players;

    /**
     * Index of the current player
     */
    private int currentPlayer;

    /**
     * The field, containing ordinal values of the tokens
     */
    private int[][] field;

    /**
     * Array containing the amount of used special tokens
     */
    private int[] usedActionTiles;

    /**
     * The token pouch, containing ordinal values of the tokens
     */
    private int[] tokenPouch;

    /**
     * Constructor for saving game data.
     *
     * @param players - Data of the players
     * @param currentPlayer - Index of the current player
     * @param gameField - The field
     * @param usedSpecialTokens - Used special tokens
     * @param tokenPouch - The pouch of the tokens
     */
    public GameData(PlayerData[] players, int currentPlayer, int[][] gameField,
                    int[] usedSpecialTokens, int[] tokenPouch) {
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.field = gameField;
        this.usedActionTiles = usedSpecialTokens;
        this.tokenPouch = tokenPouch;
    }

    /**
     * Gets the data of the players.
     *
     * @return Array containing data of the players.
     */
    public PlayerData[] getPlayers() {
        return players;
    }

    /**
     * Gets index of the current player.
     *
     * @return Index of the current player.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter for the field of the game.
     *
     * @return The field of the game
     */
    public int[][] getField() {
        return field;
    }

    /**
     * Gets the used special tokens.
     *
     * @return The used special tokens.
     */
    public int[] getUsedActionTiles() {
        return usedActionTiles;
    }

    /**
     * Gets the token pouch.
     *
     * @return The token pouch.
     */
    public int[] getTokenPouch() {
        return tokenPouch;
    }


}
