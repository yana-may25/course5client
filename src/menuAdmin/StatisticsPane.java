package menuAdmin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Client;
import classes.Jewellery;
import classes.standard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import stats.Statistics1;
import stats.Statistics2;

public class StatisticsPane extends standard {
    Client cl = Client.getInstance();
    private ObservableList<Jewellery> statisticsList = FXCollections.observableArrayList();



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableView<Statistics1> stat1Table;

    @FXML
    private TableColumn<?, ?> idOrderColomn;

    @FXML
    private TableColumn<?, ?> jewNumColomn;

    @FXML
    private TableColumn<?, ?> sumColomn;

    @FXML
    private TableView<Jewellery> table2;

    @FXML
    private TableColumn<?, ?> nameColomn2;

    @FXML
    private TableColumn<?, ?> orderNumber2;

    @FXML
    private AnchorPane ancPane;
    @FXML
    private AnchorPane ancPane1;

    @FXML
    private Label avgJews;
    @FXML
    private Label avgCheck;


    public void loadStats1(){
        cl.sendMessage("Statistics1");
        ArrayList<Statistics1> statistics1s = new ArrayList<>();
        ObservableList<Statistics1> statistics1ObservableList = FXCollections.observableArrayList();
        statistics1s = (ArrayList<Statistics1>) cl.readObject();
        statistics1ObservableList.addAll(statistics1s);
        idOrderColomn.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        jewNumColomn.setCellValueFactory(new PropertyValueFactory<>("jewNumber"));
        sumColomn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        stat1Table.setItems(statistics1ObservableList);
        avgJews.setText(String.valueOf(cl.readObject()));
        avgCheck.setText(String.valueOf(cl.readObject()));

    }
    public void loadStats2(){
        cl.sendMessage("Statistics2");

        ArrayList<Statistics2> statistics2s = new ArrayList<>();
        ObservableList<Statistics2> statistics2ObservableList = FXCollections.observableArrayList();
        statistics2s = (ArrayList<Statistics2>) cl.readObject();
        statistics2ObservableList.addAll(statistics2s);



        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Колсество заказов");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Количество клиентов");

        // Create a BarChart


        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        // Series 1 - Data of 2014
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
        dataSeries1.setName("2014");
        for (int i = 0; i < statistics2s.size(); i++)

        dataSeries1.getData().add(new XYChart.Data<String, Number>(statistics2s.get(i).getOrderNum(), statistics2s.get(i).getClientNum()));

        barChart.getData().add(dataSeries1);
        ancPane1.getChildren().addAll(barChart);

    }
    public void loadTable3() {
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
        loadStats1();
        loadStats2();
        loadTable3();

    }

    @FXML
    void back(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("../menuAdmin/adminMenu.fxml", "Меню администратора");
    }
}
