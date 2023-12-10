package CollisionLogic;

import BlockLogic.Block;
import brickGame.Main;

/**
 * The `collisionChecker` class is responsible for checking and handling collisions of the ball
 * with the walls, breaker, and blocks. It also manages the physics of the ball movement.
 */
public class collisionChecker extends Main {

    public boolean goDownBall = true;
    public boolean goRightBall = true;
    public boolean colideToBreak = false;
    public boolean colideToBreakAndMoveToRight = true;
    public boolean colideToRightWall = false;
    public boolean colideToLeftWall = false;
    public boolean colideToRightBlock = false;
    public boolean colideToBottomBlock = false;
    public boolean colideToLeftBlock = false;
    public boolean colideToTopBlock = false;

    public double vX = 1.000;
    public double vY = 1.000;
    /**
     * Resets all collision flags to their initial state.
     */
    public void resetColideFlags() {
        colideToBreak = false;
        colideToBreakAndMoveToRight = false;
        colideToRightWall = false;
        colideToLeftWall = false;
        colideToRightBlock = false;
        colideToBottomBlock = false;
        colideToLeftBlock = false;
        colideToTopBlock = false;
    }
    /**
     * Handles collisions with walls and adjusts ball movement accordingly.
     */
    public void WallCollide() {
        if (xBall >= sceneWidth) {
            resetColideFlags();
            colideToRightWall = true;
        }

        if (xBall <= 0) {
            resetColideFlags();
            colideToLeftWall = true;
        }

        if (colideToBreak) {
            if (colideToBreakAndMoveToRight) {
                goRightBall = true;
                xBall += vX;
            } else {
                goRightBall = false;
            }
        }

        if (colideToRightWall) {
            goRightBall = false;

        }
        if (colideToLeftWall) {
            goRightBall = true;
            if(vX == 0){
                vX = vX + 0.2;
        }
    }
    }
    /**
     * Handles collisions with the breaker and adjusts ball movement accordingly.
     */
    public void BreakerCollide(double xBreak) {
        if (((yBall + ballRadius) >= yBreak) && ((yBall - ballRadius) <= (yBreak + breakHeight))) {//added brackets
            if (((xBall + ballRadius) >= xBreak) && ((xBall - ballRadius) <= (xBreak + breakWidth))) {//added brackets
                if (!colideToBreak) {
                    hitTime = time;
                    resetColideFlags();
                    colideToBreak = true;
                    goDownBall = false;

                    double relation = ((xBall - centerBreakX) / (breakWidth / 2));//added brackets

                    if (Math.abs(relation) <= 0.3) {
                        vX = (Math.abs(relation) - 0.5);
                    } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                        vX = (Math.abs(relation) - 0.75);
                    } else {
                        vX = (Math.abs(relation) - 1);
                    }

                    if(vX < 0.3){
                        vX = vX + 0.1;
                    }

                    colideToBreakAndMoveToRight = (xBall - centerBreakX > 0);


                }
            }
        }
    }
    /**
     * Handles collisions with blocks and adjusts ball movement accordingly.
     */
    public void BlockCollide() {

//removed resetcollide initialization
                if (colideToTopBlock) {
                    goDownBall = false; // Reverse the vertical direction
                }

                if(colideToBottomBlock){
                    goDownBall = true;
                }
                if (colideToLeftBlock) {
                    goRightBall = false; // Reverse the horizontal direction
                }
                if(colideToRightBlock){
                    goRightBall = true;

                }

                resetColideFlags();

    }
    /**
     * Sets the physics for the ball movement based on collisions and updates its position.
     */
    public void setPhysicsToBall(double xBreak, boolean resumeFlag) {
        double originalXBall = xBall;
        double originalYBall = yBall;

        BreakerCollide(xBreak);
        WallCollide();
        BlockCollide();

        if (checkCollision(xBreak)) {
            xBall = originalXBall;
            yBall = originalYBall;
        }

        // Check if the ball is out of bounds
        if (yBall <= 0) {
            resetColideFlags();
            goDownBall = true;
        }

        if (yBall >= sceneHeigt) {
            goDownBall = false;

        }

        if (goDownBall) {
            yBall += vY;
        } else {
            yBall -= vY;
        }

        if (goRightBall) {
            xBall += vX;
        } else {
            xBall -= vX;
        }

        if(resumeFlag){
            xBall = originalXBall;
            yBall = originalYBall;
        }
    }
    /**
     * Gets the current x-coordinate of the ball.
     */
    public double getXball(){
     return xBall;
    }//getter
    /**
     * Gets the current y-coordinate of the ball.
     */
    public double getYball(){
        return yBall;
    }//getter

    /**
     * Checks for collisions with the breaker and blocks.
     * Returns true if a collision is detected, false otherwise.
     */
    private boolean checkCollision(double xBreak) {
        for (Block block : blocks) {
            if (block.checkHitToBlock(xBall, yBall) != Block.NO_HIT) {
                return true;
            }
        }

        return (xBall + ballRadius >= xBreak && xBall - ballRadius <= xBreak + breakWidth &&
                yBall + ballRadius >= yBreak && yBall - ballRadius <= yBreak + breakHeight);
    }
}

