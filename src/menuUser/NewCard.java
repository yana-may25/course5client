package menuUser;

import Client.Client;
import classes.ClientCard;
import classes.standard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewCard extends standard {

        Client cl = Client.getInstance();

        static String unumber ="";

        public static String getPhone(){
                return unumber;
        }
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField name;

        @FXML
        private TextField surname;

        @FXML
        private TextField phoneNumber;

        @FXML
        void back(MouseEvent event) throws IOException {
                ((Node)event.getSource()).getScene().getWindow().hide();
                loadWindow("../menuUser/Second.fxml","Меню продавца");
        }

        @FXML
        void continueBuy(MouseEvent event) throws IOException {
                cl.sendMessage("addClient");
                ClientCard user = new ClientCard(name.getText(), surname.getText(),
                        phoneNumber.getText());
                unumber = phoneNumber.getText();
                cl.sendObject(user);
                Alert alert = new Alert (Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Новый пользователь добавлен!");
                alert.showAndWait();
                ((Node)event.getSource()).getScene().getWindow().hide();
                loadWindow("../menuUser/purchaseItem.fxml","Оформление покупки");
        }

        @FXML
        void initialize() { }


}
