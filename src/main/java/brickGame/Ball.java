package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball extends Main{


    public void initBall(int sceneWidth,int sceneHeigt) { //changed to public
        Random random = new Random();
        xBall = random.nextInt(sceneWidth) + 1;
        yBall = random.nextInt(sceneHeigt - 200) + ((level + 1) * Block.getHeight()) + 15;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
    }

    public Circle returnBall(){
        return ball;
    }//new class




}
