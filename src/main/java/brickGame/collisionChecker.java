package brickGame;

import javafx.application.Platform;

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
            } else {
                goRightBall = false;
            }
        }

        if (colideToRightWall) {
            goRightBall = false;
        }

        if (colideToLeftWall) {
            goRightBall = true;
        }
    }

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
                        vX = Math.abs(relation);
                    } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                        vX = (Math.abs(relation) * 1.1) + (level / 3.500);
                    } else {
                        vX = (Math.abs(relation) * 1.2) + (level / 3.500);
                    }

                    colideToBreakAndMoveToRight = (xBall - centerBreakX > 0);


                }
            }
        }
    }

    public void BlockCollide() {
        for (Block block : blocks) {
            int hitResult = block.checkHitToBlock(xBall, yBall);
            if (hitResult != Block.NO_HIT) {
                resetColideFlags();
                if (hitResult == Block.HIT_TOP) {
                    goDownBall = false; // Reverse the vertical direction
                }

                if(hitResult == Block.HIT_BOTTOM){
                    goDownBall = true;
                }
                if (hitResult == Block.HIT_LEFT) {
                    goRightBall = false; // Reverse the horizontal direction
                }
                if(hitResult == Block.HIT_RIGHT){
                    goRightBall = true;
                }
            }
        }
    }

    public void setPhysicsToBall(double xBreak) {
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
            if (!isGoldStauts) {
                if (heart == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                } else {
                    heartChanged = true;
                    new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, root);
                }
            }
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
    }

    public double getXball(){
     return xBall;
    }

    public double getYball(){
        return yBall;
    }


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

