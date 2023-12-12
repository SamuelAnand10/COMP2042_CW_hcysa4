package BlockLogic;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

/**
 * The `Block` class represents a block in the game. It contains information about the block's
 * position, type, and whether it has been destroyed or not. The class also provides methods for
 * drawing the block and checking for collisions with the ball.
 * @see <a href="...BlockLogic/Block.java">Original Source Code</a>
 */
public class Block implements Serializable {
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);



    public int row;
    public int column;


    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int x;
    public int y;

    private int width = 75;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 50;
    public Rectangle rect;


    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;

    /**
     * Constructs a block with the specified row, column, color, and type.
     */
    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;


        draw();
    }
    /**
     * Draws the block based on its type and sets the fill accordingly.
     * @see #draw()
     */
    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.gif");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.gif");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            int randomInt = new Random().nextInt(1,7);
            Image image = switch (randomInt) {
                case 1 -> new Image("brickV1.png");
                case 2 -> new Image("brickV2.png");
                case 3 -> new Image("brickV3.png");
                case 4 -> new Image("brickV4.png");
                case 5 -> new Image("brickV5.png");
                case 6 -> new Image("brickV6.png");
                default -> null;
            };

            assert image != null;
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }

    }



    /**
     * Checks for collisions with the ball and returns the type of hit or NO_HIT if no collision.
     * @see #checkHitToBlock(double, double)
     */
    public int checkHitToBlock(double xBall, double yBall) {

        if (isDestroyed) {
            return NO_HIT;
        }

        if (xBall >= x && xBall <= x + width && yBall == y + height) {
            return HIT_BOTTOM;
        }

        if (xBall >= x && xBall <= x + width && yBall == y) {
            return HIT_TOP;
        }

        if (yBall >= y && yBall <= y + height && xBall == x + width) {
            return HIT_RIGHT;
        }

        if (yBall >= y && yBall <= y + height && xBall == x) {
            return HIT_LEFT;
        }

        return NO_HIT;
    }
    /**
     * Returns the top padding of the blocks.
     * @see #getPaddingTop()
     */
    public static int getPaddingTop() {
        return block.paddingTop;
    }
    /**
     * Returns the horizontal padding of the blocks.
     * @see #getPaddingH()
     */
    public static int getPaddingH() {
        return block.paddingH;
    }
    /**
     * Returns the height of the blocks.
     * @see #getHeight()
     */
    public static int getHeight() {
        return block.height;
    }
    /**
     * Returns the width of the blocks.
     * @see #getWidth()
     */
    public static int getWidth() {
        return block.width;
    }

}
