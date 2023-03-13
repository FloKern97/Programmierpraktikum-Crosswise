package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class for the tile-combination window.
 *
 * @author Florian Kern/cgt104661
 */
public class TileCombinationInterfaceController implements Initializable {


    /**
     * HBox for the grid.
     */
    @FXML
    private HBox hBoxMainContainer;

    /**
     * VBox for the grid.
     */
    @FXML
    private VBox vBoxContainer;

    /**
     * Grid for the tiles and their respective points.
     */
    @FXML
    private GridPane gridMain;

    /**
     * The amount of possible combinations of the tokens.
     */
    private final int GRIDCOMBINATIONS = 6;

    /**
     * Variable to handle gaps between cells
     */
    private final int CELLFIT = 5;

    /**
     * Method initializes the window. Builds the grid on which the combinations
     * and their points are displayed.
     *
     * @param location  unused
     * @param resources unused
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Binding width and height of VBox to width and height of HBox
        vBoxContainer.prefWidthProperty().bind(hBoxMainContainer.heightProperty());
        vBoxContainer.prefHeightProperty().bind(hBoxMainContainer.widthProperty());

        // Binding width and height of gridPane to width and height of VBox
        gridMain.prefWidthProperty().bind(vBoxContainer.widthProperty());
        gridMain.prefHeightProperty().bind(vBoxContainer.heightProperty());
        gridMain.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane[] gridArr = new GridPane[GRIDCOMBINATIONS];
        // Building the grid
        for (int i = 0; i < gridArr.length; i++) {
            HBox hBox = new HBox();
            VBox vBox = new VBox();
            // Building individual grids for displaying a possible combination
            gridArr[i] = createCombinationGrid();
            hBox.getChildren().add(vBox);
            vBox.getChildren().add(gridArr[i]);
            // Binding constructed grid width to HBox width
            gridArr[i].prefWidthProperty().bind(hBox.widthProperty());
            gridArr[i].prefHeightProperty().bind(gridArr[i].widthProperty().divide(GRIDCOMBINATIONS));
            // Centering VBox
            vBox.alignmentProperty().set(Pos.CENTER);
            //
            gridMain.add(hBox, 0, i + 1);

        }

        ((ImageView) gridArr[0].getChildren().get(0)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[0].getChildren().get(1)).setImage(new Image("gui/img/sun.png"));
        ((ImageView) gridArr[0].getChildren().get(2)).setImage(new Image("gui/img/pentagon.png"));
        ((ImageView) gridArr[0].getChildren().get(3)).setImage(new Image("gui/img/square.png"));
        ((ImageView) gridArr[0].getChildren().get(4)).setImage(new Image("gui/img/triangle.png"));
        ((ImageView) gridArr[0].getChildren().get(5)).setImage(new Image("gui/img/star.png"));

        ((ImageView) gridArr[1].getChildren().get(0)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[1].getChildren().get(1)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[1].getChildren().get(2)).setImage(new Image("gui/img/none.png"));
        ((ImageView) gridArr[1].getChildren().get(3)).setImage(new Image("gui/img/none.png"));
        ((ImageView) gridArr[1].getChildren().get(4)).setImage(new Image("gui/img/none.png"));
        ((ImageView) gridArr[1].getChildren().get(5)).setImage(new Image("gui/img/none.png"));


        ((ImageView) gridArr[2].getChildren().get(0)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[2].getChildren().get(1)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[2].getChildren().get(2)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[2].getChildren().get(3)).setImage(new Image("gui/img/none.png"));
        ((ImageView) gridArr[2].getChildren().get(4)).setImage(new Image("gui/img/none.png"));
        ((ImageView) gridArr[2].getChildren().get(5)).setImage(new Image("gui/img/none.png"));

        ((ImageView) gridArr[3].getChildren().get(0)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[3].getChildren().get(1)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[3].getChildren().get(2)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[3].getChildren().get(3)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[3].getChildren().get(4)).setImage(new Image("gui/img/none.png"));
        ((ImageView) gridArr[3].getChildren().get(5)).setImage(new Image("gui/img/none.png"));

        ((ImageView) gridArr[4].getChildren().get(0)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[4].getChildren().get(1)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[4].getChildren().get(2)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[4].getChildren().get(3)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[4].getChildren().get(4)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[4].getChildren().get(5)).setImage(new Image("gui/img/none.png"));

        ((ImageView) gridArr[5].getChildren().get(0)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[5].getChildren().get(1)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[5].getChildren().get(2)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[5].getChildren().get(3)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[5].getChildren().get(4)).setImage(new Image("gui/img/cross.png"));
        ((ImageView) gridArr[5].getChildren().get(5)).setImage(new Image("gui/img/cross.png"));

    }


    /**
     * Method builds a grid for a possible combination of a token.
     *
     * @return A Grid for the possible combination of a token
     */
    private GridPane createCombinationGrid() {
        // Initialising the grid
        GridPane result = initGridPane(1, 6);
        // Getting row and col count
        int colCount = result.getColumnCount();
        int rowCount = result.getRowCount();
        result.setHgap(CELLFIT);
        // Calculating width and height of a cell
        int cellWidth = (int) result.getWidth() / colCount;
        int cellHeight = (int) result.getHeight() / rowCount;
        for (int i = 0; i < colCount; i++) {
            ImageView imageViews = new ImageView();
            //image has to fit a cell and mustn't preserve ratio
            imageViews.setFitWidth(cellWidth);
            imageViews.setFitHeight(cellHeight);
            imageViews.setPreserveRatio(false);
            imageViews.setSmooth(true);

            //add the imageview to the cell
            result.add(imageViews, i, 0);
            imageViews.fitWidthProperty().bind(result.widthProperty().divide(colCount).subtract(CELLFIT));
            imageViews.fitHeightProperty().bind(result.heightProperty().divide(rowCount).subtract(CELLFIT));
        }
        return result;
    }

    /**
     * Builds a grid.
     *
     * @param rowCount - The amount of rows.
     * @param colCount - The amount of columns.
     *
     * @return A grid for the combinations
     */
    private GridPane initGridPane(int rowCount, int colCount) {
        GridPane gridpane = new GridPane();
        for (int x = 0; x < rowCount; x++) {
            // Adding rows
            RowConstraints row = new RowConstraints();
            row.setMinHeight(2);
            gridpane.getRowConstraints().add(row);
        }
        for (int i = 0; i < colCount; i++) {
            // Adding columns
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(2);
            gridpane.getColumnConstraints().add(col);

        }
        // Setting properties
        gridpane.setHgap(CELLFIT);
        gridpane.setVgap(CELLFIT);
        gridpane.setAlignment(Pos.CENTER);

        return gridpane;
    }


}
