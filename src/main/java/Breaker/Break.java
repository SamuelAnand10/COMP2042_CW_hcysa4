package Breaker;

import brickGame.Main;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * The `Break` class manages the initialization and retrieval of the game breaker.
 */

public class Break extends Main {
    public double xBreak = 0.0f;
    private Rectangle rect;
    /**
     * Initializes the breaker with a rectangle and an image pattern.
     */
    public void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));

        rect.setFill(pattern);
    }
    /**
     * Returns the rectangle representing the breaker.
     */
    public Rectangle returnRect() {
        return rect;
    }

   //removed the move() function to main
}
