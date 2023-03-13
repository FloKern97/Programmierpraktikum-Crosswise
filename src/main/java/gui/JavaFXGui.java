package gui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.*;

import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for changing the gui when the logic deems it
 * necessary.
 *
 * @author Florian Kern/cgt104661
 */
public class JavaFXGui implements GUIConnector {

    /**
     * An Array of ImageViews for the field.
     */
    private ImageView[][] imgViewField;

    /**
     * An Array of ImageViews for the hand of the player.
     */
    private ImageView[][] imgViewPlayerHand;

    /**
     * The score of the vertical team.
     */
    private Label scoreVertical;

    /**
     * The score of the horizontal team.
     */
    private Label scoreHorizontal;

    /**
     * Label for general info.
     */
    private Label lblInfo;

    /**
     * Labels for the displayed points at the columns.
     */
    private Label[] pointsHorizontal;

    /**
     * Labels for the displayed points at the rows.
     */
    private Label[] pointsVertical;

    /**
     * The grid of the field.
     */
    private GridPane fieldPane;

    /**
     * The grid of the player hand.
     */
    private GridPane playerHandPane;

    private RadioMenuItem mnuItemAIHandOnOff;


    private Label usedRemoves;
    private Label usedMoves;
    private Label usedSwapOnBoards;
    private Label usedSwapWithHands;
    private Stage stage;
    private int pauseTimeInMills = 2000;

    /**
     * Constructor. Receives all components of the gui that may change because of actions in the logic.
     *
     * @param imgViewField         - The imageViews for the field
     * @param imgViewPlayerHand    - The imageViews for the player hand
     * @param scoreVertical        - The label for the points of the vertical team
     * @param scoreHorizontal      -The label for the points of the horizontal team
     * @param lblInfo              - The label for general info, e.g. the current player
     * @param pointsHorizontal     - Array of labels for the displayed points at the columns of the field
     * @param pointsVertical       - Array of labels for the displayed points at the rows of the field
     * @param fieldPane            - The gridPane of the field
     * @param playerHandPane       - The gridPane of the player hand
     * @param lblUsedMoves         - The label for the used move tokens
     * @param lblUsedRemoves       - The label for the used remove tokens
     * @param lblUsedSwapOnBoards  - The label for the used Swap On Board tokens
     * @param lblUsedSwapWithHands - The label for the used Swap With Hand tokens
     * @param mnuItemAIHandOnOff   - Menu item that controls visibility of the hand of the AI
     */
    public JavaFXGui(ImageView[][] imgViewField, ImageView[][] imgViewPlayerHand,
                     Label scoreVertical, Label scoreHorizontal,
                     Label lblInfo, Label[] pointsHorizontal, Label[] pointsVertical,
                     GridPane fieldPane, GridPane playerHandPane, Label lblUsedRemoves, Label lblUsedMoves,
                     Label lblUsedSwapOnBoards, Label lblUsedSwapWithHands, RadioMenuItem mnuItemAIHandOnOff) {

        this.imgViewField = imgViewField;
        this.imgViewPlayerHand = imgViewPlayerHand;
        this.scoreVertical = scoreVertical;
        this.scoreHorizontal = scoreHorizontal;
        this.lblInfo = lblInfo;
        this.pointsHorizontal = pointsHorizontal;
        this.pointsVertical = pointsVertical;
        this.fieldPane = fieldPane;
        this.playerHandPane = playerHandPane;
        this.usedRemoves = lblUsedRemoves;
        this.usedMoves = lblUsedMoves;
        this.usedSwapOnBoards = lblUsedSwapOnBoards;
        this.usedSwapWithHands = lblUsedSwapWithHands;
        this.mnuItemAIHandOnOff = mnuItemAIHandOnOff;


    }


    /**
     * Displays a token at specified cell of the field.
     *
     * @param coord - The coordinates in the field where the token should be displayed.
     * @param token - The token, must be a non-special token, since it can not be placed on the field.
     */
    @Override
    public void displayTokenOnField(GamePositions coord, Tokens token) {
        if (token != Tokens.MOVE_STONE && token != Tokens.REMOVE_STONE
                && token != Tokens.SWAP_ON_BOARD && token != Tokens.SWAP_WITH_HAND) {
            this.imgViewField[coord.getX()][coord.getY()].setImage(new Image(Tokens.tokenToURL(token)));
        }
    }


    /**
     * Sets the pause time for the PauseTransition.
     *
     * @param pauseTime - The pause time.
     */
    @Override
    public void setPauseTime(int pauseTime) {
        this.pauseTimeInMills = pauseTime;
    }


    /**
     * Sets the name and displays the hand of the current player on the gui.
     *
     * @param name       - Name of the current player
     * @param playerHand - The hand of the current player
     * @param team       - Team of the current player
     * @param isAI       - Is the current player an AI?
     */
    @Override
    public void setCurrPlayer(String name, Team team, List<Tokens> playerHand, boolean isAI) {
        String playerText = name + " (" + team.toString().toLowerCase() + " team" + ")";
        lblInfo.setText("Current player: " + playerText);
        if (this.mnuItemAIHandOnOff.isSelected() && isAI) {
            this.playerHandPane.setVisible(false);
        } else {
            this.playerHandPane.setVisible(true);
            for (int i = 0; i < playerHand.size(); i++) {
                this.imgViewPlayerHand[i][0].setImage(new Image(Tokens.tokenToURL(playerHand.get(i))));
            }
        }
    }


    /**
     * Method used when the game is over and a team has won the game.
     *
     * @param team             - The winning team.
     * @param horizontalPoints - Points of the horizontal team
     * @param verticalPoints   - Points of the vertical team
     * @param isWinningSix     - Has a game been won by sixes?
     */
    @Override
    public void endOfGame(Team team, int[] horizontalPoints, int[] verticalPoints,
                          boolean isWinningSix) {
        int scoreHorizontal = 0;
        int scoreVertical = 0;
        for (int i = 0; i < horizontalPoints.length; i++) {
            scoreHorizontal += horizontalPoints[i];
            scoreVertical += verticalPoints[i];
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameEnd.fxml"));
        GameOverController controller;
        HBox hBox;
        try {
            hBox = loader.load();
            controller = new GameOverController(hBox, scoreVertical, scoreHorizontal, isWinningSix, team);
            loader.setController(controller);
            controller.initialize(null, null);

            Scene scene = new Scene(hBox, 500, 500);
            this.stage = new Stage();
            stage.setTitle("Game Over");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


        this.fieldPane.getParent().setDisable(true);
        this.playerHandPane.getParent().setDisable(true);

    }

    /**
     * Sets a pause after an AI has finished its turn.
     *
     * @param game        - The game
     * @param pos         - The position where a token has been placed.
     * @param draggedFrom - The position from where the token got placed.
     */
    @Override
    public void waitForNextTurn(GameCrosswise game, GamePositions pos, GamePositions draggedFrom) {
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(pauseTimeInMills));
        pauseTransition.setOnFinished(event -> {
            // Disabling effects and cause an AI turn if next player is an AI
            this.imgViewField[pos.getX()][pos.getY()].setEffect(null);
            if (draggedFrom != null)
                this.imgViewField[draggedFrom.getX()][draggedFrom.getY()].setEffect(null);
            game.causeTurn();
        });

        // Creating and assigning shadow on handed positions
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        shadow.setHeight(50);
        shadow.setWidth(50);
        shadow.setColor(Color.RED);
        this.imgViewField[pos.getX()][pos.getY()].setEffect(shadow);
        if (draggedFrom != null) {
            this.imgViewField[draggedFrom.getX()][draggedFrom.getY()].setEffect(shadow);
        }
        pauseTransition.play();
    }

    /**
     * Closes the game over window when a new game has been called.
     */
    @Override
    public void closeWindows() {
        if (stage != null) {
            this.stage.close();
        }
    }

    /**
     * Sets the points for the labels on the rows and columns of the field.
     *
     * @param horizontalPoints - Array that contains the points for the labels of the horizontal points
     * @param verticalPoints   - Array that contains the points for the labels of the vertical points
     */
    @Override
    public void setPointLabels(int[] horizontalPoints, int[] verticalPoints) {
        for (int i = 0; i < this.pointsHorizontal.length; i++) {
            this.pointsHorizontal[i].setText(String.valueOf(horizontalPoints[i]));
            this.pointsVertical[i].setText(String.valueOf(verticalPoints[i]));
        }
    }

    /**
     * Sets information for what to do after a special token has been played.
     *
     * @param specialToken - The special token
     */
    @Override
    public void setSpecialTokenInfo(Tokens specialToken) {
        switch (specialToken) {
            case MOVE_STONE -> this.lblInfo.setText("Pick a token and the position you want it to be moved to.");
            case SWAP_ON_BOARD -> this.lblInfo.setText("Choose two tokens you would like to switch positions.");
            case SWAP_WITH_HAND -> lblInfo.setText("Pick a token from your hand and swap it with a token on the field.");
        }
    }


    /**
     * Disables drag-detection for special tokens on the hand of the player.
     */
    public void disableDragSpecialTokens() {
        for (ImageView[] imageViews : this.imgViewPlayerHand) {
            if (Tokens.isSpecialToken(Tokens.getTokenFromPicture(imageViews[0].getImage()))) {
                imageViews[0].setOnDragDetected(null);
            }
        }
    }


    /**
     * Resets all changes that have been made to the drag-and-drop options.
     */
    public void resetDragAndDrop() {
        playerHandPane.setDisable(false);
        for (ImageView[] imageViews : this.imgViewField) {
            for (ImageView imageView : imageViews) {
                imageView.setOnDragDetected(null);
            }
        }

    }


    /**
     * Changes an image of the player hand to an empty one.
     *
     * @param pos Position in the hand of the player
     */
    @Override
    public void removeFromPlayerHand(GamePositions pos) {
        this.imgViewPlayerHand[pos.getX()][pos.getY()].setImage(new Image(Tokens.tokenToURL(Tokens.EMPTY)));
    }

    /**
     * Sets the total score for a team.
     *
     * @param score - The score
     * @param team  - The team
     */
    @Override
    public void setTotalScore(int score, Team team) {
        if (team == Team.VERTICAL) {
            scoreVertical.setText("Vertical score: " + score);
        } else {
            scoreHorizontal.setText("Horizontal score: " + score);
        }
    }


    /**
     * Method deactivates the option to drag over empty fields.
     */
    public void deactivateDragOverOnEmptyFields() {
        for (ImageView[] imageViews : this.imgViewField) {
            for (ImageView imageView : imageViews) {
                // removing dragging over fields if they are empty
                if (Tokens.getTokenFromPicture(imageView.getImage()) == Tokens.EMPTY) {
                    imageView.setOnDragOver(null);
                }
            }
        }
    }


    /**
     * Method deactivates the option to drag over non-empty fields.
     */
    public void deactivateDragOverOnNonEmptyFields() {
        for (ImageView[] imageViews : this.imgViewField) {
            for (ImageView imageView : imageViews) {
                // removing dragging over fields if they are not empty
                if (Tokens.getTokenFromPicture(imageView.getImage()) != Tokens.EMPTY) {
                    imageView.setOnDragOver(null);
                }
            }
        }
    }

    /**
     * Displays the field of the game.
     *
     * @param gameField The field of the game.
     */
    @Override
    public void displayField(Tokens[][] gameField) {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                this.imgViewField[i][j].setImage(new Image(Tokens.tokenToURL(gameField[i][j])));
            }
        }
    }

    /**
     * Updates the amount of used special tokens on the gui.
     *
     * @param token  - The special token
     * @param amount - The amount the special token has been used
     */
    @Override
    public void updateAmountUsedSpecialTokens(Tokens token, int amount) {
        switch (token) {
            case REMOVE_STONE -> this.usedRemoves.setText(amount + "/3");
            case MOVE_STONE -> this.usedMoves.setText(amount + "/3");
            case SWAP_ON_BOARD -> this.usedSwapOnBoards.setText(amount + "/3");
            case SWAP_WITH_HAND -> this.usedSwapWithHands.setText(amount + "/3");
        }
    }

}
