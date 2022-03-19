package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class przelew {
    @FXML
    Node zdj;
    @FXML
    Label label;
    @FXML
    TextField nr_konta_foreign;
    @FXML
    TextField amount;

    String previousColor = "";
    String our_nr_konta;

    public void initData(String name_surname,String nr){
    label.setText(name_surname);
    our_nr_konta = nr;
    }
    @FXML
    public void zatwierdz() throws IOException {
        String data[] = {nr_konta_foreign.getText(),amount.getText()};
        if (DataCorrect(data)) {
            boolean is_not_enought = false;
            //OD zabrania
            File plik = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\klient.txt");
            Scanner scanner = new Scanner(plik);
            String text = "";
            String numer_karty_file;
            boolean is_found = false;
            while (scanner.hasNextLine()) {
                numer_karty_file = scanner.nextLine();
                text += numer_karty_file + "\n";
                if (numer_karty_file.equals(our_nr_konta)) {
                    text += scanner.nextLine() + "\n";
                    String money = scanner.nextLine();
                    if (Double.parseDouble(money) - Double.parseDouble(amount.getText()) <= 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Nie masz wystarczającej ilości pieniędzy");
                        alert.show();
                        is_found = true;
                        is_not_enought = true;
                        break;
                    }
                    text += Double.parseDouble(money) - Double.parseDouble(amount.getText()) + "\n";
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
            //Do zabrania

            //OD dodanie
            if (!is_not_enought) {
                plik = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\klient.txt");
                scanner = new Scanner(plik);
                text = "";
                numer_karty_file = "";
                is_found = false;
                while (scanner.hasNextLine()) {
                    numer_karty_file = scanner.nextLine();
                    text += numer_karty_file + "\n";
                    if (numer_karty_file.equals(nr_konta_foreign.getText())) {
                        text += scanner.nextLine() + "\n";
                        String Lastmoney = scanner.nextLine();
                        text += Double.parseDouble(Lastmoney) + Double.parseDouble(amount.getText()) + "\n";
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
                fileWriter = new FileWriter(plik);
                fileWriter.write(text);
                fileWriter.close();
                //DO
            }
            nr_konta_foreign.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY)));
            amount.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY)));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Node forAlert = zdj;
            forAlert.setOpacity(1);
            alert.setGraphic(forAlert);
            alert.setTitle("Potwierdzenie wykonania przelewu");
            alert.setHeaderText("Poprawnie wykonano przelewu");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nie wykonanano przelewu");
            alert.setHeaderText("Nie wykonano poprawnie wykonano przelewu. Poraw dane");
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
        controller.initData(our_nr_konta);
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
    private  boolean DataCorrect(String[]data) {
        if (data[0].equals("") || data[1].equals("") || data[0].length() != 6){
            nr_konta_foreign.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            return false;
        }
        for (String x:data) {

        for (int i = 0; i<x.length();i++ ){
            if (!(x.charAt(i) >= '0' && x.charAt(i) <='9')){
                nr_konta_foreign.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                amount.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                return false;
            }
        }

        }

        return true;
    }
}
