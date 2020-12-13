package menuUser;

import java.io.IOException;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class JewelleryPaneUser extends standard {
    Client cl = Client.getInstance();
    private ObservableList<String> types = FXCollections.observableArrayList();
    private ObservableList<Jewellery> jlist = FXCollections.observableArrayList();
    private ObservableList<Jewellery> statisticsList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TableColumn<Jewellery, String> price;

    @FXML
    private ComboBox<String> filterBox;

    @FXML
    private TableView<Jewellery> table2;

    @FXML
    private TableColumn<?, ?> nameColomn2;

    @FXML
    private TableColumn<?, ?> orderNumber2;


    @FXML
    private AnchorPane ancPane;

    @FXML
    private Button removeFilterButton;

    @FXML
    void back(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("Second.fxml", "Title");
    }

    public void loadCollectionNames() {
        cl.sendMessage("getCollectionNames");
        ArrayList<String> jewList = new ArrayList<>();

        jewList = (ArrayList<String>) cl.readObject();
        types.addAll(jewList);
        filterBox.setItems(types);
    }

    public void loadTable1() {
        cl.sendMessage("showJewellery");
        ArrayList<Jewellery> jewArrayList = new ArrayList<>();
        jewArrayList = (ArrayList<Jewellery>) cl.readObject();
        jlist.addAll(jewArrayList);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        collection.setCellValueFactory(new PropertyValueFactory<>("collection"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        material.setCellValueFactory(new PropertyValueFactory<>("material1"));
        weightM.setCellValueFactory(new PropertyValueFactory<>("weight1"));
        stones.setCellValueFactory(new PropertyValueFactory<>("material2"));
        weightS.setCellValueFactory(new PropertyValueFactory<>("weight2"));
        price.setCellValueFactory(new PropertyValueFactory<Jewellery, String>("price"));
        table1.setItems(jlist);
    }

    public void loadTable2() {
        cl.sendMessage("showPopularity");
        ArrayList<Jewellery> jewArrayList = new ArrayList<>();
        jewArrayList = (ArrayList<Jewellery>) cl.readObject();
        statisticsList.addAll(jewArrayList);
        nameColomn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderNumber2.setCellValueFactory(new PropertyValueFactory<>("id"));
        table2.setItems(statisticsList);

        PieChart pieChart = new PieChart();

        ObservableList<PieChart.Data> valueList = FXCollections.observableArrayList();
        for (int i = 0; i < jewArrayList.size(); i++)
            valueList.add(new PieChart.Data(jewArrayList.get(i).getName(), jewArrayList.get(i).getId()));
        pieChart.getData().addAll(valueList);
        ancPane.getChildren().addAll(pieChart);

    }

    @FXML
    void initialize() {

        loadTable1();
        loadTable2();
        loadCollectionNames();
        filterBox.setOnAction(event -> {
            jlist.clear();
            loadTable1();
            FilteredList<Jewellery> filteredData = new FilteredList<>(table1.getItems(), p -> true);
            filteredData.setPredicate(s -> filterBox.getSelectionModel().getSelectedItem().equals(s.getCollection()));
            table1.setItems(filteredData);

        });
        removeFilterButton.setOnAction(event ->{
            jlist.clear();
            loadTable1();
        });
    }
}
