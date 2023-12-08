package brickGame;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * The Score class provides methods for displaying score-related messages and animations
 * within the Brick Game application. It extends the functionality of the Main class to handle
 * UI elements such as labels and buttons for various in-game situations.
 */
public class Score extends Main {

    // Method to show a score label with animation
    /**
     * Displays a score label with animation at the specified position on the screen.
     *
     * @param x     The X-coordinate for the label.
     * @param y     The Y-coordinate for the label.
     * @param score The score value to be displayed.
     * @param root  The JavaFX Pane to which the label will be added.
     */
    public void show(final double x, final double y, int score, Pane root) {
        // Determine sign based on the score
        String sign = (score > 0) ? "+" : "";
        // Create a label with the score and set its initial position
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        // Run on JavaFX thread to update UI
        Platform.runLater(() -> root.getChildren().add(label));

        // Create and play a fade transition for the label
        FadeTransition fade = new FadeTransition(Duration.millis(1000), label);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setCycleCount(1);
        fade.play();

        // Create and play a scale transition for the label
        ScaleTransition scale = new ScaleTransition(Duration.millis(1000), label);
        scale.setToX(2.0);
        scale.setToY(2.0);
        scale.setCycleCount(1);
        scale.play();
    }

    // Method to show a message label with animation
    /**
     * Displays a message label with animation at a predefined position on the screen.
     *
     * @param message The message to be displayed.
     * @param root    The JavaFX Pane to which the label will be added.
     */
    public void showMessage(String message, Pane root) {
        // Create a label with the message and set its initial position
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        // Run on JavaFX thread to update UI
        synchronized (root){
        Platform.runLater(() -> root.getChildren().add(label));}

        // Create and play a fade transition for the label
        FadeTransition fade = new FadeTransition(Duration.millis(1000), label);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setCycleCount(1);
        fade.play();

        // Create and play a scale transition for the label
        ScaleTransition scale = new ScaleTransition(Duration.millis(1000), label);
        scale.setToX(2.0);
        scale.setToY(2.0);
        scale.setCycleCount(1);
        scale.play();
    }

    // Method to show a game over message with a restart button
    /**
     * Displays a "Game Over" message with a restart button.
     *
     * @param main The Main instance to access game-related functions.
     */
    public void showGameOver(final Main main) {
        // Run on JavaFX thread to update UI
        Platform.runLater(() -> {
            // Create a "Game Over" label
            Image gamewinImage = new Image("champion - Copy.png");
            ImageView gamewin = new ImageView(gamewinImage);
            gamewin.setX(50);
            gamewin.setY(100);

            gamewin.setFitHeight(500);
            gamewin.setFitWidth(400);


            // Create a restart button
            Image restartImage = new Image("restart.png");
            ImageView restart = new ImageView(restartImage);
            restart.setFitHeight(50);
            restart.setFitWidth(150);
            restart.setTranslateX(175);
            restart.setTranslateY(400);
            restart.setOnMouseClicked(event -> main.restartGame());


            // Add the label to the root
            main.root.getChildren().addAll(gamewin, restart);
        });
    }

    // Method to show a "You Win" message
    /**
     * Displays a "You Win" message with a restart button.
     *
     * @param main The Main instance to access game-related functions.
     */
    public void showWin(final Main main) {
        // Run on JavaFX thread to update UI
        Platform.runLater(() -> {
            // Create a "You Win" label
            Image gamewinImage = new Image("Blue and Red Modern Illustration Game Mouse Pad.png");
            ImageView gamewin = new ImageView(gamewinImage);
            gamewin.setX(50);
            gamewin.setY(100);

            gamewin.setFitHeight(500);
            gamewin.setFitWidth(400);


            // Create a restart button
            Image restartImage = new Image("restart.png");
            ImageView restart = new ImageView(restartImage);
            restart.setFitHeight(50);
            restart.setFitWidth(150);
            restart.setTranslateX(200);
            restart.setTranslateY(350);
            restart.setOnMouseClicked(event -> main.restartGame());


            // Add the label to the root
            main.root.getChildren().addAll(gamewin, restart);
        });
    }
}
