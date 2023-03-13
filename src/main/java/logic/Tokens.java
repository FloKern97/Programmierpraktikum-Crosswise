package logic;

import javafx.scene.image.Image;

/**
 * Enum for the tokens used by the players.
 *
 * @author Florian Kern/cgt104661
 */

public enum Tokens {
    EMPTY, SUN, CROSS, TRIANGLE, SQUARE, PENTAGON,
    STAR, REMOVE_STONE, MOVE_STONE, SWAP_ON_BOARD, SWAP_WITH_HAND;


    public static final int ONE_OF_EACH = 6;
    public static final int PAIR = 1;
    public static final int TRIPLET = 3;
    public static final int QUADRUPLET = 5;
    public static final int QUINTUPLET = 7;
    public static final int SEXTUPLET = 6;
    public static final int AMOUNT_OF_REGULAR_TOKEN_IN_GAME = 7;
    public static final int AMOUNT_OF_STANDARD_TOKENS = 6;
    public static final int AMOUNT_OF_SPECIAL_TOKEN_IN_GAME = 3;
    public static final int MAX_ORDINAL = 10;


    /**
     * Method gets the url of a token based on its ordinal number.
     *
     * @param ordinal The ordinal of the token
     * @return The url of the token.
     */
    public static Tokens ordinalToToken(int ordinal) {
        return switch (ordinal) {
            case 0 -> EMPTY;
            case 1 -> SUN;
            case 2 -> CROSS;
            case 3 -> TRIANGLE;
            case 4 -> SQUARE;
            case 5 -> PENTAGON;
            case 6 -> STAR;
            case 7 -> REMOVE_STONE;
            case 8 -> MOVE_STONE;
            case 9 -> SWAP_ON_BOARD;
            case 10 -> SWAP_WITH_HAND;
            default -> throw new IllegalArgumentException("Numbers below zero and numbers above 10 are not allowed.");
        };
    }

    /**
     * Gets the corresponding image-URL of a token.
     *
     * @param token - The token
     * @return The URL of the corresponding image
     */
    public static String tokenToURL(Tokens token) {
        return switch (token) {
            case EMPTY -> "/gui/img/none.png";
            case SUN -> "/gui/img/sun.png";
            case CROSS -> "/gui/img/cross.png";
            case TRIANGLE -> "/gui/img/triangle.png";
            case SQUARE -> "/gui/img/square.png";
            case PENTAGON -> "/gui/img/pentagon.png";
            case STAR -> "/gui/img/star.png";
            case REMOVE_STONE -> "/gui/img/remove.png";
            case MOVE_STONE -> "/gui/img/move.png";
            case SWAP_ON_BOARD -> "/gui/img/swapOnBoard.png";
            case SWAP_WITH_HAND -> "/gui/img/swapWithHand.png";
        };
    }


    /**
     * Method checks if a token is special or not.
     *
     * @param token - The token
     * @return True if the token is a special token, false if not.
     */
    public static boolean isSpecialToken(Tokens token) {
        return switch (token) {
            case REMOVE_STONE, MOVE_STONE, SWAP_ON_BOARD, SWAP_WITH_HAND -> true;
            default -> false;
        };
    }


    /**
     * Method gets a token from an image.
     *
     * @param img - The image.
     * @return A token depending on the incoming image.
     */
    public static Tokens getTokenFromPicture(Image img) {
        String url = img.getUrl();
        if (url.equals((new Image(Tokens.tokenToURL(Tokens.EMPTY))).getUrl())) {
            return Tokens.EMPTY;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.SUN)).getUrl())) {
            return Tokens.SUN;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.CROSS)).getUrl())) {
            return Tokens.CROSS;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.TRIANGLE)).getUrl())) {
            return Tokens.TRIANGLE;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.STAR)).getUrl())) {
            return Tokens.STAR;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.PENTAGON)).getUrl())) {
            return Tokens.PENTAGON;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.SQUARE)).getUrl())) {
            return Tokens.SQUARE;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.REMOVE_STONE)).getUrl())) {
            return Tokens.REMOVE_STONE;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.SWAP_ON_BOARD)).getUrl())) {
            return Tokens.SWAP_ON_BOARD;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.SWAP_WITH_HAND)).getUrl())) {
            return Tokens.SWAP_WITH_HAND;
        } else if (url.equals(new Image(Tokens.tokenToURL(Tokens.MOVE_STONE)).getUrl())) {
            return Tokens.MOVE_STONE;
        } else {
            throw new IllegalArgumentException("URL does not match any known picture.");
        }
    }

}
