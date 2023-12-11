package Ball_Init;

import BlockLogic.Block;
import brickGame.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;
/**
 * The `Ball` class manages the initialization and retrieval of the game ball.
 */
public class Ball extends Main {

    /**
     * Initializes the ball with a random position within the game scene.
     * The ball is positioned based on the scene width, scene height, and current game level.
     */
    public void initBall(int sceneWidth,int sceneHeigt) { //changed to public
        Random random = new Random();
        xBall = random.nextInt(sceneWidth) + 1;
        yBall = random.nextInt(sceneHeigt - 200) + ((level + 1) * Block.getHeight()) + 15;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("goldball.png")));
    }
    /**
     * Returns the ball object.
     */
    public Circle returnBall(){
        return ball;
    }//new class




}
