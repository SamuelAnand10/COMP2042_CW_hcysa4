package SavingLoadingLogic;

import Ball_Init.Ball;
import BlockLogic.Block;
import BlockLogic.BlockSerializable;
import Breaker.Break;
import CollisionLogic.collisionChecker;
import brickGame.Main;
import Score.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * The `GameLoaderSaver` class is responsible for saving and loading the game state.
 * @see <a href="...SavingLoadingLogic/GameLoaderSaver.java">Original Source Code</a>
 */
public class GameLoaderSaver extends Ball {
    /**
     * The file path for saving the game state.
     * @see #savePath
     */
    public static String savePath = "D:/save/save.mdds";
    /**
     * The directory path for saving the game state file.
     * @see #savePathDir
     */
    public static String savePathDir = "D:/save/";
    /**
     * Saves the game state to a file.
     *
     * @param level                        The level of the game.
     * @param score                        The current score.
     * @param heart                        The number of hearts remaining.
     * @param destroyedBlockCount          The count of destroyed blocks.
     * @param xBall                        The x-coordinate of the ball.
     * @param yBall                        The y-coordinate of the ball.
     * @param xBreak                       The x-coordinate of the breaker.
     * @param yBreak                       The y-coordinate of the breaker.
     * @param centerBreakX                 The center x-coordinate of the breaker.
     * @param time                         The current time in the game.
     * @param goldTime                     The time remaining for the gold status.
     * @param vX                           The x-velocity of the ball.
     * @param isExistHeartBlock            Flag indicating the existence of heart blocks.
     * @param isGoldStauts                 Flag indicating the gold status.
     * @param goDownBall                   Flag indicating the downward movement of the ball.
     * @param goRightBall                  Flag indicating the rightward movement of the ball.
     * @param colideToBreak                Flag indicating collision with the breaker.
     * @param colideToBreakAndMoveToRight  Flag indicating collision and movement to the right.
     * @param colideToRightWall            Flag indicating collision with the right wall.
     * @param colideToLeftWall             Flag indicating collision with the left wall.
     * @param colideToRightBlock           Flag indicating collision with a block on the right.
     * @param colideToBottomBlock          Flag indicating collision with a block at the bottom.
     * @param colideToLeftBlock            Flag indicating collision with a block on the left.
     * @param colideToTopBlock             Flag indicating collision with a block at the top.
     * @param blocks                       The list of blocks in the game.
     *                                     @see #saveGame() 
     */
    public void saveGame(int level, int score, int heart, int destroyedBlockCount,
                         double xBall, double yBall, double xBreak, double yBreak, double centerBreakX,
                         long time, long goldTime, double vX, boolean isExistHeartBlock, boolean isGoldStauts,
                         boolean goDownBall, boolean goRightBall, boolean colideToBreak, boolean colideToBreakAndMoveToRight,
                         boolean colideToRightWall, boolean colideToLeftWall, boolean colideToRightBlock,
                         boolean colideToBottomBlock, boolean colideToLeftBlock, boolean colideToTopBlock,
                         ArrayList<Block> blocks) {
        new Thread(() -> {
            File directory = new File(savePathDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }
            File file = new File(savePath);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {

                outputStream.writeInt(level);
                outputStream.writeInt(score);
                outputStream.writeInt(heart);
                outputStream.writeInt(destroyedBlockCount);

                outputStream.writeDouble(xBall);
                outputStream.writeDouble(yBall);
                outputStream.writeDouble(xBreak);
                outputStream.writeDouble(yBreak);
                outputStream.writeDouble(centerBreakX);
                outputStream.writeLong(time);
                outputStream.writeLong(goldTime);
                outputStream.writeDouble(vX);

                outputStream.writeBoolean(isExistHeartBlock);
                outputStream.writeBoolean(isGoldStauts);
                outputStream.writeBoolean(goDownBall);
                outputStream.writeBoolean(goRightBall);
                outputStream.writeBoolean(colideToBreak);
                outputStream.writeBoolean(colideToBreakAndMoveToRight);
                outputStream.writeBoolean(colideToRightWall);
                outputStream.writeBoolean(colideToLeftWall);
                outputStream.writeBoolean(colideToRightBlock);
                outputStream.writeBoolean(colideToBottomBlock);
                outputStream.writeBoolean(colideToLeftBlock);
                outputStream.writeBoolean(colideToTopBlock);

                ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
                for (Block block : blocks) {
                    if (!block.isDestroyed) {
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }
                }

                outputStream.writeObject(blockSerializables);

                new Score().showMessage("Game Saved", root); // Update this part accordingly

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    /**
     * Loads the game state from a file.
     *
     * @param main The main class instance.
     *             @see #loadGame(Main)
     */
    public void loadGame(Main main) {
        LoadSave loadSave = new LoadSave();
        loadSave.read();
        collisionChecker collisionChecker = new collisionChecker();
        Break aBreak = new Break();

        main.isExistHeartBlock = loadSave.isExistHeartBlock;
        main.isGoldStauts = loadSave.isGoldStauts;
        collisionChecker.goDownBall = loadSave.goDownBall;
        collisionChecker.goRightBall = loadSave.goRightBall;
        collisionChecker.colideToBreak = loadSave.colideToBreak;
        collisionChecker.colideToBreakAndMoveToRight = loadSave.colideToBreakAndMoveToRight;
        collisionChecker.colideToRightWall = loadSave.colideToRightWall;
        collisionChecker.colideToLeftWall = loadSave.colideToLeftWall;
        collisionChecker.colideToRightBlock = loadSave.colideToRightBlock;
        collisionChecker.colideToBottomBlock = loadSave.colideToBottomBlock;
        collisionChecker.colideToLeftBlock = loadSave.colideToLeftBlock;
        collisionChecker.colideToTopBlock = loadSave.colideToTopBlock;
        main.level = loadSave.level;
        main.score = loadSave.score;
        main.heart = loadSave.heart;
        main.destroyedBlockCount = loadSave.destroyedBlockCount;
        Ball ball = new Ball();
        ball.xBall = loadSave.xBall;
        ball.yBall = loadSave.yBall;
        aBreak.xBreak = loadSave.xBreak;
        main.yBreak = loadSave.yBreak;
        main.centerBreakX = loadSave.centerBreakX;
        main.time = loadSave.time;
        main.goldTime = loadSave.goldTime;
        collisionChecker.vX = loadSave.vX;

        main.blocks.clear();
        main.chocos.clear();

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            main.blocks.add(new Block(ser.row, ser.j, main.colors[r % main.colors.length], ser.type));
        }

        try {
            main.loadFromSave = true;
            main.start(main.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
