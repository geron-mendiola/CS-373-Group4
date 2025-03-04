module com.tictactoe.tictactoeproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.tictactoe.tictactoeproject to javafx.fxml;
    exports com.tictactoe.tictactoeproject;
}