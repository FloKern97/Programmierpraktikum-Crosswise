package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class that starts our application.
 *
 * @author mjo, Florian Kern/cgt104661
 */
public class ApplicationMain extends Application {

    /**
     * Creating the stage and showing it. This is where the initial size and the
     * title of the window are set.
     *
     * @param stage the stage to be shown
     * @throws IOException - IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("UserInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Crosswise!");
        stage.setMinHeight(650);
        stage.setMinWidth(900);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Main method.
     *
     * @param args unused
     */
    public static void main(String... args) {
        launch(args);
    }
}
