module brickGame {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.datatransfer;

    opens brickGame to javafx.fxml;
    exports brickGame;
}