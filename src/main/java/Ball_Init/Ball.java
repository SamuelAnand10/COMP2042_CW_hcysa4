package Ball_Init;

import BlockLogic.Block;
import brickGame.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;
/**
 * The `Ball` class manages the initialization and retrieval of the game ball.
 * @see <a href="https://github.com/SamuelAnand10/COMP2042_CW_hcysa4/tree/ff7a6c66a3ce2dd61fe0b9c8113bcfccf5896ef3/src/main/java/Ball_Init/Ball.java">Original Source Code</a>
 */
public class Ball extends Main {

    /**
     * Initializes the ball with a random position within the game scene.
     * The ball is positioned based on the scene width, scene height, and current game level.
     * @see #initBall(int, int)
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
     * @see #returnBall()
     */
    public Circle returnBall(){
        return ball;
    }//new class




}
