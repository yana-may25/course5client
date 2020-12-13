package menuAdmin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.standard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class AdminMenu extends standard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void adminPane(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("adminPane.fxml", "Управление исходными данными"); }

    @FXML
    void calcPrice(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("jewelleryPane.fxml", "Управление ассортиментом");
    }

    @FXML
    void graphPane(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("statisticsPane.fxml", "Просмотр полной статистики");
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("../sample/sample.fxml", "Авторизация"); }

    @FXML
    void manageUsers(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("userPane.fxml", "Управление пользователями");
    }

    @FXML
    void initialize() {

    }
}
