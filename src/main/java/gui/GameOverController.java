package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logic.Team;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class for the window that gets displayed at the end of the game.
 *
 * @author Florian Kern/cgt104661
 */
public class GameOverController implements Initializable {


    /**
     * The vertical score
     */
    private int scoreVertical;

    /**
     * The horizontal score
     */
    private int scoreHorizontal;

    /**
     * Flag for displaying if a game has been won by sixes or not
     */
    private boolean isWinningSix;

    /**
     * The winning team
     */
    private Team winningTeam;


    /**
     * Root container of the window
     */
    @FXML
    private HBox root;

    /**
     * Label for the winning team
     */
    private Label lblWinningTeam;

    /**
     * The reason why a game has been won (points, tie or win of sixes)
     */
    private Label lblReasonWin;

    /**
     * Label for the points of the vertical team
     */
    private Label lblPointsVerticalTeam;

    /**
     * Label for the points of the horizontal team
     */
    private Label lblPointsHorizontalTeam;

    /**
     * Label for the headline of the window
     */
    private Label lblHeadLine;

    /**
     * The grid of the window
     */
    private GridPane grid;

    /**
     * Constructor for the window.
     *
     * @param root - Root container of the window
     * @param scoreVertical - The vertical score
     * @param scoreHorizontal - The horizontal score
     * @param isWinningSix - Flag for displaying if a game has been won by sixes or not
     * @param winningTeam - The winning Team
     */
    GameOverController(HBox root, int scoreVertical, int scoreHorizontal, boolean isWinningSix, Team winningTeam) {
        this.winningTeam = winningTeam;
        this.scoreHorizontal = scoreHorizontal;
        this.scoreVertical = scoreVertical;
        this.isWinningSix = isWinningSix;
        this.root = root;

        // Looking children of the root container using their fx-id
        this.lblWinningTeam = (Label) root.lookup("#lblWinningTeam");
        this.lblReasonWin = (Label) root.lookup("#lblWinReason");
        this.lblPointsVerticalTeam = (Label) root.lookup("#lblVertPoints");
        this.lblPointsHorizontalTeam = (Label) root.lookup("#lblHorPoints");
        this.lblHeadLine = (Label) root.lookup("#lblHeadLine");
        this.grid = (GridPane) root.lookup("#grid");
    }


    /**
     * Initializes the window.
     * @param url - unused
     * @param resourceBundle - unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting background color and border style of grid
        this.grid.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.grid.setStyle("-fx-border-color: rgb(0,0,0); -fx-border-style: solid; -fx-border-width: 1px");
        // Setting points
        lblPointsHorizontalTeam.setText(String.valueOf(this.scoreHorizontal));
        lblPointsVerticalTeam.setText(String.valueOf(this.scoreVertical));
        // Setting reason for win
        if (this.isWinningSix) {
            lblReasonWin.setText("Win by sixes");
        } else {
            lblReasonWin.setText("Win by points");
        }
        if (this.winningTeam == Team.NONE) {
            // Making certain parts invisible if a tie occurred
            lblWinningTeam.setVisible(false);
            lblReasonWin.setVisible(false);
            lblHeadLine.setText("Tie!");
            // Setting winner
        } else if (this.winningTeam == Team.VERTICAL) {
            lblWinningTeam.setText("Vertical Team");
        } else {
            lblWinningTeam.setText("Horizontal Team");
        }
    }

}
