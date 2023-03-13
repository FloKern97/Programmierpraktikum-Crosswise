package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for saving and loading the game.
 *
 * @author Florian Kern/cgt104661
 */
public final class GameLoadSave {


    /**
     * Method saves the game.
     *
     * @param game - The game.
     * @param fileName - File where the data of the game gets written to.
     */
    public static void saveGame(GameCrosswise game, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        GameData gameData = game.toGameData();
        try (FileWriter writer = new FileWriter(fileName)) {
            // writing data into json file
            gson.toJson(gameData, writer);
        } catch (IOException e) {
            //
        }
    }


    /**
     * Loads a game.
     *
     * @param fileName - File where the data gets read from
     *
     * @return Data of a saved game.
     */
    public static GameData loadGame(String fileName) {
        Gson gson = new Gson();
        GameData gameData = null;
        try {
            // reading from Json
            gameData = gson.fromJson(new FileReader(fileName), GameData.class);
        } catch (IOException e) {
            //
        }
        return gameData;
    }

}
