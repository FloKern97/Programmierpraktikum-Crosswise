package logic;

import static logic.Tokens.*;

import java.util.*;

/**
 * Class for the pouch in which the tokens of the game are stored in.
 *
 * @author Florian Kern/cgt104661
 */
public class TokenPouch {

    /**
     * The pouch from where the players receive their tokens from.
     */
    private final Queue<Tokens> tokenPouch;

    /**
     * Constructor for the token pouch. Uses method to initialize
     * it with a randomized queue.
     */
    public TokenPouch() {
        this.tokenPouch = fillPouch();
    }

    /**
     * Constructor for loading a game with a token pouch whose values are represented with ordinal values.
     *
     * @param pouch - The pouch with ordinal values
     */
    public TokenPouch(int[] pouch) {
        this.tokenPouch = new LinkedList<>(Arrays.stream(pouch).mapToObj(Tokens::ordinalToToken).toList());
    }

    /**
     * Getter for the next token in the pouch.
     *
     * @return The next token in the pouch.
     */
    public Tokens getToken() {
        return this.tokenPouch.poll();
    }

    public Queue<Tokens> getTokenPouch() {
        return this.tokenPouch;
    }

    /**
     * Generates a randomized list for the token pouch.
     *
     * @return A randomized list.
     */
    private List<Tokens> generateRandomList() {
        List<Tokens> res = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_REGULAR_TOKEN_IN_GAME; i++) {
            // Adding regular tokens to the pouch
            res.add(CROSS);
            res.add(SUN);
            res.add(SQUARE);
            res.add(TRIANGLE);
            res.add(PENTAGON);
            res.add(STAR);
            if (i < AMOUNT_OF_SPECIAL_TOKEN_IN_GAME) {
                // Adding special tokens to the pouch
                res.add(REMOVE_STONE);
                res.add(MOVE_STONE);
                res.add(SWAP_ON_BOARD);
                res.add(SWAP_WITH_HAND);
            }
        }
        // Shuffling the result to have a random order
        Collections.shuffle(res);
        return res;
    }


    /**
     * Method fills the pouch with the maximum amount of stones in a random order.
     *
     * @return The token pouch filled with tokens in a random order
     */
    private Queue<Tokens> fillPouch() {
        return new LinkedList<>(generateRandomList());
    }


}
