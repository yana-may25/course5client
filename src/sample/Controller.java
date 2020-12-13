package sample;

import Client.Client;
import classes.standard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.Node;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends standard {
    static ObservableList<String> list = FXCollections.observableArrayList();
    String username = "user";
    String password = "pass";

    static String uname="";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login;

    @FXML
    private PasswordField pass;
    @FXML
    void enter1(MouseEvent event) throws IOException {
        Client cl = Client.getInstance();
        cl.sendMessage("enter");
        String login1=login.getText();
        String pass1=pass.getText();
        if (login1.isEmpty()|| pass1.isEmpty())
        {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Заполните все поля!");
            alert.showAndWait();
        }
        else {
            cl.sendMessage(login1);
            cl.sendMessage(pass1);
            String mes=cl.readMessage();
            if(mes.equals("Yes1")){
                Controller.uname=login1;
                ((Node)event.getSource()).getScene().getWindow().hide();
                loadWindow("../menuAdmin/adminMenu.fxml","Title");
            }
            else if(mes.equals("Yes0")){
                Controller.uname=login1;
                ((Node)event.getSource()).getScene().getWindow().hide();
                loadWindow("../menuUser/Second.fxml","Title");
            }
            else{

                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Неверный логин или пароль");
                alert.showAndWait();
            }

        }
//        if(login1.equals(username)&&pass1.equals(password))
//        {
//
//
//            Controller.uname=login1;
//            ((Node)event.getSource()).getScene().getWindow().hide();
//            loadWindow("second.fxml","Title");
//
//        }
//        else{
//            Alert alert = new Alert (Alert.AlertType.ERROR);
//            alert.setHeaderText(null);
//            alert.setContentText("Invalid pass or login");
//            alert.showAndWait();
//
//        }

    }

    public static String getVariable(){
        return uname;
    }

    public static void setVariable(){
        uname = list.get(0);
        Controller.uname = uname;
    }


    @FXML
    void initialize() {


    }


}
