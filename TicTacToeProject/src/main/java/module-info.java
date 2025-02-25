module com.tictactoe.tictactoeproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tictactoe.tictactoeproject to javafx.fxml;
    exports com.tictactoe.tictactoeproject;
}