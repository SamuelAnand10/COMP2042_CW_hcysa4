package brickGame;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private ScheduledExecutorService scheduler;
    private boolean isStopped = true;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and convert it to milliseconds
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    private void update() {
        scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> onAction.onUpdate(), 0, fps, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(() -> onAction.onPhysicsUpdate(), 0, fps, TimeUnit.MILLISECONDS);
    }//added a scheduler

    private void initialize() {
        onAction.onInit();
    }

    public void start() {
        time = 0;
        initialize();
        update();
        timeStart();
        isStopped = false;
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            scheduler.shutdownNow();
            timeThread.interrupt();
        }
    }

    private long time = 0;
    private Thread timeThread;

    private void timeStart() {
        timeThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    time++;
                    onAction.onTime(time);
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();//handles interrupt now
            }
        });
        timeThread.start();
    }

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}
