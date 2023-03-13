package logic;

import java.util.List;

/**
 * Interface used for the logic of the game to communicate with the
 * gui.
 *
 * @author Florian Kern/cgt104661
 */

public class FakeGUI implements GUIConnector {

    /**
     * Displays a token at specified cell of the field.
     *
     * @param coord - The coordinates in the field where the token should be displayed.
     * @param token - The token
     */
    @Override
    public void displayTokenOnField(GamePositions coord, Tokens token) {

    }

    /**
     * Sets the pause time for the PauseTransition.
     * @param pauseTime - The pause time.
     */
    @Override
    public void setPauseTime(int pauseTime) {

    }

    /**
     * Resets all changes that have been made to the drag-and-drop options.
     */
    public void resetDragAndDrop() {

    }

    /**
     * Changes an image of the playerhand to an empty one.
     *
     * @param pos Position in the hand of the player
     */
    @Override
    public void removeFromPlayerHand(GamePositions pos) {

    }

    /**
     * Sets the total score for a team.
     *
     * @param score - The score
     * @param team  - The team
     */
    @Override
    public void setTotalScore(int score, Team team) {

    }

    /**
     * Disables drag-detection for special tokens on the hand of the player.
     */
    @Override
    public void disableDragSpecialTokens() {

    }

    /**
     * Deactivates drag-detection for empty fields on the board.
     */
    @Override
    public void deactivateDragOverOnEmptyFields() {

    }

    /**
     * Deactivates drag-detection for non-empty fields .
     */
    @Override
    public void deactivateDragOverOnNonEmptyFields() {

    }


    /**
     * Displays the field of the game.
     *
     * @param gameField The field of the game.
     */
    @Override
    public void displayField(Tokens[][] gameField) {

    }

    /**
     * Updates the amount of used special tokens on the gui.
     * @param token - The special token
     * @param amount - The amount the special token has been used
     */
    @Override
    public void updateAmountUsedSpecialTokens(Tokens token, int amount) {

    }


    /**
     * Sets the name and displays the hand of the current player on the gui.
     *
     * @param name - Name of the current player.
     * @param playerHand - The hand of the current player.
     */
    @Override
    public void setCurrPlayer(String name, Team team ,List<Tokens> playerHand, boolean isAI) {

    }

    /**
     * Method used when the game is over and a team has won the game.
     *
     * @param team - The winning team.
     */
    @Override
    public void endOfGame(Team team, int[] horizontalPoints, int[] verticalPoints, boolean isWinningSix) {

    }

    /**
     * Sets a pause after an AI has done its turn.
     *
     * @param game - The game
     * @param pos - The position where a token has been placed.
     * @param draggedFrom - The position from where the token got placed.
     */
    @Override
    public void waitForNextTurn(GameCrosswise game, GamePositions pos, GamePositions draggedFrom) {

    }

    /**
     * Closes the game over window when a new game has been called.
     */
    @Override
    public void closeWindows() {

    }

    /**
     * Sets the points for the labels on the rows and columns of the field.
     *
     * @param horizontalPoints - Array that contains the points for the labels of the horizontal points
     * @param verticalPoints   - Array that contains the points for the labels of the vertical points
     */
    @Override
    public void setPointLabels(int[] horizontalPoints, int[] verticalPoints) {

    }

    /**
     * Sets information for what to do after a special token has been played.
     *
     * @param specialToken - The special token
     */
    @Override
    public void setSpecialTokenInfo(Tokens specialToken ) {

    }

}
