package menuUser;

import Client.Client;
import classes.standard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import sample.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Second extends standard {
    private Client cl = Client.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text hello;

    @FXML
    void showProducts(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("jewelleryPaneUser.fxml", "Просмотр изделий");
    }

    @FXML
    void setPurchase(MouseEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        loadWindow ("purchaseItem.fxml","Оформление покупки");

    }

    @FXML
    void back(MouseEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        loadWindow ("../sample/sample.fxml","Авторизация");
    }

    @FXML
    void addClient(MouseEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        loadWindow ("../menuUser/newCard.fxml","Завести карту клиента");
    }

    @FXML
    void initialize() {
        String user= Controller.getVariable();
        hello.setText("Вы вошли под пользователем!\nВаш логин - "+user);
    }


}

