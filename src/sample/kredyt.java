package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.ImageView;
import javax.xml.crypto.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class kredyt {
    @FXML
    TextField kredyt_field;
    @FXML
    Node zdj;
    String previousColor;
    String nr_konta;

    public void initdate(String n){
        nr_konta = n;
    }

    @FXML
    public void zatwierdz() throws IOException {
        if (DataCorrect(kredyt_field.getText())){
           File plik = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\klient.txt");
           Scanner scanner = new Scanner(plik);
           String text = "";
           String numer_karty_file = "";
          boolean  is_found = false;
            while (scanner.hasNextLine()) {
                numer_karty_file = scanner.nextLine();
                text += numer_karty_file + "\n";
                if (numer_karty_file.equals(nr_konta)) {
                    text += scanner.nextLine() + "\n";
                    String hismoney = scanner.nextLine();
                    text += Double.parseDouble(hismoney) + Double.parseDouble(kredyt_field.getText()) + "\n";
                    is_found = true;
                    break; // aby nie było że nam źle czyta dane
                }
                text += scanner.nextLine() + "\n"; //dwa razy gdyż trzeba pominąć
                text += scanner.nextLine() + "\n";
            }
            while (scanner.hasNextLine() && is_found) {
                text += scanner.nextLine() + "\n";
            }
            scanner.close();
           FileWriter fileWriter = new FileWriter(plik);
            fileWriter.write(text);
            fileWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Node forAlert = zdj;
            forAlert.setOpacity(1);
            alert.setGraphic(forAlert);
            alert.setTitle("Potwierdzenie wykonania K r e d y t u");
            alert.setHeaderText("Poprawnie wykonano K r e d y t");
            alert.show();
        }
    }
    @FXML
    public void cofnij() throws IOException {
        Stage user = Main.getActual_stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("stan_konta.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );
        stan_konta controller = loader.getController();
        controller.initData(nr_konta);
        user.setScene(stage.getScene());
    }
    @FXML
    public void  Entered(Event event){
        Button button = (Button) event.getSource();
        previousColor = button.getStyle();
        button.setStyle("-fx-background-color:#46F5A5;");
    }
    @FXML
    public void Exited(Event event){
        Button button = (Button) event.getSource();
        button.setStyle(previousColor);
    }
    private static boolean DataCorrect(String money){
        if (money.equals("")){
            return false;
        }
        for (int i = 0; i<money.length();i++ ){
            if (!(money.charAt(i) >= '0' && money.charAt(i) <='9')){
                return false;
            }
        }
        return true;
    }
}
