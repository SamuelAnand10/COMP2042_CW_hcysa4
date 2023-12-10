package BoardLogic;

import BlockLogic.Block;
import brickGame.Main;

import java.util.ArrayList;
import java.util.Random;
/**
 * The `Board` class is responsible for initializing the game board with blocks.
 */
public class Board extends Main {
    /**
     * Initializes the game board with blocks based on the given level.
     *
     * @param level The level of the game.
     */
    public void initBoard(int level) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;

                    }
                } else if (r % 10 == 3) {
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
     */
public ArrayList<Block> returnBlocks(){
        return blocks;
    }//new return function


}

