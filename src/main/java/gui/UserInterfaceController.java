package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main class for the user interface.
 *
 * @author mjo, cei, Florian Kern/cgt104661
 */
public class UserInterfaceController implements Initializable {


    /**
     * MenuItem for displaying the AIHand.
     */
    @FXML
    private RadioMenuItem mnuItemAIHandOnOff;

    /**
     * Label for used remover tokens
     */
    @FXML
    private Label lblUsedRemoves;

    /**
     * Label for used move tokens
     */
    @FXML
    private Label lblUsedMoves;

    /**
     * Label for used swap on board tokens
     */
    @FXML
    private Label lblUsedSwapOnBoards;

    /**
     * Label for used swap with hand tokens
     */
    @FXML
    private Label lblUsedSwapWithHands;

    /**
     * Grid for the used special tokens.
     */
    @FXML
    private GridPane specialTokenGrid;


    /**
     * The VBox that contains the grid for the used special tokens and the team scores.
     */
    @FXML
    private VBox vBoxInfo;

    /**
     * Main-container for the field.
     */
    @FXML
    private BorderPane mainContainer;

    /**
     * VBox for the mainContainer, used to properly resize the window.
     */
    @FXML
    private VBox VBoxGridContainer;

    /**
     * HBox for the main-container, used to properly resize the window.
     */
    @FXML
    private HBox HBoxGridContainer;

    /**
     * First text-field for the vertical team.
     */
    @FXML
    private TextField txtFieldVertical1;

    /**
     * Second text-field for the vertical team.
     */
    @FXML
    private TextField txtFieldVertical2;

    /**
     * First text-field for the horizontal team.
     */
    @FXML
    private TextField txtFieldHorizontal1;

    /**
     * Second text-field for the horizontal team.
     */
    @FXML
    private TextField txtFieldHorizontal2;

    /**
     * Label used to display general info of the game,
     * displays the current player or what to do if a special token is played.
     */
    @FXML
    private Label lblInfo;

    /**
     * Label used to display the score for the vertical team.
     */
    @FXML
    private Label lblVerticalScore;

    /**
     * Label used to display the score for the horizontal team.
     */
    @FXML
    private Label lblHorizontalScore;


    /**
     * Menu-item to choose if two players are playing the game.
     */
    @FXML
    private CheckMenuItem menuItemPlayers2;

    /**
     * Menu-item to choose if four players are playing the game.
     */
    @FXML
    private CheckMenuItem menuItemPlayers4;

    /**
     * First checkbox to use an AI for a player of the vertical team.
     */
    @FXML
    private CheckBox checkBoxAIVertical1;

    /**
     * Second checkbox to use an AI for a player of the vertical team.
     */
    @FXML
    private CheckBox checkBoxAIVertical2;

    /**
     * First checkbox to use an AI for a player of the horizontal team.
     */
    @FXML
    private CheckBox checkBoxAIHorizontal1;

    /**
     * Second checkbox to use an AI for a player of the horizontal team.
     */
    @FXML
    private CheckBox checkBoxAIHorizontal2;

    /**
     * Menu to access the options of how many players are playing.
     */
    @FXML
    private Menu menuNumOfPlayers;

    /**
     * Button for starting the game.
     */
    @FXML
    private Button btnStartGame;

    /**
     * Button for starting a new game.
     */
    @FXML
    private MenuItem menuItemNewGame;

    /**
     * Menu-item to toggle the scoreboard on and off.
     */
    @FXML
    private RadioMenuItem radioMenuItemScoreBoard;

    /**
     * Menu-item to toggle the individual points for a row/column on and off.
     */
    @FXML
    private RadioMenuItem radioMenuItemPointsAtRowsCols;

    /**
     * The menuItem for choosing a short pause
     */
    @FXML
    private RadioMenuItem mnuItemShortTurn;

    /**
     * The menuItem for choosing a medium long pause
     */
    @FXML
    private RadioMenuItem mnuItemMiddleTurn;

    /**
     * The menuItem for choosing a long pause
     */
    @FXML
    private RadioMenuItem mnuItemLongTurn;

    /**
     * Label-array that gets used to assign labels to the griPane of the individual points of a column.
     */
    @FXML
    private Label[] horizontalPointLabels;

    /**
     * Label-array that gets used to assign labels to the gridPane of the individual points of a row.
     */
    @FXML
    private Label[] verticalPointLabels;

    /**
     * ImageView-array for the images on the field.
     */
    private ImageView[][] imgViewFieldGUI;

    /**
     * ImageView-array for the images on the hand of a player.
     */
    private ImageView[][] imgViewPlayerHandGUI;

    /**
     * GridPane for the individual points of column.
     */
    private GridPane pointBorderHorizontal;

    /**
     * GridPane for the individual points of row.
     */
    private GridPane pointBorderVertical;

    /**
     * GridPane for the hand of a player.
     */
    private GridPane playerHand;

    /**
     * The board of the game.
     */
    private GridPane board;

    /**
     * The grid for the board.
     */
    private GridPane gridContainerBoard;

    /**
     * Variable to handle gaps between cells
     */
    private final int CELL_FIT = 5;

    /**
     * Position of a dragged image.
     */
    private GamePositions sourceNodePos = null;

    /**
     * The amount of current phases of a turn.
     */
    private int phaseOfTurn = 0;

    /**
     * Boolean to determine if a special token has been used.
     */
    private boolean isSpecialTurn = false;

    /**
     * The special token that has been used.
     */
    private Tokens usedSpecialToken = null;

    /**
     * The size of the field.
     */
    private final int FIELD_SIZE = 6;

    /**
     * The length of short pause between a turn after an AITurn in milliseconds.
     */
    private final int SHORTPAUSE_IN_MILLS = 1000;

    /**
     * The length of medium pause between a turn after an AITurn in milliseconds.
     */
    private final int MIDDLEPAUSE_IN_MILLS = 2000;
    /**
     * The length of long pause between a turn after an AITurn in milliseconds.
     */
    private final int LONGPAUSE_IN_MILLS = 3000;


    /**
     * The game.
     */
    private GameCrosswise game = null;

    /**
     * The gui of the game.
     */
    private JavaFXGui gui;

    /**
     * Method initializes the game. Builds the field on which the game is played,
     * using a number of different gridPanes. Binds various objects to other objects as needed.
     * Assigns ImageViews to the field of the game and the hands of the players, so that images can be
     * used to play the game via drag-and-drop. Finally, it turns certain parts of the interface that are not needed
     * at this stage of the game invisible.
     *
     * @param location  unused
     * @param resources unused
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // binding height and width to the main container and centering it
        this.HBoxGridContainer.prefHeightProperty().bind(this.mainContainer.widthProperty());
        this.HBoxGridContainer.prefWidthProperty().bind(this.mainContainer.widthProperty());
        this.HBoxGridContainer.alignmentProperty().set(Pos.CENTER);
        // binding width and height to HBox
        this.VBoxGridContainer.prefWidthProperty().bind(this.HBoxGridContainer.heightProperty());
        this.VBoxGridContainer.prefHeightProperty().bind(this.HBoxGridContainer.widthProperty());

        // constructing all needed grids
        this.pointBorderHorizontal = initGridPane(1, FIELD_SIZE);
        this.pointBorderVertical = initGridPane(FIELD_SIZE, 1);
        this.playerHand = initGridPane(1, 4);
        this.board = initField();
        // Main container for the field
        GridPane mainFieldContainer = initCustomGridContainer(new int[]{100}, new int[]{85, 15});
        // GridPanes for the visual support if the players do not know what vertical or horizontal means
        GridPane gridHorizontalHelp = initGridPane(1, FIELD_SIZE);
        GridPane gridVerticalHelp = initGridPane(FIELD_SIZE, 1);
        this.gridContainerBoard = initCustomGridContainer(new int[]{10, 80, 10}, new int[]{10, 80, 10});


        // Assigning properties to the board of the game
        this.board.setVgap(20);
        this.board.setId("gridGameBoard");
        mainFieldContainer.setVgap(30);


        HBox hBox = new HBox();
        VBox vBox = new VBox();


        // Adding gridPanes into other gridPanes and binding them to the parent gridPane
        this.gridContainerBoard.add(this.board, 1, 1);
        this.gridContainerBoard.add(this.pointBorderHorizontal, 1, 2);
        this.gridContainerBoard.add(this.pointBorderVertical, 2, 1);
        this.gridContainerBoard.prefWidthProperty().bind(this.HBoxGridContainer.widthProperty());
        this.gridContainerBoard.prefHeightProperty().bind(this.HBoxGridContainer.widthProperty());
        this.horizontalPointLabels = new Label[FIELD_SIZE];
        this.verticalPointLabels = new Label[FIELD_SIZE];
        // Adding labels to point borders
        addLabelToPointBorders(this.pointBorderHorizontal, Team.HORIZONTAL);
        addLabelToPointBorders(this.pointBorderVertical, Team.VERTICAL);
        // Adding the container of the main field into VBox and centering VBox
        VBoxGridContainer.getChildren().add(mainFieldContainer);
        VBoxGridContainer.alignmentProperty().set(Pos.CENTER);
        // Binding main container for the field to VBox
        mainFieldContainer.prefWidthProperty().bind(this.VBoxGridContainer.widthProperty());
        mainFieldContainer.prefHeightProperty().bind(this.VBoxGridContainer.widthProperty());
        mainFieldContainer.add(hBox, 0, 0);
        hBox.getChildren().add(vBox);
        hBox.alignmentProperty().set(Pos.CENTER);
        vBox.getChildren().add(this.gridContainerBoard);
        vBox.prefHeightProperty().bind(hBox.widthProperty());
        vBox.prefWidthProperty().bind(hBox.heightProperty());
        this.gridContainerBoard.add(gridHorizontalHelp, 1, 0);
        this.gridContainerBoard.add(gridVerticalHelp, 0, 1);

        gridVerticalHelp.prefWidthProperty().bind(this.gridContainerBoard.widthProperty());
        gridHorizontalHelp.prefHeightProperty().bind(this.gridContainerBoard.heightProperty());
        this.gridContainerBoard.setHgap(5);
        this.gridContainerBoard.setVgap(20);


        initImages(gridHorizontalHelp, 12);
        initImages(gridVerticalHelp, 11);

        HBox playerHandBox = new HBox();
        mainFieldContainer.add(playerHandBox, 0, 1);
        playerHandBox.getChildren().add(this.playerHand);
        playerHandBox.alignmentProperty().set(Pos.CENTER);
        // Binding height and width of the gridPane that belongs to the hand of the player
        this.playerHand.prefHeightProperty().bind(playerHandBox.heightProperty());
        this.playerHand.prefWidthProperty().bind(this.playerHand.heightProperty().multiply(4).add(CELL_FIT));
        this.specialTokenGrid.prefWidthProperty().bind(vBoxInfo.widthProperty());
        initSpecialTokenGridPictures(specialTokenGrid);
        // Assigning imageViews to the field and the hand of the player
        this.imgViewFieldGUI = initImages(this.board, 0);
        this.imgViewPlayerHandGUI = initImages(this.playerHand, 0);

        // Turning components that are not needed at this stage of the game invisible or not accessible
        this.lblHorizontalScore.setVisible(false);
        this.lblVerticalScore.setVisible(false);

        this.menuItemNewGame.setDisable(true);
        this.pointBorderHorizontal.setVisible(false);
        this.pointBorderVertical.setVisible(false);
        this.lblInfo.setText("Crosswise!");


    }

    /**
     * Creates an array of imageViews corresponding to the gridPane.
     * Each imageView becomes a child of the gridPane and fills a cell.
     * For proper resizing it is bound to the gridPanes width and height.
     *
     * @param grdPn        - the gridPane
     * @param imageOrdinal - ordinal of the image
     * @return an array of imageViews added to the gridPane
     */
    private ImageView[][] initImages(GridPane grdPn, int imageOrdinal) {
        int colCount = grdPn.getColumnCount();
        int rowCount = grdPn.getRowCount();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];
        int cellWidth = (int) grdPn.getWidth() / colCount;
        int cellHeight = (int) grdPn.getHeight() / rowCount;
        // bind each Imageview to a cell of the gridPane
        for (int x = 0; x < colCount; x++) {
            for (int y = 0; y < rowCount; y++) {
                //creates an empty imageview
                imageViews[x][y] = new ImageView();
                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);

                //add the imageview to the cell
                grdPn.add(imageViews[x][y], x, y);

                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(grdPn.widthProperty().divide(colCount).subtract(CELL_FIT));
                imageViews[x][y].fitHeightProperty().bind(grdPn.heightProperty().divide(rowCount).subtract(CELL_FIT));
                // construct random number to add a random picture to the cell using the ordinalToPicture method.
                imageViews[x][y].setImage(new Image(ordinalToPicture(imageOrdinal)));
            }
        }
        return imageViews;
    }


    /**
     * Creates an array of imageViews corresponding to the gridPane.
     * Each imageView becomes a child of the gridPane and fills a cell.
     * For proper resizing it is bound to the gridPanes width and height.
     *
     * @param grdPn - The gridPane
     *
     * @return an array of imageViews added to the gridPane
     */
    private ImageView[][] initSpecialTokenGridPictures(GridPane grdPn) {
        int colCount = grdPn.getColumnCount();
        int rowCount = grdPn.getRowCount();
        int pictureOrdinal = 7;
        ImageView[][] imageViews = new ImageView[colCount][rowCount];
        int cellWidth = (int) grdPn.getWidth() / colCount;
        int cellHeight = (int) grdPn.getHeight() / rowCount;
        // bind each Imageview to a cell of the gridPane
        for (int x = 0; x < colCount; x++) {
            //creates an empty imageview
            imageViews[x][1] = new ImageView();
            //image has to fit a cell and mustn't preserve ratio
            imageViews[x][1].setFitWidth(cellWidth);
            imageViews[x][1].setFitHeight(cellHeight);
            imageViews[x][1].setPreserveRatio(false);
            imageViews[x][1].setSmooth(true);

            //add the imageview to the cell
            grdPn.add(imageViews[x][1], x, 1);

            //the image shall resize when the cell resizes
            imageViews[x][1].fitWidthProperty().bind(vBoxInfo.widthProperty().divide(colCount).subtract(CELL_FIT));
            imageViews[x][1].fitHeightProperty().bind(vBoxInfo.widthProperty().divide(rowCount).subtract(CELL_FIT));
            // construct random number to add a random picture to the cell using the ordinalToPicture method.

            imageViews[x][1].setImage(new Image(ordinalToPicture(pictureOrdinal++)));
        }
        return imageViews;
    }


    /**
     * Method builds a GridPane that gets used as the field of the game.
     *
     * @return A GridPane for the field of the game
     */
    private GridPane initField() {
        GridPane gridpane = new GridPane();
        for (int x = 0; x < FIELD_SIZE; x++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(2);
            gridpane.getColumnConstraints().add(col);
            RowConstraints row = new RowConstraints();
            row.setMinHeight(2);
            gridpane.getRowConstraints().add(row);
            gridpane.setHgap(CELL_FIT);
            gridpane.setVgap(CELL_FIT);
            gridpane.setAlignment(Pos.CENTER);
        }
        return gridpane;
    }


    /**
     * Method for building a GridPane whose columns and rows take up
     * a certain percentage of the GridPane.
     *
     * @param colConstraint - Array that contains the percentage of how much space a column uses.
     * @param rowConstraint - Array that contains the percentage of how much space a row uses.
     * @return A GridPane.
     */
    private GridPane initCustomGridContainer(int[] colConstraint, int[] rowConstraint) {
        GridPane gridpane = new GridPane();
        for (int i : colConstraint) {
            // Adding columns and assigning their percentage of usable space
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(i);
            gridpane.getColumnConstraints().add(col);
        }
        for (int i : rowConstraint) {
            // Adding rows and assigning their percentage of usable space
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(i);
            gridpane.getRowConstraints().add(row);
        }
        // Centering the GridPane
        gridpane.setAlignment(Pos.CENTER);
        return gridpane;
    }

    /**
     * Adds labels to a GridPane and adds those labels to an array, so they can be accessed.
     *
     * @param grid - The grid
     * @param team - The team, used to decide to which array the labels are added.
     */
    private void addLabelToPointBorders(GridPane grid, Team team) {
        if (team == Team.VERTICAL) {
            for (int i = 0; i < grid.getRowCount(); i++) {
                Label label = new Label("0");
                // Creating HBox for proper display of the label
                HBox hBox = new HBox();
                // Setting properties of the HBox
                hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                hBox.setAlignment(Pos.CENTER);
                hBox.prefWidthProperty().bind(grid.widthProperty());
                hBox.prefHeightProperty().bind(grid.heightProperty());
                // Adding label
                hBox.getChildren().add(label);
                grid.add(hBox, 0, i);
                this.horizontalPointLabels[i] = label;
            }
        } else {
            for (int i = 0; i < grid.getColumnCount(); i++) {
                Label label = new Label("0");
                // Creating HBox for proper display of the label
                HBox hBox = new HBox();
                // Setting properties of the HBox
                hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                hBox.setAlignment(Pos.CENTER);
                hBox.prefWidthProperty().bind(grid.widthProperty());
                hBox.prefHeightProperty().bind(grid.heightProperty());
                // Adding label
                hBox.getChildren().add(label);
                grid.add(hBox, i, 0);
                this.verticalPointLabels[i] = label;
            }
        }
    }

    /**
     * Methods builds a grid.
     *
     * @param colCount - the amount of columns.
     * @param rowCount - the amount of rows.
     * @return a grid.
     */
    private GridPane initGridPane(int colCount, int rowCount) {
        GridPane gridpane = new GridPane();
        for (int x = 0; x < colCount; x++) {
            // Adding rows
            RowConstraints row = new RowConstraints();
            row.setMinHeight(2);
            gridpane.getRowConstraints().add(row);
        }
        for (int i = 0; i < rowCount; i++) {
            // Adding columns
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(2);
            gridpane.getColumnConstraints().add(col);
        }
        // Setting properties
        gridpane.setHgap(CELL_FIT);
        gridpane.setVgap(CELL_FIT);
        gridpane.setAlignment(Pos.CENTER);
        return gridpane;
    }


    /**
     * Method gets the url of a token based on its ordinal number.
     *
     * @param ordinal The ordinal of the token
     * @return The url of the token.
     */
    private static String ordinalToPicture(int ordinal) {
        return switch (ordinal) {
            case 0 -> "gui/img/none.png";
            case 1 -> "gui/img/sun.png";
            case 2 -> "gui/img/cross.png";
            case 3 -> "gui/img/triangle.png";
            case 4 -> "gui/img/square.png";
            case 5 -> "gui/img/pentagon.png";
            case 6 -> "gui/img/star.png";
            case 7 -> "gui/img/remove.png";
            case 8 -> "gui/img/move.png";
            case 9 -> "gui/img/swapOnBoard.png";
            case 10 -> "gui/img/swapWithHand.png";
            case 11 -> "gui/img/greenArrow.png";
            case 12 -> "gui/img/orangeArrow.png";
            default -> throw new IllegalArgumentException("Numbers below zero and numbers above 12 are not allowed.");
        };
    }


    /**
     * Handler for the window that contains the possible combinations that generate points.
     *
     * @param actionEvent - Unused.
     * @throws IOException - IOException
     */
    @FXML
    public void handleCombinationStart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("tileCombinationInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stage = new Stage();
        stage.setMinHeight(500);
        stage.setMinWidth(500);


        stage.setTitle("Combinations");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Handles the game after the game is started.
     * Disables the text boxes for the player names, enables the field played on, and
     * creates a new instance of the game.
     */
    @FXML
    public void handleBtnStartGame() {
        final int maxPlayerCount = 4;
        Player[] players = new Player[maxPlayerCount];
        // Creating 2 active players
        if (menuItemPlayers2.isSelected()) {
            // Creating AI players if checkboxes were selected
            players[0] = checkBoxAIVertical1.isSelected() ?
                    new GameAI(txtFieldVertical1.getText(), Team.VERTICAL, true, true) :
                    new Player(txtFieldVertical1.getText(), Team.VERTICAL, true, false);

            players[1] = checkBoxAIHorizontal1.isSelected() ?
                    new GameAI(txtFieldHorizontal1.getText(), Team.HORIZONTAL, true, true) :
                    new Player(txtFieldHorizontal1.getText(), Team.HORIZONTAL, true, false);

            // Setting two inactive players
            players[2] = new Player(txtFieldVertical2.getText(), Team.VERTICAL, false, false);
            players[3] = new Player(txtFieldHorizontal2.getText(), Team.HORIZONTAL, false, false);
        } else {
            // Creating 4 active players

            // Creating AI players if checkboxes were selected
            players[0] = checkBoxAIVertical1.isSelected() ?
                    new GameAI(txtFieldVertical1.getText(), Team.VERTICAL, true, true) :
                    new Player(txtFieldVertical1.getText(), Team.VERTICAL, true, false);

            players[1] = checkBoxAIHorizontal1.isSelected() ?
                    new GameAI(txtFieldHorizontal1.getText(), Team.HORIZONTAL, true, true) :
                    new Player(txtFieldHorizontal1.getText(), Team.HORIZONTAL, true, false);

            players[2] = checkBoxAIVertical2.isSelected() ?
                    new GameAI(txtFieldVertical2.getText(), Team.VERTICAL, true, true) :
                    new Player(txtFieldVertical2.getText(), Team.VERTICAL, true, false);

            players[3] = checkBoxAIHorizontal2.isSelected() ?
                    new GameAI(txtFieldHorizontal2.getText(), Team.HORIZONTAL, true, true) :
                    new Player(txtFieldHorizontal2.getText(), Team.HORIZONTAL, true, false);
        }
        // Starting new game
        this.gui = new JavaFXGui(imgViewFieldGUI, imgViewPlayerHandGUI, lblVerticalScore,
                lblHorizontalScore, lblInfo, this.horizontalPointLabels,
                this.verticalPointLabels, this.gridContainerBoard, this.playerHand,
                this.lblUsedRemoves, this.lblUsedMoves, this.lblUsedSwapOnBoards, this.lblUsedSwapWithHands,
                this.mnuItemAIHandOnOff);

        this.game = new GameCrosswise(players,
                imgViewFieldGUI.length,
                gui);
        initGame();
        if (this.checkBoxAIVertical1.isSelected() || this.checkBoxAIVertical2.isSelected() ||
                this.checkBoxAIHorizontal1.isSelected() || this.checkBoxAIHorizontal2.isSelected()) {
            this.mnuItemMiddleTurn.setSelected(true);
        }
    }


    /**
     * Initializes gui components and dragEvents for a started game.
     */
    private void initGame() {
        initializeDragEvents(game);
        // Disabling, enabling and setting components visible that are needed at the beginning of the game
        txtFieldHorizontal1.setDisable(true);
        txtFieldHorizontal2.setDisable(true);
        txtFieldVertical1.setDisable(true);
        txtFieldVertical2.setDisable(true);
        lblInfo.setVisible(true);
        lblHorizontalScore.setVisible(true);
        lblVerticalScore.setVisible(true);
        menuNumOfPlayers.setDisable(true);
        btnStartGame.setDisable(true);
        checkBoxAIHorizontal1.setDisable(true);
        checkBoxAIHorizontal2.setDisable(true);
        checkBoxAIVertical1.setDisable(true);
        checkBoxAIVertical2.setDisable(true);
        menuItemNewGame.setDisable(false);
        pointBorderHorizontal.setVisible(true);
        pointBorderVertical.setVisible(true);
    }

    /**
     * Method initializes the needed drag-drop-events on the field.
     *
     * @param game - The current game, needed for the turn of a player,
     */
    private void initializeDragEvents(GameCrosswise game) {
        // Initializing drag-and-drop for every cell in the field
        for (ImageView[] imageViews : imgViewFieldGUI) {
            for (ImageView imageView : imageViews) {
                dragOver(imageView);
                dragEntered(imageView);
                dragExited(imageView);
                dragExited(imageView);
                dropImage(imageView, game);
            }
        }
        for (ImageView[] imageViews : imgViewPlayerHandGUI) {
            detectDrag(imageViews[0]);
        }
    }

    /**
     * Method handles the menu option if the user decides that two players are playing the game.
     */
    @FXML
    public void handleCheckTwoPlayers() {
        txtFieldVertical2.setDisable(true);
        checkBoxAIVertical2.setSelected(false);
        checkBoxAIVertical2.setDisable(true);
        txtFieldHorizontal2.setDisable(true);
        checkBoxAIHorizontal2.setSelected(false);
        checkBoxAIHorizontal2.setDisable(true);
        menuItemPlayers4.setSelected(false);
    }

    /**
     * Method handles the menu option if the user decides that four players are playing the game.
     */
    @FXML
    public void handleCheckFourPlayers() {
        txtFieldVertical2.setDisable(false);
        checkBoxAIVertical2.setDisable(false);
        txtFieldHorizontal2.setDisable(false);
        checkBoxAIHorizontal2.setDisable(false);
        menuItemPlayers2.setSelected(false);
    }

    /**
     * Method handles the option to start a new game.
     */
    @FXML
    public void handleMenuNewGame() {
        for (ImageView[] imageViews : imgViewFieldGUI) {
            for (ImageView imageView : imageViews) {
                // Resetting the field with empty fields
                imageView.setImage(new Image(ordinalToPicture(0)));
            }
        }
        for (ImageView[] imageViews : imgViewPlayerHandGUI) {
            for (ImageView imageView : imageViews) {
                // Resetting the hand of the player with empty fields
                imageView.setImage(new Image(ordinalToPicture(0)));
            }
        }

        // Resetting The score board
        for (int i = 0; i < this.verticalPointLabels.length; i++) {
            this.verticalPointLabels[i].setText("0");
            this.horizontalPointLabels[i].setText("0");
        }

        // Enabling components and making components invisible
        txtFieldHorizontal1.setDisable(false);
        txtFieldHorizontal2.setDisable(false);
        txtFieldVertical1.setDisable(false);
        txtFieldVertical2.setDisable(false);
        lblInfo.setVisible(false);
        lblHorizontalScore.setVisible(false);
        lblVerticalScore.setVisible(false);
        menuNumOfPlayers.setDisable(false);
        btnStartGame.setDisable(false);
        checkBoxAIHorizontal1.setDisable(false);
        checkBoxAIHorizontal2.setDisable(false);
        checkBoxAIVertical1.setDisable(false);
        checkBoxAIVertical2.setDisable(false);
        pointBorderHorizontal.setVisible(false);
        pointBorderVertical.setVisible(false);
        gridContainerBoard.getParent().setDisable(false);
        playerHand.getParent().setDisable(false);
        lblUsedSwapWithHands.setText("0/3");
        lblUsedSwapOnBoards.setText("0/3");
        lblUsedRemoves.setText("0/3");
        lblUsedMoves.setText("0/3");
        menuItemPlayers2.setSelected(false);
        menuItemPlayers4.setSelected(false);
        mnuItemShortTurn.setSelected(false);
        mnuItemLongTurn.setSelected(false);
        if (this.gui != null) {
            gui.closeWindows();
        }
    }

    /**
     * Method handles the menu option to exit the game.
     */
    @FXML
    public void handleMenuExit() {
        System.exit(0);
    }

    /**
     * Method the option to display the score board.
     */
    @FXML
    public void handleMenuScoreBoard() {
        if (radioMenuItemScoreBoard.isSelected()) {
            lblVerticalScore.setVisible(false);
            lblHorizontalScore.setVisible(false);
        } else {
            lblVerticalScore.setVisible(true);
            lblHorizontalScore.setVisible(true);
        }
    }

    /**
     * Method handles the option to display the assisting point tables at rows and columns.
     */
    @FXML
    public void handleMenuPointsAtRowsCols() {
        if (radioMenuItemPointsAtRowsCols.isSelected()) {
            pointBorderHorizontal.setVisible(false);
            pointBorderVertical.setVisible(false);
        } else {
            pointBorderHorizontal.setVisible(true);
            pointBorderVertical.setVisible(true);
        }
    }


    /**
     * Handles the saving of the current game.
     */
    @FXML
    public void handleSaveGame() {
        File currentDir = null;
        // Getting directory
        try {
            currentDir = new File(JarMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            //
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save game");
        if (currentDir != null) {
            chooser.setInitialDirectory(currentDir.getParentFile());
        }
        // Adding extension filter
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Only JSON allowed", "*.json"));

        File saveFile = chooser.showSaveDialog(mainContainer.getScene().getWindow());
        if (saveFile != null && this.game != null) {
            // Saving game
            GameLoadSave.saveGame(this.game, saveFile.getPath());
        }
    }


    /**
     * Method handles loading a saved game.
     */
    @FXML
    public void handleLoadGame() {
        File currentDir = null;
        this.gui.closeWindows();
        try {
            // Getting directory
            currentDir = new File(JarMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            //
        }
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load game");
        if (currentDir != null) {
            chooser.setInitialDirectory(currentDir.getParentFile());
        }
        // Adding extension filter
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Only JSON allowed", "*.json"));
        File loadFile = chooser.showOpenDialog(mainContainer.getScene().getWindow());
        if (loadFile != null) {
            // Loading game data
            GameData gameData = GameLoadSave.loadGame(loadFile.getPath());
            if (gameData != null) {
                JavaFXGui gui = new JavaFXGui(imgViewFieldGUI, imgViewPlayerHandGUI, lblVerticalScore,
                        lblHorizontalScore, lblInfo, this.horizontalPointLabels,
                        this.verticalPointLabels, this.gridContainerBoard, this.playerHand,
                        this.lblUsedRemoves, this.lblUsedMoves, this.lblUsedSwapOnBoards, this.lblUsedSwapWithHands,
                        this.mnuItemAIHandOnOff);
                // Starting game with loaded data
                this.game = new GameCrosswise(gameData, gui);
                initGame();
            }
        }
    }


    /**
     * Makes an ImageView draggable.
     *
     * @param sourceNode - The source node that should be made draggable.
     */
    private void detectDrag(ImageView sourceNode) {
        sourceNode.setOnDragDetected((MouseEvent event) -> {

            Dragboard db = sourceNode.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            Image image = sourceNode.getImage();
            // Putting image and URL to content
            content.putImage(new Image(
                    image.getUrl(), sourceNode.getFitWidth(), sourceNode.getFitHeight(), false, false));
            content.putUrl(image.getUrl());
            // Setting content of the dragBoard
            if (Tokens.getTokenFromPicture(content.getImage()) != Tokens.EMPTY) {
                db.setContent(content);
            }


            // Getting the position of where node has been dragged from
            sourceNodePos = new GamePositions(GridPane.getColumnIndex(sourceNode), GridPane.getRowIndex(sourceNode));
            event.consume();
        });
    }

    /**
     * Handles the case a dragged image hovers over another image.
     * Decides if an image may be dragged over another.
     *
     * @param targetNode - The ImageView that gets hovered over.
     */
    private void dragOver(ImageView targetNode) {
        targetNode.setOnDragOver((DragEvent event) -> {

            if (event.getGestureSource() != targetNode && event.getDragboard().hasImage()) {
                // Getting the dragged token
                Tokens token = Tokens.getTokenFromPicture(new Image(event.getDragboard().getUrl()));
                if (token != Tokens.EMPTY) {
                    if (token == Tokens.REMOVE_STONE) {
                        if (!Tokens.getTokenFromPicture(targetNode.getImage()).equals(Tokens.EMPTY)) {
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                    } else {
                        // Tokens may only be placed/hovered over empty fields, only during a special turn that requires
                        // placing a token over an already taken cell (i.e. swap on board) a token may be placed on another
                        if (Tokens.getTokenFromPicture(targetNode.getImage()).equals(Tokens.EMPTY) || isSpecialTurn
                                || Tokens.isSpecialToken(token)) {
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                    }
                }
            }
            // System.out.println("dragOver");
            event.consume();
        });
    }


    /**
     * Handles the moment that the drag has been entered.
     *
     * @param targetNode - The ImageView where the drag gets entered.
     */
    private void dragEntered(ImageView targetNode) {
        targetNode.setOnDragEntered((DragEvent event) -> {
            Dragboard db = event.getDragboard();

            // Welche Information kann auf diesem Target-Objekt abgelegt werden?
            //  hier: eine die einen String liefert und nicht von dem Node selbst stammt
            if (db.hasImage() && !event.isDropCompleted() && event.getGestureSource() != targetNode) {
                // Getting the token that gets dragged
                Tokens token = Tokens.getTokenFromPicture(new Image(event.getDragboard().getUrl()));
                // Making shadow for highlighting
                DropShadow shadow = new DropShadow();
                shadow.setOffsetX(0);
                shadow.setOffsetY(0);
                shadow.setHeight(50);
                shadow.setWidth(50);
                // Highlighting only on non-empty fields
                if (token != Tokens.EMPTY) {
                    if (Tokens.isSpecialToken(token)) {
                        if (!Tokens.getTokenFromPicture(targetNode.getImage()).equals(Tokens.EMPTY)) {
                            // Different color for special tokens
                            shadow.setColor(Color.RED);
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                    } else {
                        if (Tokens.getTokenFromPicture(targetNode.getImage()).equals(Tokens.EMPTY) || isSpecialTurn) {
                            // red color for special turns, blue for regular turns
                            if (isSpecialTurn) {
                                shadow.setColor(Color.RED);
                            } else {
                                shadow.setColor(Color.BLUE);
                            }
                        }
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                // Setting the effect
                targetNode.setEffect(shadow);
            }

            //  System.out.println("dragEntered");
            event.consume();
        });
    }


    /**
     * Handles the moment where the image gets dropped.
     *
     * @param targetNode - The ImageView that image gets dropped on.
     * @param game       - The current game
     */
    private void dropImage(ImageView targetNode, GameCrosswise game) {
        targetNode.setOnDragDropped((DragEvent event) -> {

            // Getting dragBoard
            Dragboard db = event.getDragboard();
            boolean success = false;
            // Flag for the remove-token
            boolean turnIsFinished = false;
            boolean moveIsAllowed = true;
            int pictureCount = countImagesOnBoard();
            // Getting position of the targeted ImageView
            GamePositions targetPos = new GamePositions(GridPane.getColumnIndex(targetNode),
                    GridPane.getRowIndex(targetNode));
            if (db.hasImage() && db.hasUrl()) {
                Tokens token = Tokens.getTokenFromPicture(new Image(db.getUrl()));
                if (token != Tokens.EMPTY) {
                    // Handling dropping of special tokens
                    if (token == Tokens.REMOVE_STONE) {
                        if (!Tokens.getTokenFromPicture(targetNode.getImage()).equals(Tokens.EMPTY)) {
                            // Replacing chosen image with an empty field
                            success = true;
                            // Handling removing on logic side of the game
                            game.handleSpecialToken(Tokens.REMOVE_STONE, targetPos, sourceNodePos);

                            // Turn is finished after removing the token
                            turnIsFinished = true;
                        }
                    } else if (token == Tokens.MOVE_STONE || token == Tokens.SWAP_ON_BOARD) {
                        if (pictureCount < 1 && token == Tokens.MOVE_STONE) {
                            // Setting error message
                            this.lblInfo.setText("To use this token, at least one token must be set on the board");
                            moveIsAllowed = false;

                        } else if (pictureCount < 2 && token == Tokens.SWAP_ON_BOARD) {
                            // Setting error message
                            this.lblInfo.setText("To use this token, at least two tokens must be set on the board.");
                            moveIsAllowed = false;

                        } else {
                            // Disabling the playerHand because the next operation is on the field
                            playerHand.setDisable(true);
                            // Making field draggable if it's not empty
                            for (ImageView[] imageViews : this.imgViewFieldGUI) {
                                for (ImageView imageView : imageViews) {
                                    if (Tokens.getTokenFromPicture(imageView.getImage()) != Tokens.EMPTY) {
                                        detectDrag(imageView);
                                    }
                                }
                            }
                            // Disabling dragging over non-empty fields and handling moving on logic side of the game
                            if (token == Tokens.MOVE_STONE) {
                                game.handleSpecialToken(Tokens.MOVE_STONE, null, sourceNodePos);
                            } else {
                                // Disabling dragging over empty fields and handling swapping on logic side of the game
                                game.handleSpecialToken(Tokens.SWAP_ON_BOARD, targetPos, sourceNodePos);
                            }
                            success = true;
                            // Following turn is a special turn
                            this.isSpecialTurn = true;
                            // First phase of the special turn is over
                            this.phaseOfTurn++;
                            // Setting previous used special token
                            this.usedSpecialToken = token;
                        }

                    } else if (token == Tokens.SWAP_WITH_HAND) {
                        if (pictureCount < 1) {
                            // Setting error message
                            this.lblInfo.setText("To use this token, at least one token must be set on the board.");
                            moveIsAllowed = false;

                        } else {
                            success = true;
                            // Following turn is a special turn
                            this.isSpecialTurn = true;
                            // First phase of the special turn is over
                            this.phaseOfTurn++;
                            // Setting previous used special token
                            this.usedSpecialToken = token;
                            // Disabling dragging over empty fields and handling swapping on logic side of the game
                            game.handleSpecialToken(Tokens.SWAP_WITH_HAND, targetPos, sourceNodePos);
                        }
                    }
                    if (!turnIsFinished && moveIsAllowed) {
                        // Special use case
                        if (this.phaseOfTurn >= 1 && this.isSpecialTurn && !Tokens.isSpecialToken(token)) {
                            game.playerTurn(targetPos, token, sourceNodePos, true, usedSpecialToken);
                            this.phaseOfTurn = 0;
                            this.isSpecialTurn = false;
                            resetDragOverOnField();
                            if (this.usedSpecialToken == Tokens.SWAP_WITH_HAND) {
                                resetDetectDragPlayerHand();
                            }
                            this.usedSpecialToken = null;
                            // Regular use case
                        } else {
                            game.playerTurn(targetPos, token, sourceNodePos, false, usedSpecialToken);
                            this.phaseOfTurn++;
                            success = true;
                        }
                    }
                }
            }

            targetNode.setEffect(null);
            // Checking if drop was completed successfully
            event.setDropCompleted(success);
            event.consume();
        });
    }


    /**
     * Reactivates drag-over options on the field.
     */
    private void resetDragOverOnField() {
        for (ImageView[] imageViews : imgViewFieldGUI) {
            for (ImageView imageView : imageViews) {
                dragOver(imageView);
            }
        }
    }

    /**
     * Resets drag-detection for the hand of the player.
     */
    private void resetDetectDragPlayerHand() {
        for (ImageView[] imageViews : this.imgViewPlayerHandGUI) {
            detectDrag(imageViews[0]);
        }
    }


    /**
     * Method handles the moment the drag has been exited.
     *
     * @param targetNode - The ImageView
     */
    private void dragExited(ImageView targetNode) {
        targetNode.setOnDragExited((DragEvent event) -> {

            if (!event.isDropCompleted() &&
                    event.getGestureSource() != targetNode &&
                    event.getDragboard().hasImage()) {
                // Removes visual feedback that gets set in the dragEntered()-method
                targetNode.setEffect(null);
            }
            //  System.out.println("dragExited");
            event.consume();
        });
    }

    /**
     * Counts the images on the board. Used for error-handling.
     *
     * @return The count of images on the field.
     */
    private int countImagesOnBoard() {
        int count = 0;
        for (int i = 0; i < this.imgViewFieldGUI.length && count < 2; i++) {
            for (int j = 0; j < this.imgViewFieldGUI[i].length && count < 2; j++) {
                // Increasing the count if the cell is not an empty token
                if (Tokens.getTokenFromPicture(this.imgViewFieldGUI[i][j].getImage()) != Tokens.EMPTY) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Handles the option that a short delay after an AI turn was selected.
     */
    @FXML
    public void handleShortAITurn() {
        if (this.gui != null) {
            this.gui.setPauseTime(SHORTPAUSE_IN_MILLS);
        }
        this.mnuItemMiddleTurn.setSelected(false);
        this.mnuItemLongTurn.setSelected(false);
    }

    /**
     * Handles the option that a medium delay after an AI turn was selected.
     */
    @FXML
    public void handleMiddleAITurn() {
        if (this.gui != null) {
            this.gui.setPauseTime(MIDDLEPAUSE_IN_MILLS);
        }
        this.mnuItemShortTurn.setSelected(false);
        this.mnuItemLongTurn.setSelected(false);

    }

    /**
     * Handles the option that a long delay after an AI turn was selected.
     */
    public void handleLongAITurn() {
        if (this.gui != null) {
            this.gui.setPauseTime(LONGPAUSE_IN_MILLS);
        }
        this.mnuItemShortTurn.setSelected(false);
        this.mnuItemMiddleTurn.setSelected(false);
    }

}
