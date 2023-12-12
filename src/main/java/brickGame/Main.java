package brickGame;

import Ball_Init.Ball;
import BlockLogic.Block;
import BoardLogic.Board;
import BonusLogic.Bonus;
import Breaker.Break;
import CollisionLogic.collisionChecker;
import GameEngine.GameEngine;
import SavingLoadingLogic.GameLoaderSaver;
import Score.Score;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



import java.util.ArrayList;
    /**
     * The main class for the Brick Game application. It extends Application, implements
     * EventHandler for handling key events, and implements GameEngine.OnAction for game events.
     * This class initializes the game components, manages the game state, and handles user input.
     */
    public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

        private GameLoaderSaver gameLoaderSaver;
        public int level = 0;


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
        public Pane root;
        private Label            scoreLabel;
        public ImageView          heartLabel;
        private Label            levelLabel;

        public boolean loadFromSave = false;
        //made all private classes public
        public Stage  primaryStage;
        ImageView load;
        ImageView newGame;

        ImageView resume;

        ImageView restart;

        boolean resumeFlag = false;
        Ball BALL;
        Break aBreak;
        Board board;

        Pane pausepane;

        collisionChecker collisionChecker;
        /**
         * The main entry point of the JavaFX application. It initializes the game components,
         * sets up the user interface, and starts the game engine.
         *
         * @param primaryStage The primary stage for the JavaFX application.
         * @throws Exception If an exception occurs during application startup.
         */
        @Override
        public void start(Stage primaryStage) throws Exception {
            this.primaryStage = primaryStage;
            Pane startpane = createStartPane();
            pausepane = createPausePane();
            pausepane.setVisible(false);
            root = new Pane();
            BALL = new Ball();//assigned class
            aBreak = new Break();//assigned class
            board = new Board();//assigned class
            collisionChecker = new collisionChecker();//assigned class

            BALL.initBall(sceneWidth,sceneHeigt);

            aBreak.initBreak();

            board.initBoard(level);//initialized all



            if (level > 0 && level < 18) {//changed condition
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }

            level++;
            scoreLabel = new Label("Score: " + score);
            levelLabel = new Label("Level: " + level);
            levelLabel.setTranslateY(20);
            Image heartImage = null;
            switch (heart){
                case 3:
                    heartImage = new Image("heart3.png");
                    break;
                case 2:
                    heartImage = new Image("heart2.png");
                    break;
                case 1:
                    heartImage = new Image("heart1.png");
                    break;
                case 0:
                    heartImage = new Image("heart0.png");
                    break;

            }
            heartLabel = new ImageView(heartImage);
            heartLabel.setFitHeight(30);
            heartLabel.setFitWidth(100);
            heartLabel.setTranslateX(sceneWidth - 100);
            ball = BALL.returnBall();//new assign
            rect = aBreak.returnRect();//new assign
            Image background;
            ImageView backgroundView;
            if(level <= 7){
                background = new Image("backgroundV2.gif");
                backgroundView = new ImageView(background);
                backgroundView.setFitWidth(sceneWidth);
                backgroundView.setFitHeight(sceneHeigt);

            }
            else{
                background = new Image("backgroundV1.png");
                backgroundView = new ImageView(background);
                backgroundView.setFitWidth(sceneWidth);
                backgroundView.setFitHeight(sceneHeigt);
            }
            root.getChildren().addAll(backgroundView,rect, ball, scoreLabel, heartLabel, levelLabel);
            blocks = board.returnBlocks();// new assign
            for (Block block : blocks) {
                root.getChildren().add(block.rect);
            }
            if(level < 2){
                root.setVisible(false);
                startpane.setVisible(true);}
            else{
                startpane.setVisible(false);
                root.setVisible(true);
            }
            StackPane layout = new StackPane();//added a stack
            layout.getChildren().addAll(startpane, root,pausepane);
            Scene scene = new Scene(layout, sceneWidth, sceneHeigt);
            scene.getStylesheets().add("style.css");
            scene.setOnKeyPressed(this);

            primaryStage.setTitle("Game");
            primaryStage.setScene(scene);
            primaryStage.show();




// removed loadfromsave condition
            newGame.setOnMouseClicked(event -> {//changed to a image on set function
                startpane.setVisible(false);
                root.setVisible(true);
                engine = new GameEngine();

                engine.setOnAction(Main.this);
                engine.setFps(120);
                engine.start();

                load.setVisible(false);
                newGame.setVisible(false);

            });

            load.setOnMouseClicked(mouseEvent -> {//changed to a image on click function
                GameLoaderSaver gameLoaderSaver = new GameLoaderSaver();
                gameLoaderSaver.loadGame(Main.this);

                load.setVisible(false);
                newGame.setVisible(false);
                startpane.setVisible(false);  // Hide the start pane
                root.setVisible(true);
                engine = new GameEngine();
                engine.setOnAction(Main.this);
                engine.setFps(120);
                engine.start();
                loadFromSave = false;
            });//moved to the right spot

            resume.setOnMouseClicked(event -> {//changed to a image on set function

                pausepane.setVisible(false);  // Hide the pause pane
                root.setVisible(true);
                resumeFlag = false;
            });

            restart.setOnMouseClicked(event -> {//changed to a image on set function
                resumeFlag = false;
                pausepane.setVisible(false);  // Hide the pause pane
                try {
                    restartGame();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });


        }

        /**
         * Creates and returns a pane for the start screen, including background and start buttons.
         *
         * @return A JavaFX Pane representing the start screen.
         */
        private Pane createStartPane() {//made a create start scene function
            Pane pane = new Pane();

            // Add background image to start pane
            Image backgroundImageImage = new Image("StartMenu.png");
            ImageView backgroundImageView = new ImageView(backgroundImageImage);
            backgroundImageView.setFitHeight(sceneHeigt);
            backgroundImageView.setFitWidth(sceneWidth);

            pane.getChildren().add(backgroundImageView);

            // Add "Start New Game" button
            Image newGameImage = new Image("load.png");
            newGame = new ImageView(newGameImage);
            newGame.setFitWidth(150);
            newGame.setFitHeight(50);
            newGame.setTranslateX(175);
            newGame.setTranslateY(300);
            pane.getChildren().add(newGame);

            // Add "Load Game" button
            Image loadImage = new Image("start.png");
            load = new ImageView(loadImage);
            load.setFitWidth(150);
            load.setFitHeight(50);
            load.setTranslateX(175);
            load.setTranslateY(400);
            pane.getChildren().add(load);

            return pane;
        }
        /**
         * Creates and returns a pane for the pause screen, including background and pause buttons.
         *
         * @return A JavaFX Pane representing the pause screen.
         */
        private Pane createPausePane() {//made a create pause scene function
            Pane pane = new Pane();

            // Add background image to start pane
            Image backgroundImageImage = new Image("Minimal Illustration Outer Space Mouse Pad.png");
            ImageView backgroundImageView = new ImageView(backgroundImageImage);
            backgroundImageView.setFitHeight(sceneHeigt);
            backgroundImageView.setFitWidth(sceneWidth);

            pane.getChildren().add(backgroundImageView);

            // Add "Start New Game" button
            Image resumeImage = new Image("resume.png");
            resume = new ImageView(resumeImage);
            resume.setFitWidth(150);
            resume.setFitHeight(50);
            resume.setTranslateX(175);
            resume.setTranslateY(300);
            pane.getChildren().add(resume);

            // Add "Load Game" button
            Image restartImage = new Image("restart.png");
            restart = new ImageView(restartImage);
            restart.setFitWidth(150);
            restart.setFitHeight(50);
            restart.setTranslateX(175);
            restart.setTranslateY(400);
            pane.getChildren().add(restart);

            return pane;
        }
        /**
         * Pauses the game, displaying the pause screen and hiding the main game screen.
         * Sets the resumeFlag to true, indicating that the game is in a paused state.
         */
        public void pauseGame(){//pause function
            resumeFlag = true;
            pausepane.setVisible(true);
            root.setVisible(false);
        }

        /**
         * Saves the current game state, including level, score, heart count, and other parameters.
         * Utilizes the GameLoaderSaver class to perform the actual saving process.
         */
        public void saveGame() {
            GameLoaderSaver gameLoaderSaver = new GameLoaderSaver();

            gameLoaderSaver.saveGame(level, score, heart, destroyedBlockCount, xBall, yBall, aBreak.xBreak, yBreak, centerBreakX,
                    time, goldTime, collisionChecker.vX, isExistHeartBlock, isGoldStauts, collisionChecker.goDownBall, collisionChecker.goRightBall, collisionChecker.colideToBreak,
                    collisionChecker.colideToBreakAndMoveToRight, collisionChecker.colideToRightWall, collisionChecker.colideToLeftWall, collisionChecker.colideToRightBlock,
                    collisionChecker.colideToBottomBlock, collisionChecker.colideToLeftBlock, collisionChecker.colideToTopBlock, blocks);
        }//made a class calling function






        public static void main(String[] args) {
            launch(args);
        }

        /**
         * Handles key events, such as moving the breaker, saving the game, and pausing the game.
         *
         * @param event The KeyEvent to be handled.
         */

        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case LEFT:
                    moveBreaker(-10); // Move left
                    break;
                case RIGHT:
                    moveBreaker(10); // Move right
                    break;
                case S:
                    saveGame();
                    break;

                case P:
                    pauseGame();
            }
        }
        /**
         * Moves the breaker (paddle) based on the given deltaX value.
         * Ensures that the breaker remains within the scene boundaries.
         *
         * @param deltaX The amount to move the breaker horizontally.
         */
        private void moveBreaker(int deltaX) {
            double newX = aBreak.xBreak + deltaX;//changed to class variable
            if (newX >= 0 && newX + breakWidth <= sceneWidth) {
                aBreak.xBreak = newX;
                centerBreakX = aBreak.xBreak + halfBreakWidth;
                aBreak.returnRect().setX(aBreak.xBreak);
            }
        }// added a new function


//deleted no use variable








        /**
         * Checks if all blocks have been destroyed. If true, triggers actions for winning the level.
         */
        private void checkDestroyedCount() {
            if (destroyedBlockCount == blocks.size() && destroyedBlockCount != 0) {//added second condition
                //System.out.println("You Win");

                nextLevel();
            }
        }

        /**
         * Initiates the transition to the next level of the game. Resets necessary parameters,
         * clears the blocks, and starts the new level.
         */
        private void nextLevel() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        collisionChecker.vX = 1.000;
                        collisionChecker = new collisionChecker();  // Add this line to reinitialize collisionChecker

                        engine.stop();
                        collisionChecker.resetColideFlags();
                        collisionChecker.goDownBall = true;

                        isGoldStauts = false;
                        isExistHeartBlock = false;

                        hitTime = 0;
                        time = 0;
                        goldTime = 0;
                        if (level >1){

                            new Score().showMessage("Level Up :)", root);
                        }
                        if (level == 18) {
                            new Score().showWin(root);
                            return;
                        }

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

        /**
         * Restarts the game, resetting level, heart count, score, and other parameters.
         */
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

        /**
         * Called when an update to the game state is needed. Handles the update of score and UI elements.
         */
        @Override
        public void onUpdate() {


            if (yBall >= sceneHeigt) {
                collisionChecker.goDownBall = false;
                if (!isGoldStauts) {
                    heart--;
                    new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, root);
                    if (heart == 0) {
                        new Score().showGameOver(this);
                        engine.stop();
                    }
                }
            }//run heart reduction algo
            Platform.runLater(new Runnable() {


                @Override
                public void run() {

                    scoreLabel.setText("Score: " + score);
                    Image heartImage = null;
                    switch (heart){
                        case 3:
                            heartImage = new Image("heart3.png");
                            break;
                        case 2:
                            heartImage = new Image("heart2.png");
                            break;
                        case 1:
                            heartImage = new Image("heart1.png");
                            break;
                        case 0:
                            heartImage = new Image("heart0.png");
                            break;

                    }
                    heartLabel.setFitHeight(30);
                    heartLabel.setFitWidth(100);
                    heartLabel.setImage(heartImage);


                    rect.setX(aBreak.xBreak);
                    rect.setY(yBreak);
                    ball.setCenterX(xBall);
                    ball.setCenterY(yBall);

                    for (Bonus choco : chocos) {
                        choco.choco.setY(choco.y);
                    }
                }
            });



            if (yBall >= Block.getPaddingTop() && yBall <= ((Block.getHeight() * (level + 1)) + Block.getPaddingTop())) {//added bracket
                for (final Block block : blocks) {
                    int hitCode = block.checkHitToBlock(xBall, yBall);
                    if (hitCode != Block.NO_HIT) {
                        score += 1;

                        new Score().show(block.x, block.y, 1,root);

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
                            ball.setFill(new ImagePattern(new Image("ball.png")));
                            System.out.println("gold ball");

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

        /**
         * Called during the initialization phase of the game. Currently not used in this implementation.
         */
        @Override
        public void onInit() {

        }
        /**
         * Called during the physics update phase of the game. Updates game physics, checks collisions,
         * and manages bonuses such as chocos and gold balls.
         */
        @Override
        public void onPhysicsUpdate() {
            Platform.runLater(() -> {
                checkDestroyedCount();
                collisionChecker.setPhysicsToBall(aBreak.xBreak,resumeFlag);



                if (time - goldTime > 5000) {
                    ball.setFill(new ImagePattern(new Image("goldball.png")));
                    root.getStyleClass().remove("goldRoot");
                    isGoldStauts = false;
                }

                for (Bonus choco : chocos) {
                    if (choco.y > sceneHeigt || choco.taken) {
                        continue;
                    }
                    if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= aBreak.xBreak && choco.x <= aBreak.xBreak + breakWidth) {
                        System.out.println("You Got it and +3 score for you");
                        choco.taken = true;
                        choco.choco.setVisible(false);
                        score += 3;
                        new Score().show(choco.x, choco.y, 3,root);
                    }
                    choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
                }


                // Update the position of the ball in the scene

                xBall = collisionChecker.getXball();//added
                yBall = collisionChecker.getYball();//added
                ball.setCenterX(xBall);
                ball.setCenterY(yBall);
            });
            //System.out.println("time is:" + time + " goldTime is " + goldTime);

        }

        /**
         * Called to update the game time. Sets the current time for game-related calculations.
         *
         * @param time The current game time.
         */
        @Override
        public void onTime(long time) {
            this.time = time;
        }
    }


