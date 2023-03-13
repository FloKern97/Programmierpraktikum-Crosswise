package logic;

/**
 * Class for creating an AI turn.
 *
 * @author Florian Kern/cgt104661
 */
public class AITurn {

    /**
     * The position where the AI places a token
     */
    private GamePositions pos;

    /**
     * The position from where the AI placed a token
     */
    private GamePositions draggedFrom;

    /**
     * The token the AI sets.
     */
    private Tokens token;

    /**
     * The position of the token in the hand of the player
     */
    private Integer handPos;

    /**
     * Flag if a turn was a follow-up turn after a move-token, swap-with-hand-token or
     * swap-on-board-token has been placed
     */
    private boolean isSpecialTurn;

    /**
     * Flag for determining if a turn lead to a win of sixes or not
     */
    private boolean isWinningSixTurn;

    /**
     * Flag for determining if a turn prevented a possible win of six of the opponent
     */
    private boolean preventsSixOpponent;

    /**
     * The used special token for the turn
     */
    private Tokens usedSpecialToken;

    /**
     * The points the turn generated
     */
    private Integer generatedPoints;

    /**
     * The points of the opponent team
     */
    private Integer opponentPoints;

    /**
     * Constructor for creating an AITurn.
     *
     * @param pos                 - The position where the AI places a token
     * @param token               - The token the AI sets.
     * @param handPos             - The position of the token in the hand of the player
     * @param isSpecialTurn       - Flag if a turn was a follow-up turn after a move-token, swap-with-hand-token or
     *                            swap-on-board-token has been placed
     * @param isWinningSixTurn    - Flag for determining if a turn lead to a win of sixes or not
     * @param usedSpecialToken    - The used special token for the turn
     * @param generatedPoints     - The points the turn generated
     * @param opponentPoints      - The points of the opponent team
     * @param preventsSixOpponent - Flag for determining if a turn prevented a possible win of six of the opponent
     * @param draggedFrom         - The position from where the AI placed a token
     */
    AITurn(GamePositions pos, Tokens token, Integer handPos,
           boolean isSpecialTurn, boolean isWinningSixTurn, Tokens usedSpecialToken, Integer generatedPoints,
           Integer opponentPoints, boolean preventsSixOpponent, GamePositions draggedFrom) {
        this.pos = pos;
        this.token = token;
        this.handPos = handPos;
        this.isSpecialTurn = isSpecialTurn;
        this.usedSpecialToken = usedSpecialToken;
        this.generatedPoints = generatedPoints;
        this.opponentPoints = opponentPoints;
        this.isWinningSixTurn = isWinningSixTurn;
        this.preventsSixOpponent = preventsSixOpponent;
        this.draggedFrom = draggedFrom;
    }

    /**
     * Getter for the points of the opponent team.
     *
     * @return The points of the opponent team
     */
    public int getOpponentPoints() {
        return opponentPoints;
    }

    /**
     * Gets the position where the token has been placed.
     *
     * @return The position of the placed token.
     */
    public GamePositions getPos() {
        return pos;
    }

    /**
     * Gets the played token.
     *
     * @return The played token
     */
    public Tokens getToken() {
        return token;
    }

    /**
     * Gets the handposition of the played token.
     *
     * @return The handposition
     */
    public Integer getHandPos() {
        return handPos;
    }

    /**
     * Tells if the turn was a special turn.
     *
     * @return - true if turn was special, false if not
     */
    public boolean isSpecialTurn() {
        return isSpecialTurn;
    }

    /**
     * Gets the used special token.
     *
     * @return - The used special token
     */
    public Tokens getUsedSpecialToken() {
        return usedSpecialToken;
    }

    /**
     * Gets the generated points.
     *
     * @return - The generated points
     */
    public int getGeneratedPoints() {
        return generatedPoints;
    }

    /**
     * Tells if a turn lead to a win of sixes.
     *
     * @return true if turn lead to win of sixes, false if not
     */
    public boolean isWinningSixTurn() {
        return isWinningSixTurn;
    }

    /**
     * Tells if a turn prevented a possible win of sixes for the opponent team.
     *
     * @return - true, if turn prevented possible win of sixes, false if not
     */
    public boolean isPreventsSixOpponent() {
        return preventsSixOpponent;
    }

    /**
     * Gets the position from where a token has been dragged.
     *
     * @return The position from where the token was dragged
     */
    public GamePositions getDraggedFrom() {
        return draggedFrom;
    }
}
