package brickGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class GameLoaderSaver extends Ball {

    public static String savePath = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

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

    public void loadGame(Main main) {
        LoadSave loadSave = new LoadSave();
        loadSave.read();
        collisionChecker collisionChecker = new collisionChecker();

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
        main.xBreak = loadSave.xBreak;
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

