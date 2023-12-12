package BoardLogic;

import BlockLogic.Block;
import brickGame.Main;

import java.util.ArrayList;
import java.util.Random;
/**
 * The `Board` class is responsible for initializing the game board with blocks.
 * @see <a href="...BoardLogic/Board.java">Original Source Code</a>
 */
public class Board extends Main {
    /**
     * Initializes the game board with blocks based on the given level.
     *
     * @param level The level of the game.
     *              @see #initBoard(int)
     */
    public void initBoard(int level) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(100);
                int type;
                if (r<10) {
                    type = Block.BLOCK_CHOCO;
                } else if (r<20 && r >= 10) {
                    if (!isExistHeartBlock && (heart < 3)) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;

                    }
                } else if (r<30 && r >= 20) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }

    /**
     * Returns the list of blocks on the game board.
     *
     * @return The list of blocks.
     * @see #returnBlocks()
     */
public ArrayList<Block> returnBlocks(){
        return blocks;
    }//new return function


}

