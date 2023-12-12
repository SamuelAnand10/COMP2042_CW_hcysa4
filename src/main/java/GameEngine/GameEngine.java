package GameEngine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * The `GameEngine` class manages the game loop and provides methods for starting and stopping
 * the game. It utilizes a scheduler for updating and running physics at a fixed rate.
 * @see <a href="...GameEngine/GameEngine.java">Original Source Code</a>
 */
public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private ScheduledExecutorService scheduler;
    private boolean isStopped = true;
    /**
     * Sets the listener for various game actions.
     * @see #setOnAction(OnAction)
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }
    /**
     * Sets the frames per second (fps) and converts it to milliseconds.
     * @param fps;
     *           @see #setFps(int)
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }
    /**
     * Updates the game loop using a scheduler for rendering and physics updates.
     *@see #update()
     */
    private void update() {
        scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> onAction.onUpdate(), 0, fps, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(() -> onAction.onPhysicsUpdate(), 0, fps, TimeUnit.MILLISECONDS);
    }//added a scheduler
    /**
     * Initializes the game by calling the `onInit` method of the listener.
     * @see #initialize()
     */
    private void initialize() {
        onAction.onInit();
    }
    /**
     * Starts the game loop, initializes the game, and starts the time tracking.
     * @see #start()
     */
    public void start() {
        time = 0;
        initialize();
        update();
        timeStart();
        isStopped = false;
    }
    /**
     * Stops the game loop, shuts down the scheduler, and interrupts the time tracking thread.
     * @see #stop()
     */
    public void stop() {
        if (!isStopped) {
            isStopped = true;
            scheduler.shutdownNow();
            timeThread.interrupt();
        }
    }

    private long time = 0;
    private Thread timeThread;
    /**
     * Starts a separate thread to track game time and calls the `onTime` method of the listener.
     * @see #timeStart()
     */
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
    /**
     * The `OnAction` interface defines methods that can be implemented by the listener to
     * respond to various game events.
     * @see #onAction
     */
    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}
