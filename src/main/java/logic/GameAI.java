package logic;

import java.util.*;

import static logic.Team.HORIZONTAL;
import static logic.Team.VERTICAL;
import static logic.Tokens.*;

/**
 * Class for the AI of the game.
 *
 * @author Florian Kern/cgt104661
 */
public class GameAI extends Player {

    /**
     * The field of the game.
     */
    private GameField field;


    /**
     * Sets the field of the game.
     *
     * @param field - The field
     */
    void setField(GameField field) {
        this.field = field;
    }


    /**
     * Constructor for constructing an AI for the game.
     *
     * @param playerName - Name of the AI player.
     * @param team       - The team the AI plays for.
     * @param isActive   - Is the player participating?
     * @param isAi       - Is the player an AI?
     */
    public GameAI(String playerName, Team team, boolean isActive, boolean isAi) {
        super(playerName, team, isActive, isAi);
    }


    /**
     * Constructor for testing.
     *
     * @param playerName - Name of the player
     * @param team       - The team the AI plays for.
     * @param isActive   - Is the player participating?
     * @param isAi       - Is the player an AI?
     * @param hand       -          Hand of the player
     */
    GameAI(String playerName, Team team, boolean isActive, boolean isAi, int[] hand) {
        super(playerName, team, isActive, isAi, hand);
    }

    /**
     * Method for getting the best possible turn for the current situation in the game.
     * Iterates through the hand, gets all possible turns the AI can do and reduces them down
     * to one single, best turn.
     *
     * @return - The best turn for the current situation.
     */
    AITurn getBestTurn() {
        List<Tokens> playerHand = this.getPlayerHand();
        List<AITurn> tokenTurns = new ArrayList<>();
        List<AITurn> allTurns = new ArrayList<>();
        AITurn res = null;
        int handSize = playerHand.size();
        // Iterating through playerhand, stops if a win by six gets determined
        boolean isWinningSixTurn = false;
        for (int i = 0; i < handSize && !isWinningSixTurn; i++) {
            Tokens token = playerHand.get(i);
            if (token != EMPTY) {
                // Checking if current token is special or not and getting the possible turns
                if (Tokens.isSpecialToken(token)) {
                    switch (token) {
                        case REMOVE_STONE -> tokenTurns = getTurnsRemoveStone(i);
                        case MOVE_STONE -> tokenTurns = getTurnsMoveStone(i);
                        case SWAP_ON_BOARD -> tokenTurns = getTurnsSwapOnBoard(i);
                        case SWAP_WITH_HAND -> tokenTurns = getTurnsSwapWithHand();
                    }
                } else {
                    // Getting possible turns for regular tokens
                    tokenTurns = getTurnsStandardToken(i);
                }
                // The result gets set if a turn leads to a win by six tokens in a row/column
                if (tokenTurns.size() == 1 && tokenTurns.get(0).isWinningSixTurn()) {
                    res = tokenTurns.get(0);
                    isWinningSixTurn = true;

                    // Setting result if a turn prevents a winning six by the opponent team
                } else if (tokenTurns.size() == 1 && tokenTurns.get(0).isPreventsSixOpponent()) {
                    res = tokenTurns.get(0);
                } else {
                    // Adding all possible turns
                    allTurns.addAll(tokenTurns);
                }
            }
        }
        if (res == null) {
            // Reducing the turns down to the best turn
            List<AITurn> filteredTurns = filterTurns(allTurns);
            // Setting the result if there is a possible turn
            if (filteredTurns.size() >= 1) {
                res = filteredTurns.get(0);
            }
        }
        return res;
    }

    /**
     * Method reduced the given turns down to the best turn.
     * The turn that leads to the most points is the best turn. If there are
     * multiple turns that lead to the same amount of points the following rules apply:
     * <p>
     * - A regular token is favored over a special token
     * - The token that is most frequent on the hand of the player gets taken
     * - The token that appears the least amount of times on the board gets taken
     * - If there are still multiple turns to choose from, the token with the smallest ordinal is taken
     * - If there are multiple favourable positions for this token, the smallest position gets taken. A
     * position is smaller the more up and the more left it is on the field.
     * </p>
     *
     * @param aiTurns - List of possible turns
     * @return - The best turn for the current situation in the game
     */
    private List<AITurn> filterTurns(List<AITurn> aiTurns) {
        List<AITurn> turns = new ArrayList<>(aiTurns);

        List<AITurn> turnsMostPoints = new ArrayList<>();
        if (turns.size() > 1) {
            // Getting the turns that lead to the most points
            AITurn max = Collections.max(turns, Comparator.comparing(AITurn::getGeneratedPoints));
            for (AITurn turn : turns) {
                if (turn.getGeneratedPoints() == max.getGeneratedPoints()) {
                    turnsMostPoints.add(turn);
                }
            }
            turns = new ArrayList<>(turnsMostPoints);
        }


        List<AITurn> specialTurnsRes = null;
        List<AITurn> specialTurns = new ArrayList<>();
        List<AITurn> standardTurns = new ArrayList<>();
        boolean doesNotContainRegularTokens = false;
        if (turns.size() > 1) {
            for (AITurn turn : turns) {
                // Checking for used special tokens
                if (turn.isSpecialTurn() || turn.getUsedSpecialToken() != null) {
                    specialTurns.add(turn);
                } else {
                    standardTurns.add(turn);
                }
            }
            // Turns that include regular tokens are favored before turns that include special tokens
            turns = new ArrayList<>(standardTurns.size() > 0 ? standardTurns : specialTurns);
            if (standardTurns.size() == 0) {
                doesNotContainRegularTokens = true;
            }
        }

        // If there aren't any turns that include regular tokens, the special turns get reduced to the best turn
        if (doesNotContainRegularTokens) {
            specialTurnsRes = filterSpecialTurns(turns);
        } else {


            List<Tokens> playerHand = this.getPlayerHand();
            List<Tokens> relevantTokens = new ArrayList<>();
            // Getting the tokens of the hand of the player that are needed for
            // the reduction to the best turn
            for (Tokens tokens : playerHand) {
                for (AITurn turn : turns) {
                    if (tokens == turn.getToken() && !relevantTokens.contains(tokens)) {
                        relevantTokens.add(tokens);
                    }
                }
            }

            // Getting the max occurrences of a token in the hand of the player
            List<AITurn> mostOccurringTokens = new ArrayList<>();
            int[] tokenOccurrences = getTokenOccurrencesOnHand(relevantTokens, playerHand);
            int maxOccurrences = 0;
            for (int tokenOccurrence : tokenOccurrences) {
                if (tokenOccurrence > maxOccurrences) {
                    maxOccurrences = tokenOccurrence;
                }
            }

            Tokens[][] gameField = this.field.getField();
            if (turns.size() > 1) {
                // Getting the turns that include the tokens that match the max occurrences on the
                // hand of the player
                for (AITurn turn : turns) {
                    if (getOccurrenceOfSingleTokenOnHand(playerHand, turn.getToken()) == maxOccurrences) {
                        mostOccurringTokens.add(turn);
                    }
                }
                turns = new ArrayList<>(mostOccurringTokens);
            }

            List<AITurn> leastOccurringTokensBoard = new ArrayList<>();
            Set<Tokens> tokensAiTurns = new HashSet<>();
            // Inserting tokens of turns into set, so they are only checked once
            for (AITurn aiTurn : turns) {
                tokensAiTurns.add(aiTurn.getToken());
            }
            // Getting the least amount of occurrences of a token on the board
            int leastAmountTokenOnBoard = getLeastTokenOccurrencesOnBoard(tokensAiTurns);

            if (turns.size() > 1) {
                // Checking if a token of a turn matches the least amount of occurrences of a token on the board
                for (AITurn turn : turns) {
                    // Getting the turns whose tokens are played the least on the board
                    if (getTokenOccurrenceOfSingleTokenOnBoard(gameField, turn.getToken())
                            == leastAmountTokenOnBoard) {
                        leastOccurringTokensBoard.add(turn);
                    }
                }
                turns = new ArrayList<>(leastOccurringTokensBoard);
            }

            List<AITurn> turnsLowestOrdinal = new ArrayList<>();
            // Getting the token with the smallest ordinal
            Tokens token = getTokenSmallestOrdinal(turns);
            if (turns.size() > 1) {
                // Getting the turns whose tokens match the token with the smallest ordinal
                for (AITurn turn : turns) {
                    if (turn.getToken() == token) {
                        turnsLowestOrdinal.add(turn);
                    }
                }
                turns = new ArrayList<>(turnsLowestOrdinal);
            }

            if (turns.size() > 1) {
                // Getting the turn with the smallest position using compareTo() method
                AITurn minPosTurn = turns.get(0);
                for (int i = 1; i < turns.size(); i++) {
                    if (turns.get(i).getPos().compareTo(minPosTurn.getPos()) < 0) {
                        minPosTurn = turns.get(i);
                    }
                }
                List<AITurn> turnsSmallestPos = new ArrayList<>();
                if (turns.size() > 1) {
                    // Getting the turns whose positions match the smallest position
                    for (AITurn turn : turns) {
                        if (turn.getPos().equals(minPosTurn.getPos())) {
                            turnsSmallestPos.add(turn);
                        }
                    }
                }
                turns = new ArrayList<>(turnsSmallestPos);
            }
        }
        return specialTurnsRes == null ? turns : specialTurnsRes;
    }

    /**
     * Method reduces special turns down to a single best turn.
     *
     * <p>
     * In case turns amount to the same gain of points the following rules apply:
     * - The special token with the smallest ordinal is favored
     * - If there are multiple favourable positions for this token, the smallest position gets taken. A
     * position is smaller the more up and the more left it is on the field.
     * </p>
     *
     * @param turns - The list of possible special turns
     * @return The best turn for a special token
     */
    private List<AITurn> filterSpecialTurns(List<AITurn> turns) {
        List<AITurn> res = new ArrayList<>(turns);
        if (res.size() > 1) {
            // Getting the turns that lead to the most points
            List<AITurn> turnsMostPoints = new ArrayList<>();
            AITurn max = Collections.max(res, Comparator.comparing(AITurn::getGeneratedPoints));
            for (AITurn re : res) {
                if (re.getGeneratedPoints() == max.getGeneratedPoints()) {
                    turnsMostPoints.add(re);
                }
            }
            res = new ArrayList<>(turnsMostPoints);
        }

        if (res.size() > 1) {
            // Getting the smallest ordinal of the special tokens of the turns
            List<AITurn> turnsSmallestOrdinal = new ArrayList<>();
            int turnSmallestTokenOrdinal = res.get(0).getUsedSpecialToken().ordinal();
            for (int i = 1; i < res.size(); i++) {
                if (res.get(i).getUsedSpecialToken().ordinal() < turnSmallestTokenOrdinal) {
                    turnSmallestTokenOrdinal = res.get(i).getUsedSpecialToken().ordinal();
                }
            }
            // Getting token with the smallest ordinal and getting turns whose used special
            // tokens match it
            Tokens smallestOrdinalToken = Tokens.ordinalToToken(turnSmallestTokenOrdinal);
            for (AITurn re : res) {
                if (re.getUsedSpecialToken() == smallestOrdinalToken) {
                    turnsSmallestOrdinal.add(re);
                }
            }
            res = new ArrayList<>(turnsSmallestOrdinal);
        }

        if (res.size() > 1) {
            // Getting turn with the smallest position
            AITurn minPosTurn = res.get(0);
            for (int i = 1; i < res.size(); i++) {
                if (res.get(i).getPos().compareTo(minPosTurn.getPos()) < 0) {
                    minPosTurn = res.get(i);
                }
            }
            List<AITurn> turnsSmallesPos = new ArrayList<>();
            // Getting turns whose position are equal to the smallest position
            if (res.size() > 1) {
                for (AITurn re : res) {
                    if (re.getPos().equals(minPosTurn.getPos())) {
                        turnsSmallesPos.add(re);
                    }
                }
            }
            res = new ArrayList<>(turnsSmallesPos);
        }
        return res;
    }


    /**
     * Method gets all possible turns for a regular token
     *
     * @param handPos - The handposition from where the token was played
     * @return List with all possible turns for the token
     */
    List<AITurn> getTurnsStandardToken(int handPos) {
        // Getting current field
        Tokens[][] gameField = this.field.getField();
        AITurn bestTurn = null;
        // Checking for own team and opposing team
        Team team = this.getTeam() == VERTICAL ? VERTICAL : HORIZONTAL;
        Team opponentTeam = team == VERTICAL ? HORIZONTAL : VERTICAL;
        List<AITurn> allTurns = new ArrayList<>();

        // Iterating through field
        for (int i = 0; i < gameField.length && bestTurn == null; i++) {
            for (int j = 0; j < gameField[i].length && bestTurn == null; j++) {
                if (gameField[i][j] == Tokens.EMPTY) {
                    // Getting the unchanged field to operate on
                    Tokens[][] newField = this.field.getField();
                    newField[i][j] = this.getPlayerHand().get(handPos);
                    // Checking if column or row needs to be checked for winning six
                    if (team == VERTICAL ? isWinningSix(i, team, newField) :
                            isWinningSix(j, team, newField)) {
                        bestTurn = new AITurn(
                                new GamePositions(i, j),
                                this.getPlayerHand().get(handPos),
                                handPos,
                                false,
                                true,
                                null,
                                null,
                                null,
                                false,
                                null
                        );
                        // Checking if possible win of sixes of opponent can be prevented
                    } else if (opponentTeam == VERTICAL ? isFiveTokensOfSameKind(i, opponentTeam, newField) &&
                            !tokenLeadsToWinningSix(i, this.getPlayerHand().get(handPos), newField, opponentTeam) :
                            isFiveTokensOfSameKind(j, opponentTeam, newField) &&
                                    !tokenLeadsToWinningSix(j, this.getPlayerHand().get(handPos), newField, opponentTeam)) {
                        bestTurn = new AITurn(
                                new GamePositions(i, j),
                                this.getPlayerHand().get(handPos),
                                handPos,
                                false,
                                false,
                                null,
                                null,
                                null,
                                true,
                                null
                        );
                    }
                    if (bestTurn == null) {
                        // Getting current, new and opponent points
                        int currentTeamPoints = calculateAllPoints(team, gameField);
                        int newTeamPoints = calculateAllPoints(team, newField);
                        int opponentPoints = calculateAllPoints(opponentTeam, newField);

                        // Calculating point increase
                        int pointIncrease = newTeamPoints - currentTeamPoints;
                        // Adding turn to list
                        allTurns.add(new AITurn(
                                new GamePositions(i, j),
                                this.getPlayerHand().get(handPos),
                                handPos,
                                false,
                                false,
                                null,
                                pointIncrease,
                                opponentPoints,
                                false,
                                null
                        ));
                    }
                }
            }
        }

        List<AITurn> res = new ArrayList<>();
        if (bestTurn == null) {
            // Getting the turns that lead to maximum point increase
            AITurn max = Collections.max(allTurns, Comparator.comparing(AITurn::getGeneratedPoints));
            for (AITurn turn : allTurns) {
                if (turn.getGeneratedPoints() == max.getGeneratedPoints()) {
                    res.add(turn);
                }
            }
        }

        // Returning list of all possible turns or a list that contains a turn that leads to a win of sixes
        // or a turn that prevents a win of sixes for the opposing team
        if (bestTurn != null) {
            return List.of(bestTurn);
        } else {
            return res;
        }

    }


    /**
     * Gets all possible turns for the remover.
     *
     * @param handPos - Position from where the token was played
     * @return List of possible turns
     */
    List<AITurn> getTurnsRemoveStone(int handPos) {
        // Getting current field
        Tokens[][] gameField = this.field.getField();
        List<AITurn> allTurns = new ArrayList<>();
        AITurn bestTurn = null;
        // Getting team and opponent team
        Team team = this.getTeam() == VERTICAL ? VERTICAL : HORIZONTAL;
        Team opponentTeam = team == VERTICAL ? HORIZONTAL : VERTICAL;
        for (int i = 0; i < gameField.length && bestTurn == null; i++) {
            for (int j = 0; j < gameField[i].length && bestTurn == null; j++) {
                if (gameField[i][j] != EMPTY) {
                    // Getting the unchanged field to operate on
                    Tokens[][] newField = this.field.getField();
                    Tokens tokenToRemove = newField[i][j];
                    // Checking if a win of sixes was prevented because of removal
                    if (opponentTeam == VERTICAL ? isFiveTokensOfSameKind(i, opponentTeam, newField) :
                            isFiveTokensOfSameKind(j, opponentTeam, newField)) {
                        bestTurn = new AITurn(
                                new GamePositions(i, j),
                                tokenToRemove,
                                handPos,
                                false,
                                false,
                                REMOVE_STONE,
                                null,
                                null,
                                true,
                                null
                        );
                    }
                    // Removing stone at position
                    newField[i][j] = EMPTY;
                    if (bestTurn == null) {
                        // Getting current, new and opponent points
                        int currentTeamPoints = calculateAllPoints(team, gameField);
                        int newTeamPoints = calculateAllPoints(team, newField);
                        int opponentPoints = calculateAllPoints(opponentTeam, newField);

                        // Calculating point increase
                        int pointIncrease = newTeamPoints - currentTeamPoints;
                        // Adding turn to list
                        allTurns.add(new AITurn(
                                new GamePositions(i, j),
                                tokenToRemove,
                                handPos,
                                false,
                                false,
                                REMOVE_STONE,
                                pointIncrease,
                                opponentPoints,
                                false,
                                null
                        ));
                    }
                }
            }
        }
        List<AITurn> res = new ArrayList<>();
        if (allTurns.size() >= 1) {
            if (bestTurn == null) {
                // Getting the turns that lead to the maximum point increase
                AITurn max = Collections.max(allTurns, Comparator.comparing(AITurn::getGeneratedPoints));
                for (AITurn turn : allTurns) {
                    if (turn.getGeneratedPoints() == max.getGeneratedPoints()) {
                        res.add(turn);
                    }
                }
            }
        }
        // Returning list of all possible turns or a list that contains a
        // turn that prevents a win of sixes for the opposing team
        if (bestTurn != null) {
            return List.of(bestTurn);
        } else {
            return res;
        }
    }


    /**
     * Method gets all possible turns for the move stone.
     *
     * @param handPos - Position from where the token was played
     * @return List of possible turns
     */
    List<AITurn> getTurnsMoveStone(int handPos) {
        // Getting current field
        Tokens[][] gameField = this.field.getField();
        List<AITurn> allTurns = new ArrayList<>();
        boolean wasPossibleWinningSixOpponent;
        AITurn bestTurn = null;
        // Getting team and opposing team
        Team team = this.getTeam() == VERTICAL ? VERTICAL : HORIZONTAL;
        Team opponentTeam = team == VERTICAL ? HORIZONTAL : VERTICAL;
        for (int i = 0; i < gameField.length && bestTurn == null; i++) {
            for (int j = 0; j < gameField[i].length && bestTurn == null; j++) {
                if (gameField[i][j] != EMPTY) {
                    // Getting the unchanged field to operate on
                    Tokens[][] newField = this.field.getField();
                    // Checking if opponent has the chance to have a win of sixes
                    wasPossibleWinningSixOpponent = opponentTeam == VERTICAL ?
                            isFiveTokensOfSameKind(i, opponentTeam, newField) :
                            isFiveTokensOfSameKind(j, opponentTeam, newField);

                    Tokens tokenToMove = newField[i][j];
                    for (int k = 0; k < newField.length && bestTurn == null; k++) {
                        for (int l = 0; l < newField[k].length && bestTurn == null; l++) {
                            if (newField[k][l] == EMPTY) {
                                // Getting unchanged field to move the token on
                                Tokens[][] swapField = this.field.getField();
                                // Checking if moving the token to this location leads to win of sixes for
                                // the own team
                                if (team == VERTICAL ? tokenLeadsToWinningSix(k, tokenToMove, swapField, team) :
                                        tokenLeadsToWinningSix(l, tokenToMove, swapField, team)) {
                                    swapField[k][l] = tokenToMove;
                                    swapField[i][j] = EMPTY;
                                    bestTurn = new AITurn(
                                            new GamePositions(k, l),
                                            tokenToMove,
                                            handPos,
                                            true,
                                            true,
                                            MOVE_STONE,
                                            null,
                                            null,
                                            false,
                                            new GamePositions(i, j)
                                    );
                                    // Checking if moved token prevented win of sixes of opponent team
                                } else if (opponentTeam == VERTICAL ? !tokenLeadsToWinningSix(k, tokenToMove, swapField, opponentTeam)
                                        && wasPossibleWinningSixOpponent :
                                        !tokenLeadsToWinningSix(l, tokenToMove, swapField, opponentTeam)
                                                && wasPossibleWinningSixOpponent) {
                                    swapField[k][l] = tokenToMove;
                                    swapField[i][j] = EMPTY;
                                    bestTurn = new AITurn(
                                            new GamePositions(k, l),
                                            tokenToMove,
                                            handPos,
                                            true,
                                            false,
                                            MOVE_STONE,
                                            null,
                                            null,
                                            true,
                                            new GamePositions(i, j)
                                    );
                                    // Moving token, setting origin position to empty
                                } else {
                                    swapField[k][l] = tokenToMove;
                                    swapField[i][j] = EMPTY;
                                }
                                if (bestTurn == null) {
                                    // Getting current, new and opponent points
                                    int currentTeamPoints = calculateAllPoints(team, gameField);
                                    int newTeamPoints = calculateAllPoints(team, swapField);
                                    int opponentPoints = calculateAllPoints(opponentTeam, swapField);
                                    // Calculating point increase
                                    int pointIncrease = newTeamPoints - currentTeamPoints;
                                    // Adding turn to list
                                    allTurns.add(new AITurn(
                                            new GamePositions(k, l),
                                            tokenToMove,
                                            handPos,
                                            true,
                                            false,
                                            MOVE_STONE,
                                            pointIncrease,
                                            opponentPoints,
                                            false,
                                            new GamePositions(i, j)
                                    ));
                                }
                            }
                        }
                    }
                }
            }
        }

        List<AITurn> res = new ArrayList<>();
        if (allTurns.size() >= 1) {
            if (bestTurn == null) {
                // Getting the turns that lead to the maximum point increase
                AITurn max = Collections.max(allTurns, Comparator.comparing(AITurn::getGeneratedPoints));
                for (AITurn turn : allTurns) {
                    if (turn.getGeneratedPoints() == max.getGeneratedPoints()) {
                        res.add(turn);
                    }
                }
                if (res.size() > 1) {
                    // Getting the smallest startest position if there are multiple turns
                    // that lead to same amount of points
                    AITurn minPosTurn = res.get(0);
                    for (int i = 1; i < res.size(); i++) {
                        if (res.get(i).getDraggedFrom().compareTo(minPosTurn.getDraggedFrom()) < 0) {
                            minPosTurn = res.get(i);
                        }
                    }
                    // Getting turns whose start position are equal to the smallest starting position
                    List<AITurn> smallestStartPos = new ArrayList<>();
                    for (AITurn turn : res) {
                        if (turn.getDraggedFrom().equals(minPosTurn.getDraggedFrom())) {
                            smallestStartPos.add(turn);
                        }
                    }
                    res = new ArrayList<>(smallestStartPos);
                }
            }
        }
        // Returning list of all possible turns or a list that contains a turn that leads to a win of sixes
        // or a turn that prevents a win of sixes for the opposing team
        if (bestTurn != null) {
            return List.of(bestTurn);
        } else {
            return res;
        }
    }


    /**
     * Gets all possible turns for the swap on board token.
     *
     * @param handPos - Position from where the token was played.
     * @return List of all possible turns
     */
    List<AITurn> getTurnsSwapOnBoard(int handPos) {
        // Getting current field
        Tokens[][] gameField = this.field.getField();
        List<AITurn> allTurns = new ArrayList<>();
        boolean wasPossibleWinningSixOpponent;
        AITurn bestTurn = null;
        // Getting team and opponentTeam
        Team team = this.getTeam() == VERTICAL ? VERTICAL : HORIZONTAL;
        Team opponentTeam = team == VERTICAL ? HORIZONTAL : VERTICAL;
        for (int i = 0; i < gameField.length && bestTurn == null; i++) {
            for (int j = 0; j < gameField[i].length && bestTurn == null; j++) {
                if (gameField[i][j] != EMPTY) {
                    // Getting the unchanged field to operate on
                    Tokens[][] newField = this.field.getField();
                    // Checking if opponent has the chance to have a win of sixes
                    wasPossibleWinningSixOpponent =
                            opponentTeam == VERTICAL ? isFiveTokensOfSameKind(i, opponentTeam, newField) :
                                    isFiveTokensOfSameKind(j, opponentTeam, newField);
                    Tokens firstSwappedToken = newField[i][j];
                    for (int k = 0; k < newField.length && bestTurn == null; k++) {
                        for (int l = 0; l < newField[k].length && bestTurn == null; l++) {
                            if (newField[k][l] != EMPTY && newField[k][l] != firstSwappedToken) {
                                // Getting unchanged field to swap the tokens on
                                Tokens[][] swapField = this.field.getField();
                                Tokens secondSwappedToken = swapField[k][l];
                                // Swapping tokens
                                swapField[i][j] = secondSwappedToken;
                                swapField[k][l] = firstSwappedToken;
                                // Checking the second swapped token lead to a win of sixes for the own team
                                if (team == VERTICAL ? isWinningSix(i, team, swapField) :
                                        isWinningSix(j, team, swapField)) {
                                    bestTurn = new AITurn(
                                            new GamePositions(i, j),
                                            firstSwappedToken,
                                            handPos,
                                            true,
                                            true,
                                            SWAP_ON_BOARD,
                                            null,
                                            null,
                                            false,
                                            new GamePositions(k, l)
                                    );

                                    // Checking the first swapped token lead to a win of sixes for the own team
                                } else if (team == VERTICAL ? isWinningSix(k, team, swapField) :
                                        isWinningSix(l, team, swapField)) {
                                    bestTurn = new AITurn(
                                            new GamePositions(k, l),
                                            firstSwappedToken,
                                            handPos,
                                            true,
                                            true,
                                            SWAP_ON_BOARD,
                                            null,
                                            null,
                                            false,
                                            new GamePositions(i, j)
                                    );
                                    // Checking if the swap prevented a win of sixes for the opponent team
                                } else if (opponentTeam == VERTICAL ?
                                        !isWinningSix(i, opponentTeam, swapField)
                                                && wasPossibleWinningSixOpponent && !isWinningSix(k, opponentTeam, swapField) :
                                        !isWinningSix(l, opponentTeam, swapField)
                                                && wasPossibleWinningSixOpponent &&
                                                !isWinningSix(j, opponentTeam, swapField)) {
                                    bestTurn = new AITurn(
                                            new GamePositions(k, l),
                                            firstSwappedToken,
                                            handPos,
                                            true,
                                            false,
                                            SWAP_ON_BOARD,
                                            null,
                                            null,
                                            true,
                                            new GamePositions(i, j)
                                    );
                                }


                                if (bestTurn == null) {
                                    // Getting current, new and opponent points
                                    int currentTeamPoints = calculateAllPoints(team, gameField);
                                    int newTeamPoints = calculateAllPoints(team, swapField);
                                    int opponentPoints = calculateAllPoints(opponentTeam, swapField);

                                    // Calculating point increase
                                    int pointIncrease = newTeamPoints - currentTeamPoints;
                                    allTurns.add(new AITurn(
                                            new GamePositions(k, l),
                                            firstSwappedToken,
                                            handPos,
                                            true,
                                            false,
                                            SWAP_ON_BOARD,
                                            pointIncrease,
                                            opponentPoints,
                                            false,
                                            new GamePositions(i, j)
                                    ));
                                }
                            }
                        }
                    }
                }
            }
        }
        List<AITurn> res = new ArrayList<>();
        if (allTurns.size() >= 1) {
            if (bestTurn == null) {
                // Getting the turns that lead to maximum point gain
                AITurn max = Collections.max(allTurns, Comparator.comparing(AITurn::getGeneratedPoints));
                for (AITurn turn : allTurns) {
                    if (turn.getGeneratedPoints() == max.getGeneratedPoints()) {
                        res.add(turn);
                    }
                }
            }
        }

        // Returning list of all possible turns or a list that contains a turn that leads to a win of sixes
        // or a turn that prevents a win of sixes for the opposing team
        if (bestTurn != null) {
            return List.of(bestTurn);
        } else {
            return res;
        }

    }


    /**
     * Method calculates the overall points of a team.
     *
     * @param team  - The team which points should be calculated
     * @param field - the field
     * @return The points of the vertical team.
     */
    int calculateAllPoints(Team team, Tokens[][] field) {
        int score = 0;
        // Iterating through every row or column, depending on the team
        for (int i = 0; i < field.length; i++) {
            score += calculatePointsRowOrCol(i, team, field);
        }
        return score;
    }


    /**
     * Method gets all possible turns for the swap with hand token.
     *
     * @return List of all possible turns
     */
    List<AITurn> getTurnsSwapWithHand() {
        // Getting current field
        Tokens[][] gameField = this.field.getField();
        List<AITurn> allTurns = new ArrayList<>();
        AITurn bestTurn = null;
        // Getting team and opponent team
        Team team = this.getTeam() == VERTICAL ? VERTICAL : HORIZONTAL;
        Team opponentTeam = team == VERTICAL ? HORIZONTAL : VERTICAL;
        List<Tokens> playerHand = this.getPlayerHand();
        // Iterating through hand and over field
        for (int handIdx = 0; handIdx < playerHand.size() && bestTurn == null; handIdx++) {
            for (int i = 0; i < gameField.length && bestTurn == null; i++) {
                for (int j = 0; j < gameField[i].length && bestTurn == null; j++) {
                    if (gameField[i][j] != EMPTY) {
                        Tokens[][] newField = this.field.getField();
                        Tokens token = playerHand.get(handIdx);
                        Tokens tokenOnField = newField[i][j];
                        // Token on hand must not be special and not be empty
                        if (!isSpecialToken(token) && token != tokenOnField && token != EMPTY) {
                            // Checking if turn leads to win of sixes
                            if (team == VERTICAL ? tokenLeadsToWinningSix(i, token, newField, team) :
                                    tokenLeadsToWinningSix(j, token, newField, team)) {
                                newField[i][j] = token;
                                bestTurn = new AITurn(
                                        new GamePositions(i, j),
                                        token,
                                        handIdx,
                                        true,
                                        true,
                                        SWAP_WITH_HAND,
                                        null,
                                        null,
                                        false,
                                        null
                                );
                                // Checking if turn prevents win of sixes for the opponent team
                            } else if (opponentTeam == VERTICAL ? isFiveTokensOfSameKind(i, opponentTeam, newField) &&
                                    !tokenLeadsToWinningSix(i, token, newField, opponentTeam) :
                                    isFiveTokensOfSameKind(j, opponentTeam, newField) &&
                                            !tokenLeadsToWinningSix(j, token, newField, opponentTeam)) {
                                newField[i][j] = token;
                                bestTurn = new AITurn(
                                        new GamePositions(i, j),
                                        token,
                                        handIdx,
                                        true,
                                        false,
                                        SWAP_WITH_HAND,
                                        null,
                                        null,
                                        true,
                                        null
                                );
                            }
                            // Placing token if it will not lead to a win of sixes for the opponent team
                            if ((opponentTeam == VERTICAL ? !tokenLeadsToWinningSix(i, token, newField, opponentTeam) :
                                    !tokenLeadsToWinningSix(j, token, newField, opponentTeam)) && bestTurn == null) {
                                newField[i][j] = token;
                            }

                            if (bestTurn == null) {
                                // Getting current, new and opponent points
                                int currentTeamPoints = calculateAllPoints(team, gameField);
                                int newTeamPoints = calculateAllPoints(team, newField);
                                int opponentPoints = calculateAllPoints(opponentTeam, newField);

                                // Calculating point increase
                                int pointIncrease = newTeamPoints - currentTeamPoints;
                                allTurns.add(new AITurn(
                                        new GamePositions(i, j),
                                        token,
                                        handIdx,
                                        true,
                                        false,
                                        SWAP_WITH_HAND,
                                        pointIncrease,
                                        opponentPoints,
                                        false,
                                        null
                                ));
                            }
                        }
                    }
                }
            }
        }
        List<AITurn> res = new ArrayList<>();
        if (allTurns.size() >= 1) {
            if (bestTurn == null) {
                // Getting the turns that lead to the most points
                AITurn max = Collections.max(allTurns, Comparator.comparing(AITurn::getGeneratedPoints));
                for (AITurn turn : allTurns) {
                    if (turn.getGeneratedPoints() == max.getGeneratedPoints()) {
                        res.add(turn);
                    }
                }
            }
        }
        // Returning list of all possible turns or a list that contains a turn that leads to a win of sixes
        // or a turn that prevents a win of sixes for the opposing team
        if (bestTurn != null) {
            return List.of(bestTurn);
        } else {
            return res;
        }
    }


    /**
     * Gets the token with the smallest ordinal from a list of AITurns.
     *
     * @param turns The list of AITurns.
     * @return The token with the smallest ordinal
     */
    Tokens getTokenSmallestOrdinal(List<AITurn> turns) {
        int lowestOrdinal = MAX_ORDINAL;
        for (AITurn aiturn : turns) {
            // Comparing ordinals and setting new lowest if ordinal is lower
            if (aiturn.getToken().ordinal() < lowestOrdinal && aiturn.getToken() != EMPTY) {
                lowestOrdinal = aiturn.getToken().ordinal();
            }
        }
        return Tokens.ordinalToToken(lowestOrdinal);
    }


    /**
     * Method calculates the points of a row.
     *
     * @param index - the row of the field
     * @param field - the field
     * @param team  - the team
     * @return Points for a row.
     */
    private int calculatePointsRowOrCol(int index, Team team, Tokens[][] field) {
        int crossCount = 0;
        int starCount = 0;
        int sunCount = 0;
        int squareCount = 0;
        int triangleCount = 0;
        int pentagonCount = 0;
        for (int i = 0; i < field.length; i++) {
            if (team == VERTICAL ? field[index][i] != EMPTY : field[i][index] != EMPTY) {
                // Checking what kind of token is in the current cell
                switch (team == VERTICAL ? field[index][i] : field[i][index]) {
                    case STAR -> ++starCount;
                    case SUN -> ++sunCount;
                    case TRIANGLE -> ++triangleCount;
                    case SQUARE -> ++squareCount;
                    case PENTAGON -> ++pentagonCount;
                    case CROSS -> ++crossCount;
                }
            }
        }
        int res = 0;
        // Checking if six different tokens have been set in a column
        boolean isSixOfOneKind = sunCount == 1 && squareCount == 1 && triangleCount == 1 && pentagonCount == 1 &&
                starCount == 1 && crossCount == 1;
        if (isSixOfOneKind) {
            res += ONE_OF_EACH;
        } else {
            // Evaluating how many points a token in column grants
            int crossScore = evaluateTokenCount(crossCount);
            int starScore = evaluateTokenCount(starCount);
            int pentagonScore = evaluateTokenCount(pentagonCount);
            int triangleScore = evaluateTokenCount(triangleCount);
            int squareScore = evaluateTokenCount(squareCount);
            int sunScore = evaluateTokenCount(sunCount);

            res = crossScore + starScore + pentagonScore + triangleScore + squareScore + sunScore;
        }

        return res;
    }


    /**
     * Evaluates how many points a token grants depending on how many times
     * it has been set in a column or row.
     *
     * @param count - The amount of how many times a token has been set.
     * @return - The points a token grants depending on its amount in a row or column.
     */
    private int evaluateTokenCount(int count) {
        return switch (count) {
            case 2 -> PAIR;
            case 3 -> TRIPLET;
            case 4 -> QUADRUPLET;
            case 5 -> QUINTUPLET;
            default -> 0;
        };
    }


    /**
     * Method checks if the horizontal team accomplished to have six tokens of a kind in a column.
     *
     * @param index - The index of the row or column
     * @param team  - The team
     * @param field - the field
     * @return True, if a column contains six symbols of a kind, false if not.
     */
    boolean isWinningSix(int index, Team team, Tokens[][] field) {
        int count = 0;
        if (team == HORIZONTAL) {
            for (int i = 0; i < field[index].length; i++) {
                // Checking if all cells of the row contain the same kind of token
                if (field[0][index] == field[i][index] && field[0][index] != EMPTY) {
                    count++;
                }
            }
        } else {
            for (int i = 0; i < field.length; i++) {
                // Checking if all cells of the column contain the same kind of token
                if (field[index][0] == field[index][i] && field[index][0] != EMPTY) {
                    count++;
                }
            }
        }
        return count == SEXTUPLET;
    }


    /**
     * Checks if a token would lead to a win of sixes.
     *
     * @param index - index of column or row, depending on team
     * @param token - The token that may lead to a win of sixes
     * @param field - The field
     * @param team  - The team
     * @return True if token leads to winning six, false if not
     */
    boolean tokenLeadsToWinningSix(int index, Tokens token, Tokens[][] field, Team team) {
        int countOfSameTokens = 0;
        if (team == HORIZONTAL) {
            for (Tokens[] tokens : field) {
                // Checking if token on field matches the handed token
                if (tokens[index] == token) {
                    countOfSameTokens++;
                }
            }
        } else {
            for (int i = 0; i < field.length; i++) {
                // Checking if token on field matches the handed token
                if (field[index][i] == token) {
                    countOfSameTokens++;
                }
            }
        }
        return countOfSameTokens == 5;
    }


    /**
     * Checks if there are five tokens of the same kind in a column orrow.
     *
     * @param index - Index of column/row, depending on team
     * @param team  - The team
     * @param field - The field
     * @return True if there are five tokens of same kind in a column or row.
     */
    boolean isFiveTokensOfSameKind(int index, Team team, Tokens[][] field) {
        int starCount = 0;
        int sunCount = 0;
        int triangleCount = 0;
        int pentagonCount = 0;
        int squareCount = 0;
        int crossCount = 0;
        if (team == HORIZONTAL) {
            for (int i = 0; i < this.field.getField()[index].length; i++) {
                // Checking if all cells of the row contain the same kind of token
                switch (field[i][index]) {
                    case STAR -> starCount++;
                    case PENTAGON -> pentagonCount++;
                    case SQUARE -> squareCount++;
                    case TRIANGLE -> triangleCount++;
                    case SUN -> sunCount++;
                    case CROSS -> crossCount++;
                }
            }
        } else {
            for (int i = 0; i < field.length; i++) {
                // Checking if all cells of the column contain the same kind of token
                switch (field[index][i]) {
                    case STAR -> starCount++;
                    case PENTAGON -> pentagonCount++;
                    case SQUARE -> squareCount++;
                    case TRIANGLE -> triangleCount++;
                    case SUN -> sunCount++;
                    case CROSS -> crossCount++;
                }
            }
        }
        return starCount == 5 || sunCount == 5 || triangleCount == 5 || pentagonCount == 5
                || squareCount == 5 || crossCount == 5;
    }

    /**
     * Gets the most occurrences of a tile on the
     *
     * @param tokensAITurns Set of tok ens of the AITurns
     * @return - The least amount of a set token on the field.
     */
    int getLeastTokenOccurrencesOnBoard(Set<Tokens> tokensAITurns) {
        int starCount = 0;
        int triangleCount = 0;
        int pentagonCount = 0;
        int crossCount = 0;
        int sunCount = 0;
        int squareCount = 0;
        int leastOccurrences = AMOUNT_OF_REGULAR_TOKEN_IN_GAME;
        Tokens[][] gameField = this.field.getField();
        // Iterating through tokens and the field
        for (Tokens token : tokensAITurns) {
            for (Tokens[] tokens : gameField) {
                for (Tokens tokenOnField : tokens) {
                    // Increasing counter depending on token
                    if (token == tokenOnField) {
                        switch (token) {
                            case SUN -> sunCount++;
                            case CROSS -> crossCount++;
                            case TRIANGLE -> triangleCount++;
                            case SQUARE -> squareCount++;
                            case PENTAGON -> pentagonCount++;
                            case STAR -> starCount++;
                        }
                    }
                }
            }
        }
        int[] countArr = new int[tokensAITurns.size()];
        int i = 0;
        // filling countArr with counts
        for (Tokens token : tokensAITurns) {
            switch (token) {
                case SUN -> countArr[i] = sunCount;
                case CROSS -> countArr[i] = crossCount;
                case TRIANGLE -> countArr[i] = triangleCount;
                case SQUARE -> countArr[i] = squareCount;
                case PENTAGON -> countArr[i] = pentagonCount;
                case STAR -> countArr[i] = starCount;
            }
            i++;
        }
        // Iterating over array to get the least occurrence on board
        for (int tokenCount : countArr) {
            if (tokenCount < leastOccurrences) {
                leastOccurrences = tokenCount;
            }
        }
        return leastOccurrences;
    }


    /**
     * Gets the amount of times a token has been set on the field.
     *
     * @param gameField - The field
     * @param token     - The token
     * @return - Amount of times the token has been set on the field.
     */
    int getTokenOccurrenceOfSingleTokenOnBoard(Tokens[][] gameField, Tokens token) {
        int res = 0;
        for (Tokens[] tokens : gameField) {
            for (Tokens tokenOnField : tokens) {
                // Increasing counter if token matches token on field
                if (tokenOnField == token) {
                    res++;
                }
            }
        }
        return res;
    }


    /**
     * Gets the amount of times a token is on the hand of the player.
     *
     * @param playerhand - Hand of the player
     * @param token      - The token
     * @return Amount of times the token is on the hand of the player.
     */
    int getOccurrenceOfSingleTokenOnHand(List<Tokens> playerhand, Tokens token) {
        int res = 0;
        for (Tokens tokens : playerhand) {
            // Increasing counter if token matches token on hand
            if (tokens == token) {
                res++;
            }
        }
        return res;
    }


    /**
     * Gets the occurrences of the token on the hand of the player
     *
     * @param relevantTokens - The relevant tokens for the AITurn.
     * @param playerhand     - The hand of the player
     * @return The occurrences of the relevant token on the hand of the player
     */
    int[] getTokenOccurrencesOnHand(List<Tokens> relevantTokens, List<Tokens> playerhand) {
        int[] res = new int[AMOUNT_OF_STANDARD_TOKENS];
        // Iterating over relevant tokens and hand of the player
        for (Tokens relevantToken : relevantTokens) {
            for (Tokens tokens : playerhand) {
                if (relevantToken == tokens) {
                    // Increasing count in array if token from relevantTokens matches token in hand of the player
                    switch (relevantToken) {
                        case SUN -> res[SUN.ordinal() - 1]++;
                        case CROSS -> res[CROSS.ordinal() - 1]++;
                        case TRIANGLE -> res[TRIANGLE.ordinal() - 1]++;
                        case SQUARE -> res[SQUARE.ordinal() - 1]++;
                        case PENTAGON -> res[PENTAGON.ordinal() - 1]++;
                        case STAR -> res[STAR.ordinal() - 1]++;
                    }
                }
            }
        }
        return res;
    }
}
