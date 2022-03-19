package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


public class Controller {


    @FXML
    private void goToLogin() throws IOException {
       Stage login = Main.getActual_stage();
       Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
       login.setScene(new Scene(root,700,460));
    }

}
