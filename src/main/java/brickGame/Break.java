package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Break extends Main {
    private double xBreak = 0.0f;
    public void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));

        rect.setFill(pattern);
    }

    public Rectangle returnRect(){
        return rect;
    }//new class

    public double move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                        return;
                    }
                    else if (xBreak == 0 && direction == LEFT) {
                        return;
                    }
                    else if (direction == RIGHT) {
                        xBreak++;

                    }
                    else if(direction == LEFT){
                        xBreak--;

                    }//added if
                    else{
                        return;
                    }
                    centerBreakX = xBreak + halfBreakWidth;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();//moved function
        return xBreak;
    }



}
