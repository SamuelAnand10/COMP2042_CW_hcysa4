package BonusLogic;

import BlockLogic.Block;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;
/**
 * The Bonus class represents a bonus object in the Brick Game application. Bonuses are created
 * when specific block types are destroyed and can be collected by the player for additional points.
 * This class implements Serializable to support game state saving/loading.
 * @see <a href="https://github.com/SamuelAnand10/COMP2042_CW_hcysa4/tree/ff7a6c66a3ce2dd61fe0b9c8113bcfccf5896ef3/src/main/java/BonusLogic/Bonus.java">Original Source Code</a>
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
     *
     */
    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
    }
    /**
     * Draws the bonus object by creating a Rectangle with a specific size and setting its fill
     * based on a randomly chosen bonus image.
     * @see #draw()
     */
    private void draw() {
        choco = new Rectangle();
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url;
        if (new Random().nextInt(20) % 2 == 0) {
            url = "bonus_1.png";
        } else {
            url = "bonus_2.png";
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }



}
