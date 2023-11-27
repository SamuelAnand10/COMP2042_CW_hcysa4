package brickGame;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Score extends Main {

    // Method to show a score label with animation
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
    public void showGameOver(final Main main) {
        // Run on JavaFX thread to update UI
        Platform.runLater(() -> {
            // Create a "Game Over" label
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            // Create a restart button
            Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction(event -> main.restartGame());

            // Add the label and button to the root
            main.root.getChildren().addAll(label, restart);
        });
    }

    // Method to show a "You Win" message
    public void showWin(final Main main) {
        // Run on JavaFX thread to update UI
        Platform.runLater(() -> {
            // Create a "You Win" label
            Label label = new Label("You Win :)");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            // Add the label to the root
            main.root.getChildren().addAll(label);
        });
    }
}
