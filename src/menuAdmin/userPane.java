package menuAdmin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Client;
import classes.Users;
import classes.standard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import validation.Validation;

public class userPane extends standard {
    Validation v = new Validation();
    Client cl = Client.getInstance();
    static ObservableList<Users> list = FXCollections.observableArrayList();
    private ObservableList<String> editChoices = FXCollections.observableArrayList();
    int selectedUser;

    ArrayList<Users> userList = new ArrayList<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField passport;

    @FXML
    private TextField surname;

    @FXML
    private TextField name;

    @FXML
    private TextField login;

    @FXML
    private Button add;


    @FXML
    private PasswordField passwordTfield;

    @FXML
    private PasswordField passwordTfield1;

    @FXML
    private TableView<Users> table1;

    @FXML
    private TableColumn<?, ?> iduser1;

    @FXML
    private TableColumn<?, ?> login1;

    @FXML
    private TableColumn<Users, String> name1;

    @FXML
    private TableColumn<?, ?> surname1;

    @FXML
    private TableColumn<?, ?> passport1;

    @FXML
    private Button delete;

    @FXML
    private Button update;

    @FXML
    private ComboBox<String> editChoiceBox;

    @FXML
    private TextField editField;


    @FXML
    private AnchorPane hiddenPane;


    @FXML
    void clear(MouseEvent event) {
        editField.clear();
    }

    @FXML
    void confirm(MouseEvent event) {
        String a = editChoiceBox.getSelectionModel().getSelectedItem();
        selectedUser = table1.getSelectionModel().getSelectedItem().getIdUser();
        if (v.hasCorrectName(editField)&&(a.equals("имя")|| a.equals("фамилия"))) {
            cl.sendMessage("updUsers");
            cl.sendObject(selectedUser);
            cl.sendMessage(editField.getText());
            cl.sendMessage(a);
            editField.clear();
            hiddenPane.setVisible(false);
            loadTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Имя и Фамилия должны состоять из букв русского алфавита!");
            alert.showAndWait();
        }
    }

    @FXML
    void update(MouseEvent event) {
        hiddenPane.setVisible(true);

    }

    public void loadTable() {
        list.clear();
        cl.sendMessage("showUsers");

        userList = (ArrayList<Users>) Client.getInstance().readObject();
        System.out.println(userList.size());
        list.addAll(userList);

        iduser1.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        login1.setCellValueFactory(new PropertyValueFactory<>("login"));
        name1.setCellValueFactory(new PropertyValueFactory<Users, String>("name"));
        surname1.setCellValueFactory(new PropertyValueFactory<>("surname"));
        passport1.setCellValueFactory(new PropertyValueFactory<>("passportNumber"));

        table1.setItems(null);
        table1.setItems(list);
    }

    @FXML
    void initialize() throws IOException {
        hiddenPane.setVisible(false);
        loadTable();

        editChoices.addAll("логин", "имя", "фамилия", "номер паспорта", "пароль");
        editChoiceBox.setItems(editChoices);
        editChoiceBox.setValue("логин");

        add.setOnAction(event -> {

            String field1 = passwordTfield.getText();
            String field2 = passwordTfield1.getText();
            String loginS = login.getText();
            String passwordS = passwordTfield.getText();
            String nameS = name.getText();
            String surnameS = surname.getText();
            String passportS = passport.getText();
            if (field1.isEmpty() || field2.isEmpty() || loginS.isEmpty() || passportS.isEmpty() || nameS.isEmpty()
                    || surnameS.isEmpty() || passportS.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Заполните все поля!");
                alert.showAndWait();
            }
            else
            {
                if (field1.equals(field2)) {
                    cl.sendMessage("addUser");
                    Users user = new Users(loginS, passwordS,
                            nameS, surnameS, passportS, false);
                    cl.sendObject(user);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Новый пользователь добавлен!");
                    alert.showAndWait();
                    loadTable();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Пароли не совпадают!");
                    alert.showAndWait();

                }
            }
        });

        delete.setOnAction(event -> {

            cl.sendMessage("deleteUser");
            cl.sendObject(table1.getSelectionModel().getSelectedItem());
            list.remove(table1.getSelectionModel().getSelectedItem());

        });
    }

    @FXML
    void back(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        try {
            loadWindow("adminMenu.fxml", "Меню администратора");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;

}
