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
