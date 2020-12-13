package menuAdmin;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Client;
import classes.Jewellery;
import classes.standard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class JewelleryPane extends standard {

    Client cl = Client.getInstance();
    private ObservableList<String> list = FXCollections.observableArrayList();
    private ObservableList<String> list1 = FXCollections.observableArrayList();

    private ObservableList<String> list2 = FXCollections.observableArrayList();
    private ObservableList<String> types = FXCollections.observableArrayList();
    private ObservableList<Jewellery> jlist = FXCollections.observableArrayList();



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField weightSArea;

    @FXML
    private TextField weightMArea;

    @FXML
    private Button add;

    @FXML
    private Button calculate;

    @FXML
    private TextField priceBox;

    @FXML
    private ChoiceBox<String> material1Box;

    @FXML
    private ChoiceBox<String> typeBox;

    @FXML
    private ChoiceBox<String> collectionBox;

    @FXML
    private ChoiceBox<String> material2Box;

    @FXML
    private TableView<Jewellery> table1;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> type;

    @FXML
    private TableColumn<?, ?> collection;

    @FXML
    private TableColumn<?, ?> material;

    @FXML
    private TableColumn<?, ?> weightM;

    @FXML
    private TableColumn<?, ?> stones;

    @FXML
    private TableColumn<?, ?> weightS;
    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<Jewellery, String> price;

    @FXML
    private Button delete;

    @FXML
    private ComboBox<String> filterBox;

    @FXML
    private ChoiceBox<?> filterMaterialsBox;

    @FXML
    void delete(MouseEvent event) {
        cl.sendMessage("deleteJewellery");
        cl.sendObject(table1.getSelectionModel().getSelectedItem());
        jlist.remove(table1.getSelectionModel().getSelectedItem());

    }

    @FXML
    void clear(MouseEvent event) {
        priceBox.clear();
        material2Box.setValue("none");
        weightMArea.clear();
        weightSArea.clear();
    }

    @FXML
    void back(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("../menuAdmin/adminMenu.fxml", "Просмотр изделий");
    }



    public void loadList() {
        cl.sendMessage("fillToAddJewellery");
        ArrayList<String> jewList1 = new ArrayList<>();

        jewList1 = (ArrayList<String>) cl.readObject();
        list1.clear();
        list1.addAll(jewList1);
        material1Box.setItems(list1);
        material1Box.setValue(list1.get(1));
        ArrayList<String> jewList = new ArrayList<>();

        jewList = (ArrayList<String>) cl.readObject();
        list2.clear();
        list2.addAll(jewList);
        list2.add("none");
        material2Box.setItems(list2);
        material2Box.setValue("none");
        weightSArea.setText("0");

        jewList = (ArrayList<String>) cl.readObject();
        list.clear();
        list.addAll(jewList);
        collectionBox.setItems(list);
        collectionBox.setValue(list1.get(2));

        types.addAll("брошь", "серьги", "кулон", "кольцо", "колье");
        typeBox.setItems(types);
        typeBox.setValue("кольцо");
    }

    public void loadTable(){

        cl.sendMessage("showJewellery");
        ArrayList<Jewellery> jewArrayList = new ArrayList<>();
        jewArrayList = (ArrayList<Jewellery>)cl.readObject();
        jlist.addAll(jewArrayList);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        collection.setCellValueFactory(new PropertyValueFactory<>("collection"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        material.setCellValueFactory(new PropertyValueFactory<>("material1"));
        weightM.setCellValueFactory(new PropertyValueFactory<>("weight1"));
        stones.setCellValueFactory(new PropertyValueFactory<>("material2"));
        weightS.setCellValueFactory(new PropertyValueFactory<>("weight2"));
        price.setCellValueFactory(new PropertyValueFactory<Jewellery, String>("price"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1.setItems(jlist);
    }


    @FXML
    void initialize() {
        loadTable();
        loadList();
        add.setVisible(false);
        weightSArea.setDisable(true);
        priceBox.setEditable(false);

        filterBox.setItems(types);
        filterBox.setOnAction(event ->{

            jlist.clear();
            loadTable();
            FilteredList<Jewellery> filteredData = new FilteredList<>(table1.getItems(), p -> true);
            filteredData.setPredicate(s->  filterBox.getSelectionModel().getSelectedItem().equals(s.getType()));
            table1.setItems(filteredData);

        });

        calculate.setOnAction(event ->{
            String weight1 =weightMArea.getText();
            String weight2 =weightMArea.getText();
            cl.sendMessage("calculatePrice");

        if ((weight1.isEmpty() || weight2.isEmpty())) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Заполните все поля!");
            alert.showAndWait();
        }
        else
        {
            cl.sendObject(collectionBox.getSelectionModel().getSelectedItem());
            cl.sendObject(material1Box.getSelectionModel().getSelectedItem());
            cl.sendObject(Double.parseDouble(weight1));
            cl.sendObject(material2Box.getSelectionModel().getSelectedItem());
            cl.sendObject(Double.parseDouble(weight2));
            double price = (double) cl.readObject();
            double scale = Math.pow(10, 2);
            double result = Math.ceil(price * scale) / scale;
            priceBox.setText(String.valueOf(price));
            add.setVisible(true);
        }
        });
        add.setOnAction(event->{
            cl.sendMessage("addJewellery");

            Jewellery jew= new Jewellery(typeBox.getSelectionModel().getSelectedItem(),
                    collectionBox.getSelectionModel().getSelectedItem(),
                    material1Box.getSelectionModel().getSelectedItem(),
                    Double.parseDouble(weightMArea.getText()),
                    material2Box.getSelectionModel().getSelectedItem(),
                    Double.parseDouble(weightSArea.getText()),
                    Double.parseDouble(priceBox.getText()));
            cl.sendObject(jew);

            Alert alert = new Alert (Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);

            try {
                alert.setContentText("Добавлена новая позиция" + cl.readMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            alert.showAndWait();
            jlist.clear();
            loadTable();
        });

        material2Box.setOnAction(event ->{

            if(material2Box.getSelectionModel().getSelectedItem()!="none")
                weightSArea.setDisable(false);
            else{
                weightSArea.setDisable(false);

            }

        });
    }

    @FXML
    void jupdate(MouseEvent event) {

    }

    @FXML void clearFilter(MouseEvent event) {

        jlist.clear();
        loadTable();
    }
}
