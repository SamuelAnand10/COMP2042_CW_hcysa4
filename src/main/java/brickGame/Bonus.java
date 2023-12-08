package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;
/**
 * The Bonus class represents a bonus object in the Brick Game application. Bonuses are created
 * when specific block types are destroyed and can be collected by the player for additional points.
 * This class implements Serializable to support game state saving/loading.
 */

public class Bonus implements Serializable {
    public Rectangle choco;

    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;
    /**
     * Constructs a Bonus object at the specified row and column position on the game board.
     *
     * @param row    The row position of the associated block.
     * @param column The column position of the associated block.
     */
    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
    }
    /**
     * Draws the bonus object by creating a Rectangle with a specific size and setting its fill
     * based on a randomly chosen bonus image.
     */
    private void draw() {
        choco = new Rectangle();
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url;
        if (new Random().nextInt(20) % 2 == 0) {
            url = "bonus1.png";
        } else {
            url = "bonus2.png";
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }



}
