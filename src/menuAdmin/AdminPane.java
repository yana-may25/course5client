package menuAdmin;

import java.io.IOException;
import java.util.ArrayList;

import Client.Client;
import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import validation.Validation;

public class AdminPane extends standard {
    static final String REGEX_NAME = "^([А-Я]{1}[а-яё]{1,}|[A-Z]{1}[a-z]{1,})+$";

    Client cl = Client.getInstance();
    static ObservableList<Material> list = FXCollections.observableArrayList();
    static ArrayList<Material> materialArrayList = new ArrayList<>();
    static ObservableList<JCollection> list1 = FXCollections.observableArrayList();
    static ArrayList<JCollection> collectionsArrayList = new ArrayList<>();
    static ArrayList<ClientCard> clientsArrayList = new ArrayList<>();
    static ObservableList<ClientCard> list2 = FXCollections.observableArrayList();

    static ArrayList<Discount> discountsArrayList = new ArrayList<>();
    static ObservableList<Discount> dList = FXCollections.observableArrayList();


    @FXML
    private TableView<Material> table1;

    @FXML
    private TableColumn<?, ?> idmaterial;

    @FXML
    private TableColumn<Material, String> name;

    @FXML
    private TableColumn<?, ?> type;

    @FXML
    private TableColumn<Material, String> price;

    @FXML
    private TextField nameAdd;

    @FXML
    private TextField priceAdd;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private ChoiceBox<?> sort;

    @FXML
    private ChoiceBox<String> chooseType;

    @FXML
    private Label text;

    @FXML
    private TextField newValue;

    @FXML
    private Button editButton1;

    @FXML
    private TableView<JCollection> table2;

    @FXML
    private TableColumn<?, ?> idcollection;

    @FXML
    private TableColumn<JCollection, String> name1;

    @FXML
    private TableColumn<?, ?> type1;

    @FXML
    private TableColumn<JCollection, Double> price1;

    @FXML
    private TextField nameAdd1;

    @FXML
    private TextField priceAdd1;

    @FXML
    private Button editButton3;

    @FXML
    private Button deleteButton1;

    @FXML
    private Button addButton1;

    @FXML
    private ChoiceBox<?> sort1;

    @FXML
    private ChoiceBox<String> chooseType1;

    @FXML
    private Label text1;

    @FXML
    private TextField newValue1;

    @FXML
    private Button editButton4;
    @FXML
    private TableView<ClientCard> table3;

    @FXML
    private TableColumn<?, ?> idclientColomn;

    @FXML
    private TableColumn<?, ?> clientNameColomn;

    @FXML
    private TableColumn<?, ?> surnameColomn;

    @FXML
    private TableColumn<?, ?> phoneColomn;

    @FXML
    private TableColumn<?, ?> purchNumColomn;

    @FXML
    private TableColumn<?, ?> discountColomn;
    @FXML
    private ChoiceBox<?> sort3;

    @FXML
    private TableView<Discount> table31;

    @FXML
    private TableColumn<?, ?> purchNumColomn1;


    @FXML
    private TableColumn<Discount, String> discountColomn1;

    @FXML
    private ChoiceBox<?> numPurchCBox;

    @FXML
    private ChoiceBox<?> discountCBox;

    @FXML
    private TableColumn<?, ?> clientsNumColomn;

    @FXML
    private ChoiceBox<String> filterMaterialsBox;


    @FXML
    void back(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("adminMenu.fxml", "Title");
    }

    @FXML
    void back1(MouseEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Изменения сохранены!");
        alert.showAndWait();
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("adminMenu.fxml", "Title");
    }

    @FXML
    void add(MouseEvent event) {
        if (nameAdd.getText().matches(REGEX_NAME)) {
            cl.sendMessage("addMaterial");
            Material material = new Material(nameAdd.getText(), chooseType.getValue(), Double.parseDouble(priceAdd.getText()));
            cl.sendObject(material);
            table1.getItems().clear();
            showMaterials();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Допускается использование только букв русского алфавита в названии!");
            alert.showAndWait();
        }

    }

    @FXML
    void add1(MouseEvent event) {
        if (nameAdd1.getText().matches(REGEX_NAME)) {

        cl.sendMessage("addCollection");
        JCollection material = new JCollection(nameAdd1.getText(),
                Integer.parseInt(chooseType1.getValue()),
                Double.parseDouble(priceAdd1.getText()));
        cl.sendObject(material);
        table2.getItems().clear();
        showCollections();}
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Допускается использование только букв русского алфавита!");
            alert.showAndWait();
        }


    }

    @FXML
    void delete(MouseEvent event) {

        cl.sendMessage("deleteMaterial");
        cl.sendObject(table1.getSelectionModel().getSelectedItem());
        list.remove(table1.getSelectionModel().getSelectedItem());

    }

    @FXML
    void delete1(MouseEvent event) {

        cl.sendMessage("deleteCollection");
        cl.sendObject(table2.getSelectionModel().getSelectedItem());
        list1.remove(table2.getSelectionModel().getSelectedItem());

    }

    @FXML
    void update(MouseEvent event1) {
        text.setVisible(true);
        newValue.setVisible(true);
        editButton1.setVisible(true);
    }

    @FXML
    void update1(MouseEvent event) {
        text1.setVisible(true);
        newValue1.setVisible(true);
        editButton4.setVisible(true);
    }

    @FXML
    void saveUpdate(MouseEvent event) {
        Material selMaterial = table1.getSelectionModel().getSelectedItem();
        selMaterial.setValue(Double.parseDouble(newValue.getText()));
        cl.sendMessage("updateMaterial");
        cl.sendObject(selMaterial.getId());
        cl.sendObject(Double.parseDouble(newValue.getText()));
        table1.getItems().clear();
        showMaterials();

    }

    @FXML
    void saveUpdate1(MouseEvent event) {
        JCollection selMaterial = table2.getSelectionModel().getSelectedItem();
        Double ind = Double.parseDouble(newValue1.getText());
        cl.sendMessage("updateCollection");
        cl.sendObject(selMaterial.getId());
        cl.sendObject(ind);
        table2.getItems().clear();
        showCollections();

    }

    public void showMaterials() {
        cl.sendMessage("showMaterials");
        materialArrayList = (ArrayList<Material>) cl.readObject();
        list.addAll(materialArrayList);
        idmaterial.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Material, String>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<Material, String>("value"));
        table1.setItems(null);
        table1.setItems(list);
    }

    public void showCollections() {
        cl.sendMessage("showCollections");
        collectionsArrayList = (ArrayList<JCollection>) cl.readObject();
        list1.addAll(collectionsArrayList);
        idcollection.setCellValueFactory(new PropertyValueFactory<>("id"));
        name1.setCellValueFactory(new PropertyValueFactory<JCollection, String>("name"));
        type1.setCellValueFactory(new PropertyValueFactory<>("year"));
        price1.setCellValueFactory(new PropertyValueFactory<JCollection, Double>("index"));
        table2.setItems(null);
        table2.setItems(list1);
    }

    public void showClients() {
        cl.sendMessage("showClients");
        clientsArrayList = (ArrayList<ClientCard>) cl.readObject();
        list2.addAll(clientsArrayList);
        idclientColomn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientNameColomn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColomn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        phoneColomn.setCellValueFactory(new PropertyValueFactory<>("number"));
        purchNumColomn.setCellValueFactory(new PropertyValueFactory<>("purchases"));

        table3.setItems(null);
        table3.setItems(list2);
    }

    public void showDiscounts() {
        cl.sendMessage("showDiscRate");
        discountsArrayList = (ArrayList<Discount>) cl.readObject();
        dList.addAll(discountsArrayList);
        purchNumColomn1.setCellValueFactory(new PropertyValueFactory<>("purchNum"));
        discountColomn1.setCellValueFactory(new PropertyValueFactory<>("rate"));
        table31.setItems(dList);


    }

    @FXML
    void initialize() {

        showMaterials();
        showCollections();
        showClients();
        showDiscounts();
        text.setVisible(false);
        newValue.setVisible(false);
        editButton1.setVisible(false);
        text1.setVisible(false);
        newValue1.setVisible(false);
        editButton4.setVisible(false);
        discountColomn1.setEditable(true);
        discountColomn1.setCellFactory(TextFieldTableCell.forTableColumn());
        discountColomn1.setOnEditCommit((TableColumn.CellEditEvent<Discount, String> event) -> {
            TablePosition<Discount, String> pos = event.getTablePosition();
            int newRate = Integer.parseInt((event.getNewValue()));
            int row = pos.getRow();
            Discount discount = event.getTableView().getItems().get(row);

            cl.sendMessage("updDiscRate");
            discount.setRate(String.valueOf(newRate));
            cl.sendObject(table31.getSelectionModel().getSelectedItem().getPurchNum());
            cl.sendObject(newRate);

        });

        //-----------

        ObservableList<String> list1 = FXCollections.observableArrayList("основа", "вставки");
        chooseType.setItems(list1);
        chooseType.setValue("основа");
        ObservableList<String> list2 = FXCollections.observableArrayList("2019", "2020", "2021");
        chooseType1.setItems(list2);
        chooseType1.setValue("2020");
        filterMaterialsBox.setItems(list1);
        filterMaterialsBox.setOnAction(event -> {
            list.clear();
            showMaterials();
            FilteredList<Material> filteredData = new FilteredList<>(table1.getItems(), p -> true);
            filteredData.setPredicate(s -> filterMaterialsBox.getSelectionModel().getSelectedItem().equals(s.getType()));
            table1.setItems(filteredData);
        });


    }

    @FXML
    void removeFilter(MouseEvent event) {
        list.clear();
        showMaterials();
    }

}