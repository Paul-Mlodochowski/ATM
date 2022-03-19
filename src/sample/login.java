package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class login {
    @FXML
    TextField nr_karty;
    @FXML
    PasswordField PIN;
    @FXML
    private void zatwierdz() throws IOException {
if (DataCorrect(nr_karty.getText(),PIN.getText()))
        {

            File plik = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\logins.txt");
            Scanner scanner = new Scanner(plik);
            int numer_login;
            int pin_login;
            boolean logged = false;
            int i = 1;
            while (scanner.hasNextLine()){
                if (!(i % 2 == 0)){
                    numer_login = scanner.nextInt();
                    if (numer_login == Integer.parseInt(nr_karty.getText())) {
                        pin_login = scanner.nextInt();
                        if (pin_login == Integer.parseInt(PIN.getText())) {
                            resetInfractions();
                            Stage user = Main.getActual_stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("stan_konta.fxml"));
                            Stage stage = new Stage(StageStyle.DECORATED);
                            stage.setScene(
                                    new Scene(loader.load())
                            );
                            stan_konta controller = loader.getController();
                            controller.initData(nr_karty.getText());
                            user.setScene(stage.getScene());

                            logged = true;
                            break;

                        }
                        else {
                            CheckAndAddInfractions(nr_karty.getText());
                            changeBorderColor_RED();
                            break;
                        }
                    }


                }
                i++;
            }
            if (!logged){
                    alert();
                    changeBorderColor_RED();
            }
            scanner.close();
        }
else {
    // JAK PODANO NIEPRAWIDŁOWE DANE
    changeBorderColor_RED();
}


    }

    private void alert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Podano błędne dane, spróbuj ponownie");
        alert.setTitle("WARNING");
        alert.show();

    }
    private static boolean DataCorrect(String nr_karty, String PIN) throws FileNotFoundException {
        if (nr_karty.equals("") || PIN.equals("")){
            return false;
        }
        for (int i = 0; i<nr_karty.length();i++ ){
            if (!(nr_karty.charAt(i) >= '0' && nr_karty.charAt(i) <='9') || nr_karty.length() != 6){
                return false;
            }
        }
        for (int i = 0; i<PIN.length();i++ ){
            if (!(PIN.charAt(i) >= '0' && PIN.charAt(i) <='9')|| PIN.length() != 4){
                return false;
            }
        }
        return CheckInfractions(nr_karty);
    }
    private void changeBorderColor_RED(){
        nr_karty.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        PIN.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
    private void changeBorderColor_NONE(){ // Może kiedyś się przydać xD
        nr_karty.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY)));
        PIN.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY)));
    }

    private void CheckAndAddInfractions(String nr_karty) throws IOException {
        File plik = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\account_block.txt");
        Scanner scanner = new Scanner(plik);
        String text = "";
        String nr_karty_file;
        while (scanner.hasNextLine()){
            //skopiuj całe, znajdź,skopiuj, wklej
            nr_karty_file = scanner.nextLine();
            if (nr_karty.equals(nr_karty_file) ){
                text += nr_karty_file + "\n";
                int infractions = Integer.parseInt(scanner.nextLine());
                infractions++;
                if (infractions >= 3){
                    // WYWAL MU WARNING
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Konto zostało zablokowane");
                    alert.setTitle("WARNING");
                    alert.show();
                }
                text += infractions + "\n";
            }
            else
            text += nr_karty_file + "\n";

        }
        FileWriter pisacz = new FileWriter("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\account_block.txt");
        pisacz.write(text);
        pisacz.close();
        scanner.close();
    }
    private void resetInfractions() throws IOException {
        File plik = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\account_block.txt");
        Scanner scanner = new Scanner(plik);
        String text = "";
        String nr_karty_file;
        while (scanner.hasNextLine()){
            //skopiuj całe, znajdź,skopiuj, wklej
            nr_karty_file = scanner.nextLine();
            if (nr_karty.getText().equals(nr_karty_file) ){
                text += nr_karty_file + "\n";
                int infractions = Integer.parseInt(scanner.nextLine());
                infractions = 0; //Aby zresetować
                text += infractions + "\n";
            }
            else
                text += nr_karty_file + "\n";

        }
        FileWriter pisacz = new FileWriter("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\account_block.txt");
        pisacz.write(text);
        pisacz.close();
        scanner.close();
    }
    private static  boolean CheckInfractions(String nr_karty) throws FileNotFoundException {
        File plik = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\account_block.txt");
        Scanner scanner = new Scanner(plik);

        String nr_karty_file;
        while (scanner.hasNextLine()){
            nr_karty_file = scanner.nextLine();
            if (nr_karty.equals(nr_karty_file)){

                int infractions = Integer.parseInt(scanner.nextLine());
                if (infractions >= 3){
                    // WYWAL MU WARNING
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Konto zostało zablokowane");
                    alert.setTitle("WARNING");
                    alert.show();
                    return false;
                }
                else {
                    return true;
                }

            }

        }
        scanner.close();
        return true;
    }



}
