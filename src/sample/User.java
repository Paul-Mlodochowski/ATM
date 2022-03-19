package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class User {
    private BigDecimal cash;
    private String Name;
    private String SurName;
    public Label label;
    private String nr_konta;

    public User() {
    }

    public Label getLabel() {
        return label;
    }
    public void clear(){
        this.nr_konta = null;
        this.cash = null;
        Name = null;
        nr_konta= "";
    }


    public void User_set(BigDecimal cash, String name, String surName,String nr_konta) {
        this.nr_konta = nr_konta;
        this.cash = cash;
        Name = name;
        SurName = surName;

    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public String getName() {
        return Name;
    }


    public String getSurName() {
        return SurName;
    }

    public void substract(BigDecimal amount) throws IOException {
      if(amount.floatValue() > 0){
          this.cash = this.cash.subtract(amount);
          setFile();
      }

      else {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setContentText("Nie można podać negatywnej wartości");
          alert.setTitle("Information");
          alert.showAndWait();
      }
    }

    public void setData(String nr_konta){
        File file = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\klient.txt");
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                if(nr_konta.equals(scanner.nextLine())){
                        String tmp = scanner.nextLine();
                        String[] NameAndSurname = tmp.split(" ");
                        if(NameAndSurname.length > 2){ // Jeżeli użytkownik ma dwu członowe nazwizsko
                            for (int i = 2 ; i < NameAndSurname.length; i++)
                                NameAndSurname[1] += " " + NameAndSurname[i];
                        }
                        User_set(BigDecimal.valueOf(Double.parseDouble(scanner.nextLine())),NameAndSurname[0],NameAndSurname[1],nr_konta);
                        break;
                }
                else{
                    scanner.nextLine();
                    scanner.nextLine();
                }

            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void setFile() throws IOException {
        File plik = new File("C:\\Users\\PAWEŁ\\IdeaProjects\\BankomatFX\\src\\sample\\data\\klient.txt");
        Scanner scanner = new Scanner(plik);
        String text = "";
        String numer_karty_file;
        boolean is_found = false;
        while (scanner.hasNextLine()){
            numer_karty_file = scanner.nextLine();
            text += numer_karty_file + "\n";
            if (numer_karty_file.equals(this.nr_konta)){
               text+= scanner.nextLine() + "\n";
               scanner.nextLine();
                text += getCash() + "\n";
                is_found = true;
                break; // aby nie było że nam źle czyta dane
            }
            text += scanner.nextLine() + "\n"; //dwa razy gdyż trzeba pominąć
            text += scanner.nextLine() + "\n";
        }
        while (scanner.hasNextLine() && is_found){
            text+= scanner.nextLine() + "\n";
        }
        scanner.close();
        FileWriter fileWriter = new FileWriter(plik);
        fileWriter.write(text);
        fileWriter.close();



    }

}
