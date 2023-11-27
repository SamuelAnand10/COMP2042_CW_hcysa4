package brickGame;

public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private Thread updateThread;
    private Thread physicsThread;
    public boolean isStopped = true;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and convert it to milliseconds
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    private synchronized void update() {
        updateThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    onAction.onUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();//handles interrupt now
                }
            }
        });
        updateThread.start();
    }

    private void initialize() {
        onAction.onInit();
    }

    private synchronized void physicsCalculation() {
        physicsThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    onAction.onPhysicsUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();//handles interrupt now
                }
            }
        });

        physicsThread.start();
    }

    public void start() {
        time = 0;
        initialize();
        update();
        physicsCalculation();
        timeStart();
        isStopped = false;
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            updateThread.interrupt();
            physicsThread.interrupt();
            timeThread.interrupt();
        }
    }

    private long time = 0;

    private Thread timeThread;

    private void timeStart() {
        timeThread = new Thread(() -> {
            try {
                while (true) {
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
