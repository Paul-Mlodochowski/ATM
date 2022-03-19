package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class stan_konta {

    String previousColor = "";
    @FXML
    TextField wyplac_user_choice;
    @FXML
    Label personal_data;
    @FXML
    TextField user_money;
    @FXML
    Button zatwierdz;

    private final User user = new User();
    private String nr_konta_private;


    @FXML
    public void Wyloguj() throws IOException {
        user.clear();
        Stage login = Main.getActual_stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        login.setScene(new Scene(root,700,400));
    }

    void initData(String nr_konta){
        nr_konta_private = nr_konta;
        user.setData(nr_konta);
        personal_data.setText(user.getName() + " " + user.getSurName());
        user_money.setText(user.getCash() +" $");

    }
    @FXML
    public void przelew() throws IOException {
        Stage newstage = Main.getActual_stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("przelew.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load(),600,400)
        );
        przelew controller = loader.getController();
        controller.initData(user.getName()+" "+user.getSurName(),nr_konta_private);
        newstage.setScene(stage.getScene());
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
    @FXML
    public void clicked(Event event) throws IOException {
        Button button = (Button) event.getSource();
        if (button.getText().length() < 5)
       user.substract(BigDecimal.valueOf(Integer.parseInt(button.getText().substring(0,3))));
        else
            user.substract(BigDecimal.valueOf(Integer.parseInt(button.getText().substring(0,4))));
        user_money.setText(String.valueOf(user.getCash()) + " $");

    }
    @FXML
    public void checkin(){
            zatwierdz.setOpacity(1);
    }
    @FXML
    public void zatwierdz_action() throws IOException {
    if (DataCorrect(wyplac_user_choice.getText())){
           if (BigDecimal.valueOf(Double.parseDouble(wyplac_user_choice.getText())).floatValue() <= user.getCash().floatValue()){
               if (Double.parseDouble(wyplac_user_choice.getText()) % 10 == 0) {
                   user.substract(BigDecimal.valueOf(Double.parseDouble(wyplac_user_choice.getText())));
                   user_money.setText(String.valueOf(user.getCash()) + " $");
               }
               else {
                   alerting("Bankomat wydaje tylko banknoty");
               }
           }
           else {
               alerting("Nie masz wystarczająco środókw na koncie");
           }

        }
        else {
            alerting("Podano złe dane");
        }
    }
    @FXML
    public void Izrael() throws IOException {
        Stage newStage = Main.getActual_stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(" kredyt.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
       stage.setScene(
               new Scene(loader.load()));
        kredyt controller = loader.getController();
        controller.initdate(nr_konta_private);
        newStage.setScene(stage.getScene());

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
    private static void alerting(String header){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(header);
        alert.show();
    }


}
