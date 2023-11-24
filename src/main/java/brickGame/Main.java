package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

private GameLoaderSaver gameLoaderSaver;
    public int level = 0;

    public double xBreak = 0.0f;
    public double centerBreakX;
    public double yBreak = 640.0f;

    public Circle ball;
    public double xBall;
    public double yBall;


    public int breakWidth     = 130;
    public int breakHeight    = 30;
    public int halfBreakWidth = breakWidth / 2;

    public int sceneWidth = 500;
    public int sceneHeigt = 700;

    public static int LEFT  = 1;
    public static int RIGHT = 2;



    public boolean isGoldStauts      = false;
    public boolean isExistHeartBlock = false;

    public Rectangle rect;
    public int       ballRadius = 10;

    public int destroyedBlockCount = 0;
    //removed v = 1.000

    public int  heart    = 3;
    public int  score    = 0;
    public long time     = 0;
    public long hitTime  = 0;
    public long goldTime = 0;

    public GameEngine engine;


    public ArrayList<Block> blocks = new ArrayList<Block>();
    public ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    public Color[]          colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };
    public  Pane             root;
    private Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;

    public boolean loadFromSave = false;
//made all private classes public
    Stage  primaryStage;
    Button load;
    Button newGame;

    Ball BALL;
    Break aBreak;
    Board board;

    collisionChecker collisionChecker;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        root = new Pane();
        load = new Button("Load Game");
        newGame = new Button("Start New Game");
        load.setTranslateX(220);
        load.setTranslateY(300);
        newGame.setTranslateX(220);
        newGame.setTranslateY(340);
         BALL = new Ball();//assigned class
         aBreak = new Break();//assigned class
         board = new Board();//assigned class
        collisionChecker = new collisionChecker();//assigned class

        if (loadFromSave == false) {
            level++;
            if (level >1){
                new Score().showMessage("Level Up :)", this);
            }
            if (level == 18) {
                new Score().showWin(this);
                return;
            }

            BALL.initBall(sceneWidth,sceneHeigt);

            aBreak.initBreak();

            board.initBoard();//initialized all



        }




        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);
        ball = BALL.returnBall();//new assign
        rect = aBreak.returnRect();//new assign
        if (loadFromSave == false) {

            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, newGame);
        } else {
            root.getChildren().addAll(rect,ball, scoreLabel, heartLabel, levelLabel);
        }
        blocks = board.returnBlocks();// new assign
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
        Scene scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (loadFromSave == false) {
            if (level > 0 && level < 18) {//changed condition
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }



            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    engine = new GameEngine();
                    engine.setOnAction(Main.this);
                    engine.setFps(120);
                    engine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });
        } else {
            load.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    GameLoaderSaver gameLoaderSaver = new GameLoaderSaver();
                    gameLoaderSaver.loadGame(Main.this);

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });//moved to the right spot


            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();
            loadFromSave = false;
        }


    }

    public void saveGame() {
        GameLoaderSaver gameLoaderSaver = new GameLoaderSaver();

        gameLoaderSaver.saveGame(level, score, heart, destroyedBlockCount, xBall, yBall, xBreak, yBreak, centerBreakX,
                time, goldTime, collisionChecker.vX, isExistHeartBlock, isGoldStauts, collisionChecker.goDownBall, collisionChecker.goRightBall, collisionChecker.colideToBreak,
                collisionChecker.colideToBreakAndMoveToRight, collisionChecker.colideToRightWall, collisionChecker.colideToLeftWall, collisionChecker.colideToRightBlock,
                collisionChecker.colideToBottomBlock, collisionChecker.colideToLeftBlock, collisionChecker.colideToTopBlock, blocks);
    }//made a class calling function






    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        double flag;
        switch (event.getCode()) {

            case LEFT:

                flag = aBreak.move(Break.LEFT);
                if(flag != 0){
                    xBreak = flag;
                }

                break;

            case RIGHT:

                flag = aBreak.move(Break.RIGHT);
                if(flag != 0){
                    xBreak = flag;
                }



                break;
            case DOWN:
                //setPhysicsToBall();
                break;
            case S:
                saveGame();
                break;
        }
    }

//deleted no use variable









    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");

            nextLevel();
        }
    }


    private void nextLevel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    collisionChecker.vX = 1.000;

                    engine.stop();
                    collisionChecker.resetColideFlags();
                    collisionChecker.goDownBall = true;

                    isGoldStauts = false;
                    isExistHeartBlock = false;


                    hitTime = 0;
                    time = 0;
                    goldTime = 0;

                    engine.stop();
                    blocks.clear();
                    chocos.clear();
                    destroyedBlockCount = 0;
                    start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void restartGame() {

        try {
            level = 0;
            heart = 3;
            score = 0;
            collisionChecker.vX = 1.000;
            destroyedBlockCount = 0;
            collisionChecker.resetColideFlags();
            collisionChecker.goDownBall = true;

            isGoldStauts = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);

                rect.setX(xBreak);
                rect.setY(yBreak);
                ball.setCenterX(xBall);
                ball.setCenterY(yBall);

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });


        if (yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall);
                if (hitCode != Block.NO_HIT) {
                    score += 1;

                    new Score().show(block.x, block.y, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    collisionChecker.resetColideFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = time;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.getChildren().add(choco.choco);
                            }
                        });
                        chocos.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        root.getStyleClass().add("goldRoot");
                        isGoldStauts = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                    }

                    if (hitCode == Block.HIT_RIGHT) {
                        collisionChecker.colideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        collisionChecker.colideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        collisionChecker.colideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        collisionChecker.colideToTopBlock = true;
                    }

                }

                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }


    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        collisionChecker.setPhysicsToBall();


        xBall = collisionChecker.xBall;//added

        if (time - goldTime > 5000) {
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStauts = false;
        }

        for (Bonus choco : chocos) {
            if (choco.y > sceneHeigt || choco.taken) {
                continue;
            }
            if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= xBreak && choco.x <= xBreak + breakWidth) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                new Score().show(choco.x, choco.y, 3, this);
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }

        yBall = collisionChecker.yBall;//added

        // Update the position of the ball in the scene
        Platform.runLater(() -> {
            ball.setCenterX(xBall);
            ball.setCenterY(yBall);
        });
        //System.out.println("time is:" + time + " goldTime is " + goldTime);

    }


    @Override
    public void onTime(long time) {
        this.time = time;
    }
}
