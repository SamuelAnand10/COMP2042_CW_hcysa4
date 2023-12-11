module brickGame {

    requires javafx.controls;
    requires java.datatransfer;

    requires javafx.graphics;

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports BlockLogic;
    opens BlockLogic to javafx.fxml;
    exports Ball_Init;
    opens Ball_Init to javafx.fxml;
    exports GameEngine;
    opens GameEngine to javafx.fxml;
    exports Breaker;
    opens Breaker to javafx.fxml;
    exports CollisionLogic;
    opens CollisionLogic to javafx.fxml;
    exports SavingLoadingLogic;
    opens SavingLoadingLogic to javafx.fxml;
    exports BoardLogic;
    opens BoardLogic to javafx.fxml;
    exports BonusLogic;
    opens BonusLogic to javafx.fxml;
    exports Score;
    opens Score to javafx.fxml;
}