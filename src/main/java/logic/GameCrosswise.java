package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static logic.Team.*;
import static logic.Tokens.*;

/**
 * Class contains all the logic needed for a game of crosswise. Determines the current player, sets the token,
 * and determines the winner of the game.
 *
 * @author Florian Kern/cgt104661
 */
public class GameCrosswise {
    /**
     * The maximum amount of tokens a player can have in his hand at once.
     */
    public static final int HANDSIZE = 4;

    /**
     * The amount of players playing the game.
     */
    private final int NUM_OF_PLAYERS = 4;
    /**
     * Flag for printing logged turns on the console.
     */
    private final boolean PRINT_ON_CONSOLE = false;

    /**
     * Index of the current player.
     */
    private int idxCurrentPlayer;

    /**
     * Array containing the names of the players.
     */
    private Player[] players = new Player[NUM_OF_PLAYERS];

    /**
     * Connection to the gui.
     */
    private final GUIConnector gui;

    /**
     * The pouch for the tokens of the game.
     */
    private TokenPouch tokenPouch;

    /**
     * The field on which the game is played.
     */
    private final GameField field;


    /**
     * Contains the amount of used special tokens
     */
    private int[] usedSpecialTokens = new int[4];

    /**
     * Forces a game to end if no player can play a token
     */
    private boolean isForcedEnd = false;


    /**
     * Constructor for testing of a game of Crosswise.
     *
     * @param players names of the players.
     * @param field   The field on which the game is played
     * @param gui     Connection to the gui
     */
    GameCrosswise(Player[] players, GameField field, GUIConnector gui) {
        this.players = players;
        this.field = field;
        this.gui = gui;
        this.idxCurrentPlayer = 0;
        this.tokenPouch = new TokenPouch();
    }

    /**
     * Constructor for a game of crosswise.
     *
     * @param players   - The array containing all information about the participating players.
     * @param fieldSize - The size of the field
     * @param gui       - Connection to the gui
     */
    public GameCrosswise(Player[] players, int fieldSize, GUIConnector gui) {
        this(players, new GameField(new Tokens[fieldSize][fieldSize]), gui);
        // Deleting logged file
        deleteLogFile();

        // Initialising the player hands
        for (Player player : this.players) {
                player.fillHand(tokenPouch);
        }
        Tokens[][] gameField = this.field.getField();
        // Initialising the field with empty tokens
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                this.field.setToken(EMPTY, new GamePositions(i, j));
            }
        }

        // Logging start of the game
        writeToLog(buildInitialGameLog());

        // Setting the current player
        if (isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand())) {
            this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(),
                    this.players[idxCurrentPlayer].getTeam(), this.players[idxCurrentPlayer].getPlayerHand(),
                    this.players[idxCurrentPlayer].isAi());

            if (this.players[idxCurrentPlayer].isAi()) {
                GameAI ai = (GameAI) this.players[idxCurrentPlayer];
                ai.setField(this.field);
                playerTurnAI(ai);

            }
        } else {
            skipTurn();
        }
    }


    /**
     * Constructor for loading a game.
     *
     * @param gameData - Data of the game
     * @param gui      - The gui
     */
    public GameCrosswise(GameData gameData, GUIConnector gui) {
        for (int i = 0; i < gameData.getPlayers().length; i++) {
            // Getting data of the saved players
            PlayerData playerData = gameData.getPlayers()[i];
            if (playerData.isAi()) {
                // differentiate between AI and human players
                this.players[i] = new GameAI(
                        playerData.getName(),
                        playerData.getTeam(),
                        playerData.isActive(),
                        playerData.isAi(),
                        playerData.getPlayerHand()
                );
            } else {
                this.players[i] = new Player(
                        playerData.getName(),
                        playerData.getTeam(),
                        playerData.isActive(),
                        playerData.isAi(),
                        playerData.getPlayerHand()
                );
            }
        }
        // Getting needed game data
        this.idxCurrentPlayer = gameData.getCurrentPlayer();
        this.usedSpecialTokens = gameData.getUsedActionTiles();
        this.tokenPouch = new TokenPouch(gameData.getTokenPouch());
        this.field = new GameField(gameData.getField());
        this.gui = gui;


        // Displaying field
        this.gui.displayField(this.field.getField());

        // Log start of the game
        writeToLog(buildInitialGameLog());
        // Setting the current player

        // Checking is turn is playable
        if (isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand())) {
            this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(), this.players[idxCurrentPlayer].getTeam(),
                    this.players[idxCurrentPlayer].getPlayerHand(),
                    this.players[idxCurrentPlayer].isAi());

            // Executing AI turn if current player is an AI
            if (this.players[idxCurrentPlayer].isAi()) {
                GameAI ai = (GameAI) this.players[idxCurrentPlayer];
                ai.setField(this.field);
                playerTurnAI(ai);
            }
        } else {
            // skipping turn if turn was not playable
            skipTurn();
        }
    }

    /**
     * Gets all the data from the game.
     *
     * @return The data needed to save the game.
     */
    GameData toGameData() {
        PlayerData[] playerData = new PlayerData[this.players.length];
        for (int i = 0; i < playerData.length; i++) {
            // Constructing player data for saving the game
            playerData[i] = new PlayerData(
                    this.players[i].getPlayerName(),
                    this.players[i].isActive(),
                    this.players[i].isAi(),
                    this.players[i].getPlayerHand().stream().mapToInt(Enum::ordinal).toArray(),
                    this.players[i].getTeam()
            );
        }
        Tokens[][] gameField = this.field.getField();
        int[][] fieldData = new int[gameField.length][gameField.length];

        // Converting field enums to respective ordinal values
        for (int i = 0; i < fieldData.length; i++) {
            for (int j = 0; j < fieldData[i].length; j++) {
                fieldData[i][j] = gameField[i][j].ordinal();
            }
        }

        // Converting token pouch enums to respective ordinal values
        int[] pouchData = this.tokenPouch.getTokenPouch().stream().mapToInt(Enum::ordinal).toArray();
        return new GameData(playerData, idxCurrentPlayer, fieldData, usedSpecialTokens, pouchData);
    }


    /**
     * Sets the next player after an AI turn and starts a new AI turn if next player is an AI.
     */
    public void causeTurn() {
        // Setting player
        this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(), this.players[idxCurrentPlayer].getTeam(),
                this.players[idxCurrentPlayer].getPlayerHand(),
                this.players[idxCurrentPlayer].isAi());
        if (this.players[idxCurrentPlayer].isAi()) {
            GameAI ai = (GameAI) this.players[idxCurrentPlayer];
            ai.setField(this.field);
            // Executing AI turn
            playerTurnAI(ai);
        }
    }


    /**
     * Gets the points of a column or row, depending on the team.
     *
     * @param index - the index of the column or row
     * @param team  - The team
     * @return Points of the row or column.
     */
    int getPointsAtRowOrCol(int index, Team team) {
        return this.field.getPointsAtRowOrCol(index, team);
    }

    /**
     * Gets the total score of a team.
     *
     * @param team - The team
     * @return The total points
     */
    int getTeamScore(Team team) {
        return team == VERTICAL ? this.field.getScore(VERTICAL) : this.field.getScore(HORIZONTAL);
    }

    /**
     * Checks if a game has been won because of six tokens of one kind in a row/column
     *
     * @param team  - The team
     * @param index - The index of the row or column
     * @return True if there are six tokens of the same kind in the column or row, false if not.
     */
    public boolean isWinningSix(Team team, int index) {
        return team == VERTICAL ? this.field.isWinningSix(index, VERTICAL) : this.field.isWinningSix(index, HORIZONTAL);
    }


    /**
     * Method handles the turn of a player.
     *
     * @param coordinate       - The coordinate at which the token should be placed on
     * @param token            - The token
     * @param draggedFromPos   - The position where the token has been dragged from
     * @param isSpecialTurn    - Is the turn special because of a special token?
     * @param usedSpecialToken - The previously played special token
     */
    public void playerTurn(GamePositions coordinate, Tokens token, GamePositions draggedFromPos,
                           boolean isSpecialTurn, Tokens usedSpecialToken) {
        Tokens[][] gameField = this.field.getField();
        int[] horizontalScore = new int[gameField.length];
        int[] verticalScore = new int[gameField.length];
        int activePlayers = getActivePlayers();
        // Only doing something if desired position is empty or the turn is special
        if (gameField[coordinate.getX()][coordinate.getY()] == EMPTY || isSpecialTurn) {
            if (isSpecialTurn) {
                // Handling move according to previously played special token
                switch (usedSpecialToken) {
                    case MOVE_STONE -> handleMoveTurn(token, coordinate, gameField,
                            draggedFromPos, activePlayers);
                    case SWAP_ON_BOARD -> handleTurnSwappedOnBoard(token, coordinate, gameField,
                            draggedFromPos, activePlayers);
                    case SWAP_WITH_HAND -> handleTurnSwappedWithHand(token, coordinate,
                            gameField, draggedFromPos, activePlayers);
                }
            } else {
                if (!Tokens.isSpecialToken(token)) {
                    // Setting token and displaying it on the gui
                    this.field.setToken(token, coordinate);
                    this.gui.displayTokenOnField(coordinate, token);

                    // Calculating points for columns and rows, and setting them on the gui
                    calculateAndSetPoints(gameField, horizontalScore, verticalScore);
                    // Setting the total score of both teams
                    this.gui.setTotalScore(getTeamScore(VERTICAL), VERTICAL);
                    this.gui.setTotalScore(getTeamScore(HORIZONTAL), HORIZONTAL);

                    // Removing token and getting a new one
                    this.players[idxCurrentPlayer].removeFromHand(token);
                    Tokens nextToken = this.tokenPouch.getTokenPouch().poll();
                    if (nextToken != null) {
                        this.players[idxCurrentPlayer].addToHand(nextToken);
                    } else {
                        this.players[idxCurrentPlayer].addToHand(EMPTY);
                    }
                    writeToLog(buildMessagePlacedToken(coordinate, token, draggedFromPos.getX()));
                    // Checking if game is finished
                    if (getWinningTeam(isForcedEnd) == null) {
                        writeToLog(logGameStatus(false));
                        this.idxCurrentPlayer = ++this.idxCurrentPlayer % activePlayers;
                        // Setting next player if turn is playable
                        if (isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand())) {
                            this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(),
                                    this.players[idxCurrentPlayer].getTeam(),
                                    this.players[idxCurrentPlayer].getPlayerHand(),
                                    this.players[idxCurrentPlayer].isAi());
                            // Executing AI turn if next player is an AI
                            if (this.players[idxCurrentPlayer].isAi()) {
                                GameAI ai = (GameAI) this.players[idxCurrentPlayer];
                                ai.setField(this.field);
                                playerTurnAI(ai);
                            }
                        } else {
                            // skipping turn if next turn is unplayable
                            skipTurn();
                        }
                    } else {
                        // logging game end
                        writeToLog(logGameStatus(true));
                        boolean isWinningSixVertical = false;
                        boolean isWinningSixHorizontal = false;
                        for (int i = 0; i < this.field.getField().length && !isWinningSixVertical &&
                                !isWinningSixHorizontal; i++) {
                            isWinningSixHorizontal = this.field.isWinningSix(i, HORIZONTAL);
                            isWinningSixVertical = this.field.isWinningSix(i, VERTICAL);
                        }
                        // handling end of game
                        handleEndOfGame(isForcedEnd, horizontalScore, verticalScore,
                                isWinningSixHorizontal || isWinningSixVertical);
                    }
                }
            }
        }
    }

    /**
     * Method executes the turn of the AI.
     *
     * @param ai - The AI
     */
    private void playerTurnAI(GameAI ai) {
        // Getting turn for the AI
        AITurn aiturn = ai.getBestTurn();
        if (aiturn != null) {
            int activePlayers = getActivePlayers();
            Tokens[][] gameField = this.field.getField();
            int[] horizontalScore = new int[gameField.length];
            int[] verticalScore = new int[gameField.length];

            //  Executing special turn if calculated turn included a special token, a turn is special if
            //  it consists of two phases (i.e. placing move token and moving token on the field afterwards)
            if (aiturn.isSpecialTurn() && aiturn.getUsedSpecialToken() != null) {
                switch (aiturn.getUsedSpecialToken()) {
                    case MOVE_STONE -> {
                        handleMoveStone(MOVE_STONE,
                                new GamePositions(this.players[idxCurrentPlayer].getPlayerHand().indexOf(MOVE_STONE),
                                        0));
                        handleMoveTurn(aiturn.getToken(), aiturn.getPos(), gameField, aiturn.getDraggedFrom(), activePlayers);
                    }
                    case SWAP_ON_BOARD -> {
                        handleSwapOnBoard(SWAP_ON_BOARD,
                                new GamePositions(this.players[idxCurrentPlayer].getPlayerHand().indexOf(SWAP_ON_BOARD),
                                        0));
                        handleTurnSwappedOnBoard(aiturn.getToken(), aiturn.getPos(), gameField, aiturn.getDraggedFrom(), activePlayers);
                    }
                    case SWAP_WITH_HAND -> {
                        handleSwapWithHand(SWAP_WITH_HAND,
                                new GamePositions(this.players[idxCurrentPlayer].getPlayerHand().indexOf(SWAP_WITH_HAND),
                                        0)
                        );
                        handleTurnSwappedWithHand(aiturn.getToken(), aiturn.getPos(), gameField,
                                new GamePositions(this.players[idxCurrentPlayer].getPlayerHand().indexOf(aiturn.getToken()), 0), activePlayers);
                    }
                }
                //
            } else if (!aiturn.isSpecialTurn() && aiturn.getUsedSpecialToken() == REMOVE_STONE) {
                handleRemoveStone(aiturn.getPos(), activePlayers, REMOVE_STONE,
                        new GamePositions(this.players[idxCurrentPlayer].getPlayerHand().indexOf(REMOVE_STONE), 0));

                // Handling turn involving regular token
            } else if (!aiturn.isSpecialTurn() && aiturn.getUsedSpecialToken() == null) {
                // Setting and displaying token on field
                this.field.setToken(aiturn.getToken(), aiturn.getPos());
                this.gui.displayTokenOnField(aiturn.getPos(), aiturn.getToken());
                // Calculating points for columns and rows, and setting them on the gui
                calculateAndSetPoints(gameField, horizontalScore, verticalScore);
                // Setting the total score of both teams
                this.gui.setTotalScore(getTeamScore(VERTICAL), VERTICAL);
                this.gui.setTotalScore(getTeamScore(HORIZONTAL), HORIZONTAL);
                // Removing token from hand logically and on the gui
                this.players[idxCurrentPlayer].removeFromHand(aiturn.getToken());
                this.gui.removeFromPlayerHand(new GamePositions(aiturn.getHandPos(), 0));
                // Getting new token from pouch if it isn't empty
                Tokens nextToken = this.tokenPouch.getTokenPouch().poll();
                if (nextToken != null) {
                    this.players[idxCurrentPlayer].addToHand(nextToken);
                } else {
                    this.players[idxCurrentPlayer].addToHand(EMPTY);
                }
                // Logging turn
                writeToLog(buildMessagePlacedToken(aiturn.getPos(), aiturn.getToken(),
                        aiturn.getHandPos()));
                // Checking if game is finished
                if (getWinningTeam(isForcedEnd) == null) {
                    writeToLog(logGameStatus(false));
                    this.idxCurrentPlayer = ++this.idxCurrentPlayer % activePlayers;
                    if (!isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand())) {
                        // Skipping turn next turn is not playable
                        skipTurn();
                    } else {
                        // Initiating pause and new AI turn if next player is an AI
                        this.gui.waitForNextTurn(this, aiturn.getPos(), null);
                    }
                } else {
                    // Logging game end
                    writeToLog(logGameStatus(true));
                    boolean isWinningSixVertical = false;
                    boolean isWinningSixHorizontal = false;
                    for (int i = 0; i < this.field.getField().length && !isWinningSixVertical &&
                            !isWinningSixHorizontal; i++) {
                        isWinningSixHorizontal = this.field.isWinningSix(i, HORIZONTAL);
                        isWinningSixVertical = this.field.isWinningSix(i, VERTICAL);
                    }
                    // Handling end of game
                    handleEndOfGame(isForcedEnd, horizontalScore, verticalScore,
                            isWinningSixHorizontal || isWinningSixVertical);
                }
            }
        } else {
            // Skipping turn if AI could not play a token (i.e. only unplayable special tokens on hand)
            skipTurn();
        }
    }


    /**
     * Skips the turn of a player until a player can play a token or ends the game if no one can play a token.
     */
    private void skipTurn() {
        int activePlayers = getActivePlayers();
        // Logging skipped turn
        writeToLog(buildTurnSkippedLog());
        Tokens[][] gameField = this.field.getField();
        int[] verticalPoints = new int[gameField.length];
        int[] horizontalPoints = new int[gameField.length];
        for (int i = 0; i < gameField.length; i++) {
            verticalPoints[i] = getPointsAtRowOrCol(i, VERTICAL);
            horizontalPoints[i] = getPointsAtRowOrCol(i, HORIZONTAL);
        }
        int skippedCount = 0;
        idxCurrentPlayer = ++idxCurrentPlayer % activePlayers;
        // Increasing index of current player until a turn is possible or until it is determined that
        // no one can play a token
        while (!isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand()) && !isForcedEnd) {
            writeToLog(buildTurnSkippedLog());
            idxCurrentPlayer = ++idxCurrentPlayer % activePlayers;
            skippedCount++;
            if (activePlayers == 2 ? skippedCount == 2 : skippedCount == 4) {
                // Forcing end of game
                isForcedEnd = true;
                handleEndOfGame(true, horizontalPoints, verticalPoints, false);
                writeToLog(logForcedEnd());
                writeToLog(logGameStatus(true));
            }
        }
        if (!isForcedEnd) {
            // Setting next player
            this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(),
                    this.players[idxCurrentPlayer].getTeam(),
                    this.players[idxCurrentPlayer].getPlayerHand(),
                    this.players[idxCurrentPlayer].isAi());
            if (this.players[idxCurrentPlayer].isAi()) {
                // Executing AI turn if next player is an AI
                GameAI ai = (GameAI) this.players[idxCurrentPlayer];
                ai.setField(this.field);
                playerTurnAI(ai);
            }
        }
    }

    /**
     * Handles the turn after the swapped_with_hand special token has been played.
     *
     * @param token         - The token to be set
     * @param coordinate    - The position on the field
     * @param gameField     - The field
     * @param activePlayers - The amount of active players
     */
    private void handleTurnSwappedWithHand(Tokens token, GamePositions coordinate, Tokens[][] gameField,
                                           GamePositions draggedFromPos, int activePlayers) {
        int[] horizontalScore = new int[gameField.length];
        int[] verticalScore = new int[gameField.length];
        Tokens targetToken = gameField[coordinate.getX()][coordinate.getY()];
        // Setting token and displaying on the gui
        this.field.setToken(token, coordinate);
        this.gui.displayTokenOnField(coordinate, token);
        // Adding swapped token to hand
        this.players[idxCurrentPlayer].removeFromHand(token);
        this.players[idxCurrentPlayer].addToHand(targetToken);
        Tokens nextToken = this.tokenPouch.getTokenPouch().poll();
        if (nextToken != null) {
            this.players[idxCurrentPlayer].addToHand(nextToken);
        } else {
            this.players[idxCurrentPlayer].addToHand(EMPTY);
        }
        // Calculating points for columns and rows and setting them on the gui
        calculateAndSetPoints(gameField, horizontalScore, verticalScore);
        // Setting the total score of both teams
        this.gui.setTotalScore(getTeamScore(VERTICAL), VERTICAL);
        this.gui.setTotalScore(getTeamScore(HORIZONTAL), HORIZONTAL);
        writeToLog(buildSpecialMessage(SWAP_ON_BOARD, token, targetToken, coordinate, draggedFromPos));
        // Checking if game is finished
        if (getWinningTeam(isForcedEnd) == null) {
            boolean wasAi = this.players[idxCurrentPlayer].isAi();
            this.idxCurrentPlayer = ++this.idxCurrentPlayer % activePlayers;
            if (!isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand())) {
                // Skipping turn if next turn is unplayable
                skipTurn();
            } else {
                // Initializing pause between turns if previous player was an AI
                if (wasAi) {
                    this.gui.waitForNextTurn(this, coordinate, draggedFromPos);
                } else {
                    // Setting next player
                    this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(),
                            this.players[idxCurrentPlayer].getTeam(), this.players[idxCurrentPlayer].getPlayerHand(),
                            this.players[idxCurrentPlayer].isAi());
                }

            }
        } else {
            // Logging end of game
            writeToLog(logGameStatus(true));
            boolean isWinningSixVertical = false;
            boolean isWinningSixHorizontal = false;
            for (int i = 0; i < this.field.getField().length && !isWinningSixVertical &&
                    !isWinningSixHorizontal; i++) {
                isWinningSixHorizontal = this.field.isWinningSix(i, HORIZONTAL);
                isWinningSixVertical = this.field.isWinningSix(i, VERTICAL);
            }

            // Handling end of the game
            handleEndOfGame(isForcedEnd, horizontalScore, verticalScore,
                    isWinningSixHorizontal || isWinningSixVertical);
        }


    }


    /**
     * Handles the turn after the swapped_on_board special token has been played.
     *
     * @param token          - The token to be set
     * @param coordinate     - The position on the field
     * @param gameField      - The field
     * @param draggedFromPos - The position where the token was dragged from
     * @param activePlayers  - The amount of active players
     */
    private void handleTurnSwappedOnBoard(Tokens token, GamePositions coordinate, Tokens[][] gameField,
                                          GamePositions draggedFromPos, int activePlayers) {
        int[] horizontalScore = new int[gameField.length];
        int[] verticalScore = new int[gameField.length];
        Tokens targetToken = gameField[coordinate.getX()][coordinate.getY()];
        // Setting tokens and displaying on gui
        this.field.setToken(token, coordinate);
        this.field.setToken(targetToken, draggedFromPos);
        this.gui.displayTokenOnField(coordinate, token);
        this.gui.displayTokenOnField(draggedFromPos, targetToken);
        // Getting new token from pouch if not empty
        Tokens nextToken = this.tokenPouch.getTokenPouch().poll();
        if (nextToken != null) {
            this.players[idxCurrentPlayer].addToHand(nextToken);
        } else {
            this.players[idxCurrentPlayer].addToHand(EMPTY);
        }

        // Calculating points for columns and rows and setting them on the gui
        calculateAndSetPoints(gameField, horizontalScore, verticalScore);

        // Setting the total score of both teams
        this.gui.setTotalScore(getTeamScore(VERTICAL), VERTICAL);
        this.gui.setTotalScore(getTeamScore(HORIZONTAL), HORIZONTAL);
        writeToLog(buildSpecialMessage(SWAP_ON_BOARD, token, targetToken, coordinate, draggedFromPos));
        // Checking if game is finished
        if (getWinningTeam(isForcedEnd) == null) {
            boolean wasAI = this.players[idxCurrentPlayer].isAi();
            this.idxCurrentPlayer = ++this.idxCurrentPlayer % activePlayers;
            // Setting next player
            if (!isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand())) {
                // Skipping turn if next turn is unplayable
                skipTurn();
            } else {
                // Initializing pause between turns if previous player was an AI
                if (wasAI) {
                    this.gui.waitForNextTurn(this, coordinate, draggedFromPos);
                } else {
                    // Setting next player
                    this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(),
                            this.players[idxCurrentPlayer].getTeam(), this.players[idxCurrentPlayer].getPlayerHand(),
                            this.players[idxCurrentPlayer].isAi());
                }
            }
        } else {
            // Logging end of game
            writeToLog(logGameStatus(true));
            boolean isWinningSixVertical = false;
            boolean isWinningSixHorizontal = false;
            for (int i = 0; i < this.field.getField().length && !isWinningSixVertical &&
                    !isWinningSixHorizontal; i++) {
                isWinningSixHorizontal = this.field.isWinningSix(i, HORIZONTAL);
                isWinningSixVertical = this.field.isWinningSix(i, VERTICAL);
            }
            // Handling end of the game
            handleEndOfGame(isForcedEnd, horizontalScore, verticalScore,
                    isWinningSixHorizontal || isWinningSixVertical);
        }
        // Resetting drag-and-drop options
        gui.resetDragAndDrop();

    }

    /**
     * Handles the turn after the special token for moving has been played.
     *
     * @param token          - The token to be moved
     * @param coordinate     - The position in the field
     * @param gameField      - The field
     * @param draggedFromPos - The position where the token was dragged from
     * @param activePlayers  - The amount of active players
     */
    private void handleMoveTurn(Tokens token, GamePositions coordinate, Tokens[][] gameField,
                                GamePositions draggedFromPos, int activePlayers) {
        int[] horizontalScore = new int[gameField.length];
        int[] verticalScore = new int[gameField.length];
        // Setting token and swapping previous position to an empty field
        this.field.setToken(token, coordinate);
        this.field.setToken(EMPTY, draggedFromPos);
        // Displaying on gui
        this.gui.displayTokenOnField(coordinate, token);
        this.gui.displayTokenOnField(draggedFromPos, EMPTY);
        // Getting new token if pouch is not empty
        Tokens nextToken = this.tokenPouch.getTokenPouch().poll();
        if (nextToken != null) {
            this.players[idxCurrentPlayer].addToHand(nextToken);
        } else {
            this.players[idxCurrentPlayer].addToHand(EMPTY);
        }

        // Calculating points for columns and rows and setting them on the gui
        calculateAndSetPoints(gameField, horizontalScore, verticalScore);

        // Setting the total score of both teams
        this.gui.setTotalScore(getTeamScore(VERTICAL), VERTICAL);
        this.gui.setTotalScore(getTeamScore(HORIZONTAL), HORIZONTAL);
        writeToLog(buildSpecialMessage(MOVE_STONE, token, null, coordinate, draggedFromPos));
        // Checking if game is finished
        if (getWinningTeam(isForcedEnd) == null) {
            boolean wasAi = this.players[idxCurrentPlayer].isAi();
            this.idxCurrentPlayer = ++this.idxCurrentPlayer % activePlayers;
            if (!isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand())) {
                // Skipping turn if next turn is unplayable
                skipTurn();
            } else {
                // Initializing pause between turns if previous player was an AI
                if (wasAi) {
                    this.gui.waitForNextTurn(this, coordinate, draggedFromPos);
                } else {
                    // Setting next player
                    this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(),
                            this.players[idxCurrentPlayer].getTeam(), this.players[idxCurrentPlayer].getPlayerHand(),
                            this.players[idxCurrentPlayer].isAi());
                }
            }
        } else {
            // Logging end of game
            writeToLog(logGameStatus(true));
            boolean isWinningSixVertical = false;
            boolean isWinningSixHorizontal = false;
            for (int i = 0; i < this.field.getField().length && !isWinningSixVertical &&
                    !isWinningSixHorizontal; i++) {
                isWinningSixHorizontal = this.field.isWinningSix(i, HORIZONTAL);
                isWinningSixVertical = this.field.isWinningSix(i, VERTICAL);
            }

            // Handling end of the game
            handleEndOfGame(isForcedEnd, horizontalScore, verticalScore,
                    isWinningSixHorizontal || isWinningSixVertical);
        }
        // Resetting drag-and-drop options
        this.gui.resetDragAndDrop();
    }

    /**
     * Calculates and sets the points for assisting point tables at the columns and rows
     *
     * @param gameField       - The field
     * @param horizontalScore - Array for storing the points of the individual rows
     * @param verticalScore   - Array for storing the points of the individual columns
     */
    private void calculateAndSetPoints(Tokens[][] gameField, int[] horizontalScore, int[] verticalScore) {
        for (int i = 0; i < gameField.length; i++) {
            // Calculating points for each row and column
            horizontalScore[i] = getPointsAtRowOrCol(i, HORIZONTAL);
            verticalScore[i] = getPointsAtRowOrCol(i, VERTICAL);
        }
        // Setting points in the gui
        this.gui.setPointLabels(horizontalScore, verticalScore);
    }


    /**
     * Gets the amount of active players.
     *
     * @return The amount of active players.
     */
    private int getActivePlayers() {
        int activePlayers = 0;
        for (Player player : this.players) {
            // Checking if player is active
            if (player.isActive()) {
                activePlayers++;
            }
        }
        return activePlayers;
    }


    /**
     * Method gets the winning team of the game.
     *
     * @param isForcedEnd - Forces the game to end
     * @return The name of the winning team.
     */
    Team getWinningTeam(boolean isForcedEnd) {
        Team winner;
        boolean isWinningSixVertical = false;
        boolean isWinningSixHorizontal = false;
        int teamScoreHorizontal = getTeamScore(HORIZONTAL);
        int teamScoreVertical = getTeamScore(VERTICAL);
        // Checking if there are six tokens of the same kind in a row or column
        for (int i = 0; i < this.field.getField().length && !isWinningSixVertical && !isWinningSixHorizontal; i++) {
            isWinningSixHorizontal = this.field.isWinningSix(i, HORIZONTAL);
            isWinningSixVertical = this.field.isWinningSix(i, VERTICAL);
        }
        if (isWinningSixVertical && !isForcedEnd) {
            winner = VERTICAL;
        } else if (isWinningSixHorizontal && !isForcedEnd) {
            winner = HORIZONTAL;
        }
        // Game continues if there still are empty cells left
        else if (this.field.emptyCellsLeft() && !isForcedEnd) {
            winner = null;
            // Checking which team scored higher or if the game is tied
        } else if (teamScoreHorizontal == teamScoreVertical) {
            winner = NONE;
        } else if (teamScoreHorizontal < teamScoreVertical) {
            winner = VERTICAL;
        } else {
            winner = HORIZONTAL;
        }
        return winner;
    }

    /**
     * Method handles the end of the game
     *
     * @param isForcedEnd      - if true, forces end of the game
     * @param horizontalPoints - Points of the horizontal team
     * @param verticalPoints   - Points of the vertical Team
     * @param isWinningSix     - Flag for informing the gui if a game has been won by sixes
     */
    private void handleEndOfGame(boolean isForcedEnd, int[] horizontalPoints, int[] verticalPoints,
                                 boolean isWinningSix) {
        this.gui.endOfGame(getWinningTeam(isForcedEnd), horizontalPoints, verticalPoints, isWinningSix);
    }


    /**
     * Method handles the turn at which a special token has been played.
     *
     * @param token          The played special token
     * @param pos            - The position on the field
     * @param draggedFromPos - The position where the token has been dragged from
     */
    public void handleSpecialToken(Tokens token, GamePositions pos, GamePositions draggedFromPos) {
        // Getting amount of active players
        int activePlayers = getActivePlayers();
        switch (token) {
            // Handling turn according to special token
            case REMOVE_STONE -> handleRemoveStone(pos, activePlayers, token, draggedFromPos);
            case MOVE_STONE -> handleMoveStone(token, draggedFromPos);
            case SWAP_WITH_HAND -> handleSwapWithHand(token, draggedFromPos);
            case SWAP_ON_BOARD -> handleSwapOnBoard(token, draggedFromPos);
        }
    }


    /**
     * Method handles the turn at which the remove_stone has been played.
     *
     * @param pos            - The position of the token in the field
     * @param activePlayers  - The amount of active players
     * @param token          - The played token
     * @param draggedFromPos - The position where the remover was played from
     */
    private void handleRemoveStone(GamePositions pos, int activePlayers, Tokens token, GamePositions draggedFromPos) {
        Tokens[][] gameField = this.field.getField();
        int[] horizontalScore = new int[gameField.length];
        int[] verticalScore = new int[gameField.length];
        Tokens targetToken = gameField[pos.getX()][pos.getY()];
        // Removing token from hand and adding removed token from the field to the hand
        this.players[idxCurrentPlayer].removeFromHand(token);
        this.players[idxCurrentPlayer].addToHand(targetToken);
        // Removing token from the field
        this.field.setToken(EMPTY, pos);
        this.gui.displayTokenOnField(pos, EMPTY);
        // Calculating points for columns and rows and setting them on the gui
        calculateAndSetPoints(gameField, horizontalScore, verticalScore);
        // Setting total score for both teams
        this.gui.setTotalScore(getTeamScore(VERTICAL), VERTICAL);
        this.gui.setTotalScore(getTeamScore(HORIZONTAL), HORIZONTAL);
        usedSpecialTokens[0]++;
        this.gui.updateAmountUsedSpecialTokens(REMOVE_STONE, usedSpecialTokens[0]);
        writeToLog(buildSpecialMessage(REMOVE_STONE, targetToken, null, pos, draggedFromPos));
        // Checking if game is finished
        boolean wasAi = this.players[idxCurrentPlayer].isAi();
        this.idxCurrentPlayer = ++this.idxCurrentPlayer % activePlayers;
        if (!isPlayableTurn(this.players[idxCurrentPlayer].getPlayerHand())) {
            skipTurn();
        } else {
            if (wasAi) {
                this.gui.waitForNextTurn(this, pos, null);
            } else {
                this.gui.setCurrPlayer(this.players[idxCurrentPlayer].getPlayerName(),
                        this.players[idxCurrentPlayer].getTeam(), this.players[idxCurrentPlayer].getPlayerHand(),
                        this.players[idxCurrentPlayer].isAi());
            }
        }

    }


    /**
     * Handles the turn at which the move token has been played.
     *
     * @param token          - The token.
     * @param draggedFromPos - The position where the token has been dragged from.
     */
    private void handleMoveStone(Tokens token, GamePositions draggedFromPos) {
        // Removing token from hand
        this.players[idxCurrentPlayer].removeFromHand(token);
        // Setting info according to special token
        this.gui.setSpecialTokenInfo(MOVE_STONE);
        // Removing token from gui
        this.gui.removeFromPlayerHand(draggedFromPos);
        if (!players[idxCurrentPlayer].isAi()) {
            this.gui.deactivateDragOverOnNonEmptyFields();
        }
        // Updating used special tokens on gui
        usedSpecialTokens[1]++;
        this.gui.updateAmountUsedSpecialTokens(MOVE_STONE, usedSpecialTokens[1]);
        writeToLog(buildMessagePlacedToken(null, token, draggedFromPos.getX()));

    }

    /**
     * Handles the turn at which the swap_on_board token has been played.
     *
     * @param token          - The token
     * @param draggedFromPos - The position where the token has been dragged from
     */
    private void handleSwapOnBoard(Tokens token, GamePositions draggedFromPos) {
        // Removing token from hand
        this.players[idxCurrentPlayer].removeFromHand(token);
        // Setting info according to special token
        this.gui.setSpecialTokenInfo(SWAP_ON_BOARD);
        // Removing token from gui
        this.gui.removeFromPlayerHand(draggedFromPos);
        if (!players[idxCurrentPlayer].isAi()) {
            this.gui.deactivateDragOverOnEmptyFields();
        }
        usedSpecialTokens[2]++;
        this.gui.updateAmountUsedSpecialTokens(SWAP_ON_BOARD, usedSpecialTokens[2]);
        writeToLog(buildMessagePlacedToken(null, token, draggedFromPos.getX()));
    }

    /**
     * Handles the turn at which the swap_with_hand token has been played.
     *
     * @param token          - The token
     * @param draggedFromPos - The position where the token has been dragged from
     */
    private void handleSwapWithHand(Tokens token, GamePositions draggedFromPos) {
        // Removing token from hand
        this.players[idxCurrentPlayer].removeFromHand(token);
        // Setting info according to special token
        this.gui.setSpecialTokenInfo(SWAP_WITH_HAND);
        // Removing token from gui
        this.gui.removeFromPlayerHand(draggedFromPos);
        if (!this.players[idxCurrentPlayer].isAi()) {
            this.gui.disableDragSpecialTokens();
            this.gui.deactivateDragOverOnEmptyFields();
        }
        usedSpecialTokens[3]++;
        this.gui.updateAmountUsedSpecialTokens(SWAP_WITH_HAND, usedSpecialTokens[3]);
        writeToLog(buildMessagePlacedToken(null, token, draggedFromPos.getX()));

    }


    /**
     * Checks if a turn is playable or not
     *
     * @param playerhand - The hand of the player
     * @return True, if turn is playable, false if not
     */
    boolean isPlayableTurn(List<Tokens> playerhand) {
        boolean isPlayableTurn = false;
        for (int i = 0; i < playerhand.size() && !isPlayableTurn; i++) {
            Tokens token = playerhand.get(i);
            if (token != EMPTY) {
                if (isSpecialToken(token)) {
                    switch (token) {
                        case REMOVE_STONE:
                            // Field must not be empty
                            isPlayableTurn = !this.field.fieldIsEmpty();
                            break;
                        case MOVE_STONE:
                            // Field must have empty cells and field must not be completely empty
                            isPlayableTurn = this.field.emptyCellsLeft() && !this.field.fieldIsEmpty();
                            break;
                        case SWAP_ON_BOARD:
                            // At least two tokens have to be on the board
                            isPlayableTurn = this.field.countTokensOnField() >= 2;
                            break;
                        case SWAP_WITH_HAND:
                            // At least one regular token must be on the hand and the field must not be empty
                            for (int j = 0; j < playerhand.size() && !isPlayableTurn; j++) {
                                isPlayableTurn = !isSpecialToken(playerhand.get(j)) && !this.field.fieldIsEmpty()
                                        && playerhand.get(j) != EMPTY;
                            }
                            break;
                    }
                } else {
                    // A regular token can only be played if there are empty cells left
                    isPlayableTurn = this.field.emptyCellsLeft();
                }
            }
        }
        return isPlayableTurn;
    }


    /**
     * Deletes the log made from the previous game.
     */
    private void deleteLogFile() {
        String pathToLog = "./src/logging/log.txt";
        new File(pathToLog).delete();
    }

    /**
     * Writes an entry into the log.
     *
     * @param entry - The entry
     */
    private void writeToLog(String entry) {
        if (PRINT_ON_CONSOLE) {
            System.out.println(entry);
        }
        try {
            FileWriter writer = new FileWriter("./src/logging/log.txt", true);
            writer.write(entry);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Builds the message for a skipped turn.
     *
     * @return The message for a skipped turn
     */
    private String buildTurnSkippedLog() {
        StringBuilder sb = new StringBuilder();
        int handSize = this.players[idxCurrentPlayer].getPlayerHand().size();
        sb.append(this.players[idxCurrentPlayer].getPlayerName() + " | ");
        sb.append("Turn skipped | Unplayable turn | ");
        sb.append("hand: [");
        logPlayerHand(sb, handSize, this.players[idxCurrentPlayer].getPlayerHand());
        return sb.toString();
    }



    /**
     * Creates an entry for the log when the game is started.
     * Lists all their players, their activity status, if they are an AI or not and their starting hand.
     *
     * @return The initial game info.
     */
    private String buildInitialGameLog() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.players.length; i++) {
            // Getting hands, names, activity status and AI status of the players
            List<Tokens> hand = this.players[i].getPlayerHand();
            sb.append("Player" + i + " | ");
            sb.append(this.players[i].getPlayerName() + " | ");
            sb.append("isActive: " + this.players[i].isActive() + " | ");
            if (this.players[i].isAi()) {
                sb.append("AI | ");
            } else {
                sb.append("Human | ");
            }
            // Gets hand information about the players
            sb.append("hand: [");
            logPlayerHand(sb, hand.size(), hand);
        }
        return sb.toString();
    }

    /**
     * Creates an entry for the log when a token has been placed.
     *
     * @param pos         - The position where the token got placed.
     * @param token       - The token.
     * @param handSlotPos - The position of the token in the hand of the player.
     * @return An entry for the log
     */
    private String buildMessagePlacedToken(GamePositions pos, Tokens token, int handSlotPos) {
        StringBuilder sb = new StringBuilder();

        List<Tokens> hand = this.players[idxCurrentPlayer].getPlayerHand();
        sb.append(this.players[idxCurrentPlayer].getPlayerName() + " | ");
        // Appending special turn message
        if (token == MOVE_STONE || token == SWAP_ON_BOARD || token == SWAP_WITH_HAND) {
            sb.append("Used special field token [" + token.ordinal() + "], special turn follows | ");
        } else {
            sb.append("Token [" + token.ordinal() + "] " + "placed at [" + pos.getX() + "|" + pos.getY() + "] | ");
        }
        // Getting hand slot position
        sb.append("Token pulled from hand at position [" + handSlotPos + "] | ");
        sb.append("Hand is now: [");
        int handSize = hand.size();
        // logging hand of the player
        logPlayerHand(sb, handSize, hand);
        if (token != MOVE_STONE && token != SWAP_ON_BOARD && token != SWAP_WITH_HAND) {
            Tokens[][] gameField = this.field.getField();
            logGameField(sb, gameField);
            logUsedSpecialTiles(sb, this.usedSpecialTokens);
        }
        return sb.toString();
    }

    /**
     * Creates an entry for the log when a special turn (e.g. removing a stone) has been made.
     *
     * @param specialToken   - The special token
     * @param token          - The token that got played
     * @param swappedToken   - The token that got swapped on the field
     * @param pos            - The position where the token has been placed.
     * @param draggedFromPos - The position from where the token got dragged from.
     * @return An entry for the log.
     */
    private String buildSpecialMessage(Tokens specialToken, Tokens token, Tokens swappedToken,
                                       GamePositions pos, GamePositions draggedFromPos) {
        StringBuilder sb = new StringBuilder();
        // Getting hand of the player
        List<Tokens> hand = this.players[idxCurrentPlayer].getPlayerHand();
        int handSize = hand.size();
        Tokens[][] gameField = this.field.getField();
        // Building special message according to special token
        switch (specialToken) {
            case MOVE_STONE -> {
                sb.append(this.players[idxCurrentPlayer].getPlayerName() + " | ");
                sb.append("Token [" + token.ordinal() + "] " + "from [" + draggedFromPos.getX() + "|" + draggedFromPos.getY()
                        + "] moved to [" + pos.getX() + "|" + pos.getY() + "] | ");
                sb.append("Hand is now: [");
                // Logging hand, field and used special tokens
                logPlayerHand(sb, handSize, hand);
                logGameField(sb, gameField);
                logUsedSpecialTiles(sb, this.usedSpecialTokens);
            }
            case SWAP_ON_BOARD -> {
                sb.append(this.players[idxCurrentPlayer].getPlayerName() + " | ");
                sb.append("Token [" + token.ordinal() + "] " + "swapped with token at [" + pos.getX() + "|" + pos.getY() + "] | ");
                sb.append("Swapped token [" + swappedToken.ordinal() + "] now at [" + draggedFromPos.getX() + "|" + draggedFromPos.getY() + "] | ");
                sb.append("Hand is now: [");
                // Logging hand, field and used special tokens
                logPlayerHand(sb, handSize, hand);
                logGameField(sb, gameField);
                logUsedSpecialTiles(sb, this.usedSpecialTokens);
            }
            case SWAP_WITH_HAND -> {
                sb.append(this.players[idxCurrentPlayer].getPlayerName() + " | ");
                sb.append("Token [" + token.ordinal() + "] from hand " + "swapped with token at [" + pos.getX() + "|" + pos.getY() + "] | ");
                sb.append("Token pulled from hand at position [" + draggedFromPos.getX() + "] | ");
                sb.append("Hand is now: [");
                // Logging hand, field and used special tokens
                logPlayerHand(sb, handSize, hand);
                logGameField(sb, gameField);
                logUsedSpecialTiles(sb, this.usedSpecialTokens);
            }
            case REMOVE_STONE -> {
                sb.append(this.players[idxCurrentPlayer].getPlayerName() + " | ");
                sb.append("Token [" + token.ordinal() + "] removed at [" + pos.getX() + "|" + pos.getY() + "] | ");
                sb.append("Token pulled from hand at position [" + draggedFromPos.getX() + "] | ");
                sb.append("Hand is now: [");
                // Logging hand, field and used special tokens
                logPlayerHand(sb, handSize, hand);
                logGameField(sb, gameField);
                logUsedSpecialTiles(sb, this.usedSpecialTokens);
            }
        }
        return sb.toString();
    }

    /**
     * Logs the hand of a player.
     *
     * @param sb       - The StringBuilder where information gets appended.
     * @param handSize - Size of the hand of the player
     * @param hand     - The hand of the player
     */
    private void logPlayerHand(StringBuilder sb, int handSize, List<Tokens> hand) {
        for (int i = 0; i < handSize; i++) {
            // Appending ordinal value
            sb.append(hand.get(i).ordinal());
            if (i < handSize - 1) {
                sb.append(", ");
            }
        }
        sb.append("]\n\n");
    }

    /**
     * logs the field.
     *
     * @param sb        - The StringBuilder where information gets appended.
     * @param gameField - The field.
     */
    private void logGameField(StringBuilder sb, Tokens[][] gameField) {
        sb.append("Field is now: [\n");
        for (int i = 0; i < gameField.length; i++) {
            sb.append("[");
            for (int j = 0; j < gameField[i].length; j++) {
                // Appending ordinal value
                sb.append(gameField[i][j].ordinal());
                if (j < gameField[i].length - 1) {
                    sb.append(", ");
                }
            }
            if (i < gameField.length - 1) {
                sb.append("],\n");
            } else {
                sb.append("]\n");
            }
        }
        sb.append("],\n\n");
    }

    /**
     * Logs the game status (finished, ongoing).
     *
     * @param gameIsFinished - Determines if a game is finished or not.
     * @return An entry for the log.
     */
    private String logGameStatus(boolean gameIsFinished) {
        if (gameIsFinished) {
            return "Game status: Finished game\n";
        } else {
            return "Game status: Ongoing\n";

        }
    }


    /**
     * Builds a message that a game has been forced to end
     *
     * @return - A message containing information that a game has been forced to end.
     */
    private String logForcedEnd() {
        return "Game has been forced to end, no player can play a token.\n\n";
    }

    /**
     * Logs the used special tokens.
     *
     * @param sb                The StringBuilder
     * @param usedSpecialTokens - Amount of used special tokens
     */
    private void logUsedSpecialTiles(StringBuilder sb, int[] usedSpecialTokens) {
        sb.append("usedActionTiles: [");
        for (int i = 0; i < usedSpecialTokens.length; i++) {
            sb.append(usedSpecialTokens[i]);
            if (i < usedSpecialTokens.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]\n");
    }


}
