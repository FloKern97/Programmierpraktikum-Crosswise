package logic;

import static logic.Team.*;
import static logic.Tokens.*;

/**
 * Class contains all field-related methods needed for a game of crosswise.
 *
 * @author Florian Kern/cgt104661
 */

public class GameField {

    /**
     * The field on which the players play the game.
     */
    private final Tokens[][] field;

    /**
     * Constructor. Initialises the field of the game,
     *
     * @param field - The field
     */
    public GameField(Tokens[][] field) {
        this.field = field;
    }


    /**
     * Constructor for testing and loading a game.
     *
     * @param field - The field, values are represented with their ordinal values.
     */
    public GameField(int[][] field) {
        this.field = new Tokens[field.length][field.length];
        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field[i].length; j++) {
                this.field[i][j] = Tokens.ordinalToToken(field[i][j]);
            }
        }
    }

    /**
     * Getter for the field of the game.
     *
     * @return - A copy of the field.
     */
    public Tokens[][] getField() {
        Tokens[][] res = new Tokens[this.field.length][this.field.length];
        for (int i = 0; i < this.field.length; i++) {
            System.arraycopy(this.field[i], 0, res[i], 0, this.field[i].length);
        }
        return res;
    }

    /**
     * Setter for setting a token on the field.
     *
     * @param token - The token to be placed.
     * @param pos   - The position on the field where the token should be placed
     */
    public void setToken(Tokens token, GamePositions pos) {
        this.field[pos.getX()][pos.getY()] = token;
    }


    /**
     * Getter for the points of a row.
     *
     * @param index - The index of the row.
     * @return The points for the row
     */
    public int getPointsAtRowOrCol(int index, Team team) {
        return calculatePointsRowOrCol(index, team);
    }

    /**
     * Getter for the current score of a team.
     *
     * @param team - The team
     * @return The current score
     */
    public int getScore(Team team) {
        return team == Team.VERTICAL ? calculateAllPoints(VERTICAL) : calculateAllPoints(HORIZONTAL);
    }

    /**
     * Method checks if there are empty cells left on the field.
     *
     * @return True, if there are empty field on the field, false if not.
     */
    boolean emptyCellsLeft() {
        boolean isEmpty = false;
        for (int i = 0; i < this.field.length && !isEmpty; i++) {
            for (int j = 0; j < this.field[i].length && !isEmpty; j++) {
                isEmpty = this.field[i][j] == EMPTY;
            }
        }
        return isEmpty;
    }

    /**
     * Checks if the field is empty.
     *
     * @return True, if field is empty, false if not
     */
    boolean fieldIsEmpty() {
        boolean isEmpty = true;
        for (int i = 0; i < this.field.length && isEmpty; i++) {
            for (int j = 0; j < this.field[i].length && isEmpty; j++) {
                isEmpty = this.field[i][j] == EMPTY;
            }
        }
        return isEmpty;
    }

    /**
     * Counts the tokens on the field.
     *
     * @return - Amount of tokens on the field.
     */
    int countTokensOnField() {
        int tokenCount = 0;
        for (Tokens[] tokens : this.field) {
            for (Tokens token : tokens) {
                if (token != EMPTY) {
                    tokenCount++;
                }
            }
        }
        return tokenCount;
    }


    /**
     * Method checks if the horizontal team accomplished to have six tokens of a kind in a column.
     *
     * @param index - The index of the row or column
     * @param team  - The team
     * @return True, if a column contains six symbols of a kind, false if not.
     */
    boolean isWinningSix(int index, Team team) {
        int count = 0;
        if (team == HORIZONTAL) {
            for (int i = 0; i < this.field[index].length; i++) {
                // Checking if all cells of the row contain the same kind of token
                if (this.field[0][index] == this.field[i][index] && this.field[0][index] != EMPTY) {
                    count++;
                }
            }
        } else {
            for (int i = 0; i < this.field.length; i++) {
                // Checking if all cells of the column contain the same kind of token
                if (this.field[index][0] == this.field[index][i] && this.field[index][0] != EMPTY) {
                    count++;
                }
            }
        }
        return count == 6;
    }


    /**
     * Method calculates the overall points of a team.
     *
     * @param team - The team which points should be calculated
     * @return The points of the vertical team.
     */
    int calculateAllPoints(Team team) {
        int score = 0;
        // Iterating through every row or column, depending on the team
        for (int i = 0; i < this.field.length; i++) {
            score += calculatePointsRowOrCol(i, team);
        }
        return score;
    }


    /**
     * Method calculates the points of a row or column.
     *
     * @param index - the row or column of the field
     * @return Points for a row or column.
     */
    private int calculatePointsRowOrCol(int index, Team team) {
        int crossCount = 0;
        int starCount = 0;
        int sunCount = 0;
        int squareCount = 0;
        int triangleCount = 0;
        int pentagonCount = 0;
        for (int i = 0; i < this.field.length; i++) {
            if (team == VERTICAL ? this.field[index][i] != EMPTY : this.field[i][index] != EMPTY) {
                // Checking what kind of token is in the current cell
                switch (team == VERTICAL ? this.field[index][i] : this.field[i][index]) {
                    case STAR -> ++starCount;
                    case SUN -> ++sunCount;
                    case TRIANGLE -> ++triangleCount;
                    case SQUARE -> ++squareCount;
                    case PENTAGON -> ++pentagonCount;
                    case CROSS -> ++crossCount;
                }
            }
        }
        // Evaluating how many points a token in column grants
        int res = 0;
        // Checking if six different tokens have been set in a column
        boolean isOneOfEach = sunCount == 1 && squareCount == 1 && triangleCount == 1 && pentagonCount == 1 &&
                starCount == 1 && crossCount == 1;
        if (isOneOfEach) {
            res += 6;
        } else {
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
     * it has been set in a column or row .
     *
     * @param count - The amount of how many times a token has been set.
     * @return - The points a token grants depending on its amount in a row or column.
     */
    private int evaluateTokenCount(int count) {
        return switch (count) {
            case 2 -> 1;
            case 3 -> 3;
            case 4 -> 5;
            case 5 -> 7;
            default -> 0;
        };
    }
}
