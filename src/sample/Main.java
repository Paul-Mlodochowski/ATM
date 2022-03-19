package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage actual_stage;

    public static Stage getActual_stage() {
        return actual_stage;
    }

    public void setActual_stage(Stage actual_stage) {
        this.actual_stage = actual_stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Bankomat $$$");
        primaryStage.setScene(new Scene(root, 700, 600));
        setActual_stage(primaryStage);
        primaryStage.show();
    }
    @Override
    public void init(){
        System.out.println("Łączenie z bazą danych");
    }
    @Override
    public void stop(){
        System.out.println("Zamykanie połączenia z bazą danych");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
