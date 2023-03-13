package logic;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;
import static logic.Tokens.*;
import static logic.Team.*;

/**
 * Test cases for the logic of the game Crosswise.
 *
 * @author Florian Kern/cgt104661
 */
public class LogicTest {


    // ------------------- COLUMN POINT EVALUATION TESTS ----------------------

    @Test
    public void testPointEvaluationColumnSixOfOneKind() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {CROSS, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(6, game.getPointsAtRowOrCol(0, VERTICAL));
    }

    @Test
    public void testPointEvaluationColumnPair() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {CROSS, CROSS, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(1, game.getPointsAtRowOrCol(0, VERTICAL));
    }

    @Test
    public void testPointEvaluationColumnTriplet() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {CROSS, CROSS, CROSS, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(3, game.getPointsAtRowOrCol(1, VERTICAL));
    }

    @Test
    public void testPointEvaluationColumnQuadruplet() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {CROSS, CROSS, CROSS, CROSS, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(5, game.getPointsAtRowOrCol(2, VERTICAL));
    }

    @Test
    public void testPointEvaluationColumnQuintuplet() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {CROSS, CROSS, CROSS, CROSS, CROSS, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(7, game.getPointsAtRowOrCol(3, VERTICAL));
    }

    @Test
    public void testPointEvaluationColumnNoPoints() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(0, game.getPointsAtRowOrCol(0, VERTICAL));

    }

    @Test
    public void testPointEvaluationColumnPairAndTriplet() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {CROSS, CROSS, SUN, SUN, SUN, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(4, game.getPointsAtRowOrCol(0, VERTICAL));
    }

    // -------------------- COLUMN POINT EVALUATION TESTS ----------------------------------------

    @Test
    public void testPointEvaluationRowPair() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {CROSS, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {CROSS, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(1, game.getPointsAtRowOrCol(0, HORIZONTAL));
    }


    @Test
    public void testPointEvaluationRowTriplet() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, CROSS, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, CROSS, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, CROSS, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(3, game.getPointsAtRowOrCol(1, HORIZONTAL));
    }

    @Test
    public void testPointEvaluationRowQuadruplet() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, EMPTY, CROSS, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, CROSS, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, CROSS, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, CROSS, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(5, game.getPointsAtRowOrCol(2, HORIZONTAL));
    }

    @Test
    public void testPointEvaluationRowQuintuplet() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, EMPTY, EMPTY, CROSS, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, CROSS, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, CROSS, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, CROSS, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, CROSS, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(7, game.getPointsAtRowOrCol(3, HORIZONTAL));
    }

    @Test
    public void testPointEvaluationRowNoPoints() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }), new FakeGUI());
        assertEquals(0, game.getPointsAtRowOrCol(0, HORIZONTAL));
    }

    @Test
    public void testPointEvaluationRowTwoPairs() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {EMPTY, EMPTY, EMPTY, EMPTY, CROSS, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, CROSS, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, SUN, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, SUN, EMPTY},
                }), new FakeGUI());
        assertEquals(2, game.getPointsAtRowOrCol(4, HORIZONTAL));
    }

    // ----------------- FIELD EVALUATION  ---------------------

    @Test
    public void testEvaluateFieldHorizontal() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {SUN, CROSS, CROSS, TRIANGLE, PENTAGON, SQUARE},
                        {SUN, SQUARE, SUN, STAR, STAR, STAR},
                        {PENTAGON, PENTAGON, PENTAGON, SUN, SUN, SUN},
                        {SUN, TRIANGLE, PENTAGON, SQUARE, STAR, CROSS},
                        {SQUARE, TRIANGLE, SQUARE, TRIANGLE, SQUARE, SQUARE},
                        {PENTAGON, STAR, STAR, STAR, SUN, SQUARE},
                }), new FakeGUI());
        assertEquals(26, game.getTeamScore(VERTICAL));
    }


    @Test
    public void testEvaluateFieldVertical() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {SUN, CROSS, CROSS, TRIANGLE, PENTAGON, SQUARE},
                        {SUN, SQUARE, SUN, STAR, STAR, STAR},
                        {PENTAGON, PENTAGON, PENTAGON, SUN, SUN, SUN},
                        {SUN, TRIANGLE, PENTAGON, SQUARE, STAR, CROSS},
                        {SQUARE, TRIANGLE, SQUARE, TRIANGLE, SQUARE, SQUARE},
                        {PENTAGON, STAR, STAR, STAR, SUN, SQUARE},
                }), new FakeGUI());
        assertEquals(13, game.getTeamScore(HORIZONTAL));
    }


    // ----------------- EVALUATION WINNING TEAM  ---------------------

    @Test
    public void testEvaluateWinnerHorizontal() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {SUN, CROSS, CROSS, TRIANGLE, PENTAGON, SQUARE},
                        {SUN, SQUARE, SUN, STAR, STAR, STAR},
                        {PENTAGON, PENTAGON, PENTAGON, SUN, SUN, SUN},
                        {SUN, TRIANGLE, PENTAGON, SQUARE, STAR, CROSS},
                        {SQUARE, TRIANGLE, SQUARE, TRIANGLE, SQUARE, SQUARE},
                        {PENTAGON, STAR, STAR, STAR, SUN, SQUARE},
                }), new FakeGUI());
        assertEquals(VERTICAL, game.getWinningTeam(false));
    }

    @Test
    public void testEvaluateWinnerVertical() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {PENTAGON, CROSS, SUN, SUN, TRIANGLE, SQUARE},
                        {PENTAGON, CROSS, SUN, SUN, TRIANGLE, SQUARE},
                        {PENTAGON, CROSS, SUN, SUN, TRIANGLE, SQUARE},
                        {PENTAGON, CROSS, SUN, SUN, TRIANGLE, SQUARE},
                        {PENTAGON, CROSS, SUN, SUN, TRIANGLE, SQUARE},
                        {TRIANGLE, STAR, STAR, STAR, SUN, PENTAGON},
                }), new FakeGUI());
        assertEquals(HORIZONTAL, game.getWinningTeam(false));
    }

    @Test
    public void testEvaluateWinnerNoWinner() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {STAR, STAR, STAR, STAR, STAR, SQUARE},
                        {STAR, STAR, STAR, STAR, STAR, SQUARE},
                        {STAR, STAR, STAR, STAR, STAR, SQUARE},
                        {STAR, STAR, STAR, STAR, STAR, SQUARE},
                        {STAR, STAR, STAR, STAR, STAR, SQUARE},
                        {SQUARE, SQUARE, SQUARE, SQUARE, SQUARE, PENTAGON},
                }), new FakeGUI());
        assertEquals(NONE, game.getWinningTeam(false));
    }

    @Test
    public void testEvaluateWinnerUnfinishedGame() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {STAR, SUN, CROSS, EMPTY, PENTAGON, SQUARE},
                        {EMPTY, EMPTY, SQUARE, SUN, STAR, EMPTY},
                        {STAR, STAR, PENTAGON, EMPTY, STAR, SQUARE},
                        {EMPTY, EMPTY, EMPTY, CROSS, STAR, EMPTY},
                        {STAR, SUN, STAR, EMPTY, EMPTY, EMPTY},
                        {SQUARE, SQUARE, EMPTY, SQUARE, SQUARE, EMPTY},
                }), new FakeGUI());
        assertNull(game.getWinningTeam(false));
    }

    @Test
    public void testEvaluateWinnerSextupletHorizontal() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {STAR, SUN, CROSS, EMPTY, PENTAGON, SQUARE},
                        {STAR, EMPTY, SQUARE, SUN, STAR, EMPTY},
                        {STAR, STAR, PENTAGON, EMPTY, STAR, SQUARE},
                        {STAR, EMPTY, EMPTY, CROSS, STAR, EMPTY},
                        {STAR, SUN, STAR, EMPTY, EMPTY, EMPTY},
                        {STAR, SQUARE, EMPTY, SQUARE, SQUARE, EMPTY},
                }), new FakeGUI());
        assertTrue(game.isWinningSix(HORIZONTAL, 0));
        assertEquals(HORIZONTAL, game.getWinningTeam(false));
    }

    @Test
    public void testEvaluateWinnerSextupletVertical() {
        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian"),
                new Player("Benedikt")},
                new GameField(new Tokens[][]{
                        {SUN, SUN, SUN, SUN, SUN, SUN},
                        {STAR, EMPTY, SQUARE, SUN, STAR, EMPTY},
                        {SQUARE, STAR, PENTAGON, EMPTY, STAR, SQUARE},
                        {TRIANGLE, EMPTY, EMPTY, CROSS, STAR, EMPTY},
                        {PENTAGON, SUN, STAR, EMPTY, EMPTY, EMPTY},
                        {SQUARE, SQUARE, EMPTY, SQUARE, SQUARE, EMPTY},
                }), new FakeGUI());
        assertTrue(game.isWinningSix(VERTICAL, 0));
        assertEquals(VERTICAL, game.getWinningTeam(false));
    }


    // ----------------- AI TESTS  ---------------------
    @Test
    public void testAITurn_remover() {
        int[][] field = new int[][]{
                {0, 0, 0, 0, 0, 0},
                {0, 0, 5, 0, 0, 0},
                {0, 0, 0, 5, 0, 0},
                {0, 5, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(2, ai.getTurnsRemoveStone(0).size());

    }


    @Test
    public void testAITurn_removerStopWinningSixOpponent() {
        int[][] field = new int[][]{
                {0, 0, 0, 0, 0, 0},
                {5, 5, 5, 5, 5, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsRemoveStone(0).size());

    }


    @Test
    public void testAITurn_moveStone() {
        int[][] field = new int[][]{
                {1, 2, 3, 4, 5, 1},
                {0, 1, 3, 1, 2, 2},
                {0, 2, 1, 2, 4, 3},
                {0, 0, 2, 3, 2, 4},
                {0, 0, 3, 4, 3, 5},
                {0, 0, 2, 5, 1, 1}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 8, 4, 5});
        ai.setField(gameField);
        assertEquals(3, ai.getTurnsMoveStone(1).size());
    }


    @Test
    public void testAiTurn_getBestTurnsMoveStone() {
        int[][] field = new int[][]{
                {0, 0, 0, 0, 5, 1},
                {0, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 4, 0},
                {0, 0, 0, 3, 2, 4},
                {0, 0, 0, 4, 3, 0},
                {0, 0, 0, 5, 1, 1}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 8, 4, 5});
        ai.setField(gameField);
        assertEquals(3, ai.getTurnsMoveStone(1).size());
    }


    @Test
    public void testAITurn_moveStonePreventOpponentWin() {
        int[][] field = new int[][]{
                {1, 1, 1, 1, 1, 0},
                {4, 0, 1, 0, 0, 0},
                {3, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{0, 8, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsMoveStone(1).size());
    }


    @Test
    public void testAITurn_moveStoneMoveForWin() {
        int[][] field = new int[][]{
                {1, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 8, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsMoveStone(1).size());
    }


    @Test
    public void testAITurn_swapOnBoardStoneTwoTokens() {
        int[][] field = new int[][]{
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 9, 4, 5});
        ai.setField(gameField);
        assertEquals(2, ai.getTurnsSwapOnBoard(1).size());
    }


    @Test
    public void testAITurn_swapOnBoardStoneSwapForWinVertical() {
        int[][] field = new int[][]{
                {1, 1, 1, 2, 1, 1},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 9, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsSwapOnBoard(1).size());
    }


    @Test
    public void testAITurn_swapOnBoardStoneSwapForWinHorizontal() {
        int[][] field = new int[][]{
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 9, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsSwapOnBoard(1).size());
    }


    @Test
    public void testAITurn_swapOnBoardStoneMaxPointSwapTurnsHorizontal() {
        int[][] field = new int[][]{
                {0, 2, 0, 2, 0, 0},
                {0, 2, 0, 2, 0, 0},
                {0, 2, 3, 2, 0, 0},
                {0, 2, 0, 1, 0, 0},
                {0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 9, 4, 5});
        ai.setField(gameField);
        assertEquals(14, ai.getTurnsSwapOnBoard(1).size());
    }


    @Test
    public void testAITurn_swapOnBoardStoneMorePossibleTurns() {
        int[][] field = new int[][]{
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0, 0},
                {0, 0, 2, 0, 0, 0},
                {0, 0, 0, 4, 0, 0},
                {0, 0, 0, 0, 5, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 9, 4, 5});
        ai.setField(gameField);
        assertEquals(20, ai.getTurnsSwapOnBoard(1).size());
    }

    @Test
    public void testAITurn_swapOnBoardStonePreventSixOpponentIsHorizontal() {
        int[][] field = new int[][]{
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 2, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 9, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsSwapOnBoard(1).size());
    }

    @Test
    public void testAITurn_swapOnBoardStonePreventSixOpponentIsVertical() {
        int[][] field = new int[][]{
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0},
                {0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 9, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsSwapOnBoard(1).size());
    }

    @Test
    public void testAITurn_swapWithHandStone() {
        int[][] field = new int[][]{
                {2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(3, ai.getTurnsSwapWithHand().size());
    }


    @Test
    public void testAITurn_swapWithHandSwapForWin() {
        int[][] field = new int[][]{
                {2, 2, 2, 2, 2, 1},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0, 0},
                {0, 1, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 2, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsSwapWithHand().size());
    }

    @Test
    public void testAITurn_swapWithHandPreventOpponentWinningSix() {
        int[][] field = new int[][]{
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsSwapWithHand().size());
    }


    @Test
    public void testAITurn_swapWithHandPrioritizeWinPrevention() {
        int[][] field = new int[][]{
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 3}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsSwapWithHand().size());
    }


    @Test
    public void testAITurn_StandardTile_placeWinningTile() {
        int[][] field = new int[][]{
                {1, 1, 0, 0, 0, 0},
                {1, 2, 2, 2, 2, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsStandardToken(1).size());
    }


    @Test
    public void testAITurn_StandardTile_stopWinOpposingTeam() {
        int[][] field = new int[][]{
                {0, 1, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(1, ai.getTurnsStandardToken(1).size());
    }

    @Test
    public void testAITurn_StandardTile_cantStopWinOpposingTeamWithToken() {
        int[][] field = new int[][]{
                {0, 1, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 2, 4, 5});
        ai.setField(gameField);
        assertEquals(24, ai.getTurnsStandardToken(1).size());
    }

    @Test
    public void testAITurn_StandardTile_allCellsFree() {
        int[][] field = new int[][]{
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(36, ai.getTurnsStandardToken(1).size());
    }

    @Test
    public void testAITurn_standardTileGetMaxPointTurns() {
        int[][] field = new int[][]{
                {1, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 1, 4, 5});
        ai.setField(gameField);
        assertEquals(2, ai.getTurnsStandardToken(1).size());
    }


    @Test
    public void testAITurn_suggestTurnRegularTokenWinningSix() {
        int[][] field = new int[][]{
                {1, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        AITurn expected = new AITurn(new GamePositions(5, 0), SUN, 0, false,
                true, null, null, null,
                false, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{1, 3, 4, 2});
        ai.setField(gameField);
        assertEquals(expected.getPos(), ai.getBestTurn().getPos());
        assertEquals(expected.getToken(), ai.getBestTurn().getToken());
        assertEquals(expected.getHandPos(), ai.getBestTurn().getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnRegularTokenPreventWinningSix() {
        int[][] field = new int[][]{
                {1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        AITurn expected = new AITurn(new GamePositions(0, 5), CROSS, 3, false,
                false, null, null, null,
                true, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{1, 3, 4, 2});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }

    @Test
    public void testAITurn_suggestTurnGetChooseRightPosition() {
        int[][] field = new int[][]{
                {1, 2, 2, 0, 0, 0},
                {1, 2, 2, 0, 0, 0},
                {1, 2, 0, 0, 0, 0},
                {1, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };
        AITurn expected = new AITurn(new GamePositions(4, 0), SUN, 0, false,
                false, null, 2, 0,
                false, null);

        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{1, 3, 4, 2});
        ai.setField(gameField);
        assertEquals(expected.getPos(), ai.getBestTurn().getPos());
        assertEquals(expected.getToken(), ai.getBestTurn().getToken());
        assertEquals(expected.getHandPos(), ai.getBestTurn().getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnMoveTokenWinningSix() {
        int[][] field = new int[][]{
                {1, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };
        AITurn expected = new AITurn(new GamePositions(5, 0), SUN, 0, true,
                true, MOVE_STONE, null, null,
                false, new GamePositions(1, 2));
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{1, 3, 4, 2});
        ai.setField(gameField);
        assertEquals(expected.getPos(), ai.getBestTurn().getPos());
        assertEquals(expected.getToken(), ai.getBestTurn().getToken());
        assertEquals(expected.getHandPos(), ai.getBestTurn().getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnMoveTokenPreventWinningSix() {
        int[][] field = new int[][]{
                {1, 0, 1, 0, 0, 0},
                {1, 0, 0, 2, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };
        AITurn expected = new AITurn(new GamePositions(0, 1), SUN, 0, true,
                false, MOVE_STONE, null, null,
                true, new GamePositions(0, 0));
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{8, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnMoveTokenMoveForPoints() {
        int[][] field = new int[][]{
                {0, 0, 1},
                {1, 0, 0},
                {1, 0, 0}
        };
        AITurn expected = new AITurn(new GamePositions(0, 0), SUN, 0, true,
                false, MOVE_STONE, 1, 0,
                false, new GamePositions(1, 0));
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{8, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnRemoveTokenRemoveToken() {
        int[][] field = new int[][]{
                {0, 0, 1},
                {1, 0, 0},
                {1, 0, 0}
        };
        AITurn expected = new AITurn(new GamePositions(1, 0), SUN, 0, false,
                false, REMOVE_STONE, 0, 0,
                false, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnRemovePreventWinningSix() {
        int[][] field = new int[][]{
                {0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(1, 0), SUN, 0, false,
                false, REMOVE_STONE, 0, 5,
                true, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }

    @Test
    public void testAITurn_suggestTurnRemove_bestRemoveForTurn() {
        int[][] field = new int[][]{
                {0, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0},
                {1, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(3, 0), SUN, 0, false,
                false, REMOVE_STONE, 0, 4,
                false, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }

    @Test
    public void testAITurn_suggestTurnRemove_bestRemoveForTurnMultiplePossibilities() {
        int[][] field = new int[][]{
                {0, 0, 1, 0, 2, 0},
                {0, 2, 1, 0, 2, 2},
                {1, 0, 1, 0, 2, 0},
                {1, 0, 0, 0, 2, 0},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(3, 0), SUN, 0, false,
                false, REMOVE_STONE, 0, 4,
                false, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{7, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }

    @Test
    public void testAITurn_suggestTurnSwapOnBoardSwapForWin() {
        int[][] field = new int[][]{
                {1, 1, 1, 1, 0, 2},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 3, 2},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(3, 4), CROSS, 0, true,
                true, SWAP_ON_BOARD, null, null,
                false, new GamePositions(0, 5));
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{9, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnSwapOnBoardPreventWinningSix() {
        int[][] field = new int[][]{
                {1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(3, 2), SUN, 0, true,
                false, SWAP_ON_BOARD, null, null,
                true, new GamePositions(0, 0));
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{9, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnSwapOnBoardSwapForPoints() {
        int[][] field = new int[][]{
                {3, 0, 3, 1, 0, 0},
                {0, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 2, 5, 0, 0},
                {0, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(0, 0), CROSS, 0, true,
                false, SWAP_ON_BOARD, 1, 0,
                false, new GamePositions(3, 2));
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{9, 0, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
        assertEquals(expected.getGeneratedPoints(), bestTurn.getGeneratedPoints());
        assertEquals(expected.getOpponentPoints(), bestTurn.getOpponentPoints());
    }


    @Test
    public void testAITurn_suggestTurnSwapWithHandSwapWinningSix() {
        int[][] field = new int[][]{
                {3, 0, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 2},
                {3, 0, 0, 0, 0, 0},
                {3, 0, 0, 5, 0, 0},
                {3, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(5, 0), TRIANGLE, 1, true,
                true, SWAP_WITH_HAND, null, null,
                false, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{10, 3, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
    }


    @Test
    public void testAITurn_suggestTurnSwapWithHandPreventSix() {
        int[][] field = new int[][]{
                {3, 0, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 2},
                {3, 0, 0, 0, 0, 0},
                {3, 0, 0, 5, 0, 0},
                {3, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(0, 0), CROSS, 0, true,
                false, SWAP_WITH_HAND, null, null,
                true, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", VERTICAL, true, true, new int[]{2, 10, 0, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
        assertEquals(expected.isPreventsSixOpponent(), bestTurn.isPreventsSixOpponent());
    }


    @Test
    public void testAITurn_suggestTurnSwapWithHandSwapForPoints() {
        int[][] field = new int[][]{
                {2, 0, 0, 0, 0, 0},
                {3, 0, 1, 0, 0, 2},
                {3, 0, 1, 0, 0, 0},
                {3, 0, 0, 5, 0, 0},
                {3, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(0, 0), TRIANGLE, 1, true,
                false, SWAP_WITH_HAND, 2, 0,
                false, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{10, 3, 2, 0});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getOpponentPoints(), bestTurn.getOpponentPoints());
        assertEquals(expected.getGeneratedPoints(), bestTurn.getGeneratedPoints());
        assertEquals(expected.getUsedSpecialToken(), bestTurn.getUsedSpecialToken());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
        assertEquals(expected.isPreventsSixOpponent(), bestTurn.isPreventsSixOpponent());
    }

    @Test
    public void testAITurn_getBestTurn() {
        int[][] field = new int[][]{
                {2, 0, 0, 0, 0, 0},
                {0, 0, 4, 4, 0, 0},
                {0, 2, 4, 0, 0, 0},
                {0, 0, 3, 3, 0, 0},
                {3, 0, 0, 5, 0, 0},
                {0, 0, 1, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(0, 2), SQUARE, 3, true,
                false, MOVE_STONE, 2, 1,
                false, new GamePositions(1, 3));
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{7, 3, 5, 8});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getOpponentPoints(), bestTurn.getOpponentPoints());
        assertEquals(expected.getGeneratedPoints(), bestTurn.getGeneratedPoints());
        assertEquals(expected.getUsedSpecialToken(), bestTurn.getUsedSpecialToken());
        assertEquals(expected.getDraggedFrom(), bestTurn.getDraggedFrom());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
        assertEquals(expected.isPreventsSixOpponent(), bestTurn.isPreventsSixOpponent());
    }


    @Test
    public void testAITurn_suggestTurnGameStarted() {
        int[][] field = new int[][]{
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
        };
        AITurn expected = new AITurn(new GamePositions(0, 0), TRIANGLE, 0, false,
                false, null, 0, 0,
                false, null);
        GameField gameField = new GameField(field);
        GameAI ai = new GameAI("ai", HORIZONTAL, true, true, new int[]{3, 7, 9, 10});
        ai.setField(gameField);
        AITurn bestTurn = ai.getBestTurn();
        assertEquals(expected.getPos(), bestTurn.getPos());
        assertEquals(expected.getToken(), bestTurn.getToken());
        assertEquals(expected.getHandPos(), bestTurn.getHandPos());
        assertEquals(expected.getGeneratedPoints(), bestTurn.getGeneratedPoints());
        assertEquals(expected.getOpponentPoints(), bestTurn.getOpponentPoints());
    }


    // ----------------- FIELD TESTS  ---------------------

    @Test
    public void testSetToken() {
        Tokens[][] expected = new Tokens[][]{
                {SUN, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        field.setToken(SUN, new GamePositions(0, 0));
        assertArrayEquals(expected, field.getField());

    }

    @Test
    public void testFieldIsEmpty() {
        GameField field = new GameField(new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        assertTrue(field.fieldIsEmpty());
    }

    @Test
    public void testFieldIsNotEmpty() {
        GameField field = new GameField(new int[][]{
                {0, 1, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        assertFalse(field.fieldIsEmpty());
    }


    @Test
    public void testEmptyCellsLeft() {
        GameField field = new GameField(new int[][]{
                {0, 0, 2},
                {0, 1, 0},
                {0, 0, 1}
        });
        assertTrue(field.emptyCellsLeft());
    }


    @Test
    public void testNoEmptyCellsLeft() {
        GameField field = new GameField(new int[][]{
                {3, 1, 2},
                {4, 1, 6},
                {2, 5, 1}
        });
        assertFalse(field.emptyCellsLeft());
    }

    @Test
    public void testCountTokensOnField() {
        int expected = 3;
        GameField field = new GameField(new int[][]{
                {0, 0, 2},
                {0, 1, 0},
                {0, 0, 1}
        });
        assertEquals(expected, field.countTokensOnField());
    }

    @Test
    public void testPlayerTurnRegularToken() {

        Tokens[][] expected = new Tokens[][]{
                {CROSS, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                {SUN, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {CROSS, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian", VERTICAL, true, false),
                new Player("Benedikt", HORIZONTAL, true, false)},
                field, new FakeGUI());
        game.playerTurn(new GamePositions(1, 0), SUN, new GamePositions(2, 0),
                false, null);
        assertArrayEquals(expected, field.getField());
    }

    // ----------------- PLAYERTURN / PLAYERTURN_AI TESTS  ---------------------

    @Test
    public void testPlayerTurnRemoveToken() {

        Tokens[][] expected = new Tokens[][]{
                {CROSS, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {CROSS, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                {SUN, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian", VERTICAL, true, false),
                new Player("Benedikt", HORIZONTAL, true, false)},
                field, new FakeGUI());
        game.handleSpecialToken(REMOVE_STONE, new GamePositions(1, 0), new GamePositions(2, 0));
        assertArrayEquals(expected, field.getField());
    }

    @Test
    public void testPlayerTurnMoveToken() {

        Tokens[][] expected = new Tokens[][]{
                {EMPTY, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                {CROSS, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {CROSS, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian", VERTICAL, true, false),
                new Player("Benedikt", HORIZONTAL, true, false)},
                field, new FakeGUI());
        game.playerTurn(new GamePositions(1, 0), CROSS, new GamePositions(0, 0), true, MOVE_STONE);
        assertArrayEquals(expected, field.getField());
    }

    @Test
    public void testPlayerTurnSwapOnBoardToken() {

        Tokens[][] expected = new Tokens[][]{
                {SUN, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {CROSS, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian", VERTICAL, true, false),
                new Player("Benedikt", HORIZONTAL, true, false)},
                field, new FakeGUI());
        game.playerTurn(new GamePositions(0, 1), CROSS, new GamePositions(0, 0), true, SWAP_ON_BOARD);
        assertArrayEquals(expected, field.getField());
    }

    @Test
    public void testPlayerTurnSwapWithHandToken() {

        Tokens[][] expected = new Tokens[][]{
                {SUN, CROSS, TRIANGLE, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {SUN, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        int[] hand = new int[]{1, 3, 4};
        int[] hand2 = new int[]{1, 3, 4, 5};

        GameCrosswise game = new GameCrosswise(new Player[]{
                new Player("Florian", VERTICAL, true, false, hand),
                new Player("Benedikt", HORIZONTAL, true, false, hand2)},
                field, new FakeGUI());
        game.playerTurn(new GamePositions(0, 2), TRIANGLE, new GamePositions(1, 0), true, SWAP_WITH_HAND);
        assertArrayEquals(expected, field.getField());
    }

    @Test
    public void testCauseTurn_IncludesPlayerTurnAI_RegularToken() {

        Tokens[][] expected = new Tokens[][]{
                {SUN, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {SUN, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {SUN, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        int[] hand = new int[]{1, 0, 0, 0};

        GameCrosswise game = new GameCrosswise(new Player[]{
                new GameAI("Flo", VERTICAL, true, true, hand)},
                field, new FakeGUI());
        game.causeTurn();
        assertArrayEquals(expected, field.getField());
    }


    @Test
    public void testCauseTurn_IncludesPlayerTurnAI_RemoveToken() {

        Tokens[][] expected = new Tokens[][]{
                {EMPTY, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {SUN, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        int[] hand = new int[]{7, 0, 0, 0};

        GameCrosswise game = new GameCrosswise(new Player[]{
                new GameAI("Flo", VERTICAL, true, true, hand)},
                field, new FakeGUI());
        game.causeTurn();
        assertArrayEquals(expected, field.getField());
    }

    @Test
    public void testCauseTurn_IncludesPlayerTurnAI_SwapOnBoardToken() {

        Tokens[][] expected = new Tokens[][]{
                {CROSS, SUN, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {SUN, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        int[] hand = new int[]{9, 0, 0, 0};

        GameCrosswise game = new GameCrosswise(new Player[]{
                new GameAI("Flo", VERTICAL, true, true, hand)},
                field, new FakeGUI());
        game.causeTurn();
        assertArrayEquals(expected, field.getField());
    }

    @Test
    public void testCauseTurn_IncludesPlayerTurnAI_SwapWithHandToken() {

        Tokens[][] expected = new Tokens[][]{
                {SUN, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {CROSS, CROSS, CROSS, CROSS, CROSS, CROSS},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {SUN, CROSS, PENTAGON, STAR, SQUARE, TRIANGLE},
                {CROSS, SUN, CROSS, CROSS, CROSS, CROSS},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        int[] hand = new int[]{2, 10, 0, 0};

        GameCrosswise game = new GameCrosswise(new Player[]{
                new GameAI("Flo", VERTICAL, true, true, hand)},
                field, new FakeGUI());
        game.causeTurn();
        assertArrayEquals(expected, field.getField());
    }

    @Test
    public void testPlayerTurn_CombinationWithPlayerTurnAi() {

        Tokens[][] expected = new Tokens[][]{
                {SUN, SUN, SUN, SUN, SUN, SUN},
                {TRIANGLE, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        };

        GameField field = new GameField(new Tokens[][]{
                {SUN, SUN, SUN, SUN, SUN, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        int[] hand = new int[]{1, 0, 0, 0};

        GameCrosswise game = new GameCrosswise(new Player[]{new Player("Florian", HORIZONTAL,
                true, false)
                , new GameAI("Flo", VERTICAL, true, true, hand)},
                field, new FakeGUI());
        game.playerTurn(new GamePositions(1, 0), TRIANGLE, new GamePositions(3, 0),
                false, null);
        assertArrayEquals(expected, field.getField());
    }

    @Test
    public void testHandleSpecialToken() {
        GameField field = new GameField(new Tokens[][]{
                {SUN, SUN, SUN, SUN, SUN, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        });

        int[] hand = new int[]{1, 8, 0, 0};
        List<Tokens> expected = List.of(SUN, EMPTY, EMPTY);
        Player player = new Player("Flo", HORIZONTAL, true, false, hand);
        GameCrosswise game = new GameCrosswise(new Player[]{
                player}, field, new FakeGUI());
        game.handleSpecialToken(MOVE_STONE, new GamePositions(0, 0), new GamePositions(1, 0));
        assertArrayEquals(expected.toArray(), player.getPlayerHand().toArray());

    }

    // ----------------- PLAYABLETURN TESTS  ---------------------

    @Test
    public void testIsPlayableTurn_RemoveTokenFieldIsEmpty() {
        GameField field = new GameField(new Tokens[][]{
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
        });
        int[] hand = new int[]{7};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertFalse(game.isPlayableTurn(player[0].getPlayerHand()));
    }

    @Test
    public void testIsPlayableTurn_RemoveTokenFieldIsNotEmpty() {
        GameField field = new GameField(new Tokens[][]{
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, SUN},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
        });
        int[] hand = new int[]{7};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertTrue(game.isPlayableTurn(player[0].getPlayerHand()));
    }

    @Test
    public void testIsPlayableTurn_MoveTokenFieldIsEmpty() {
        GameField field = new GameField(new Tokens[][]{
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
        });
        int[] hand = new int[]{8};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertFalse(game.isPlayableTurn(player[0].getPlayerHand()));
    }

    @Test
    public void testIsPlayableTurn_MoveTokenFieldIsFull() {
        GameField field = new GameField(new Tokens[][]{
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
        });
        int[] hand = new int[]{8};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertFalse(game.isPlayableTurn(player[0].getPlayerHand()));
    }

    @Test
    public void testIsPlayableTurn_MoveTokenIsPlayable() {
        GameField field = new GameField(new Tokens[][]{
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {EMPTY, SUN},
        });
        int[] hand = new int[]{8};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertTrue(game.isPlayableTurn(player[0].getPlayerHand()));
    }

    @Test
    public void testIsPlayableTurn_SwapOnBoardTokenIsPlayable() {
        GameField field = new GameField(new Tokens[][]{
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {SUN, SUN},
                {EMPTY, SUN},
        });
        int[] hand = new int[]{9};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertTrue(game.isPlayableTurn(player[0].getPlayerHand()));
    }

    @Test
    public void testIsPlayableTurn_SwapOnBoardTokenIsNotPlayable() {
        GameField field = new GameField(new Tokens[][]{
                {SUN, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
        });
        int[] hand = new int[]{9};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertFalse(game.isPlayableTurn(player[0].getPlayerHand()));
    }

    @Test
    public void testIsPlayableTurn_SwapWithHandTokenIsNotPlayable() {
        GameField field = new GameField(new Tokens[][]{
                {SUN, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
        });
        int[] hand = new int[]{10, 9};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertFalse(game.isPlayableTurn(player[0].getPlayerHand()));
    }


    @Test
    public void testIsPlayableTurn_SwapWithHandTokenIsNotPlayable3() {
        GameField field = new GameField(new Tokens[][]{
                {EMPTY, EMPTY},
                {SUN, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
                {EMPTY, EMPTY},
        });
        int[] hand = new int[]{10, 0};
        Player[] player = new Player[] {new Player("Florian", VERTICAL, true, false, hand)};
        GameCrosswise game = new GameCrosswise(player, field, new FakeGUI());
        assertFalse(game.isPlayableTurn(player[0].getPlayerHand()));
    }

}
