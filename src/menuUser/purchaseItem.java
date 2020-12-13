package menuUser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Client;
import classes.standard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import static java.lang.Double.parseDouble;

public class purchaseItem extends standard {
    double priceForSelected;

    Client cl = Client.getInstance();
    static ObservableList<String> list = FXCollections.observableArrayList();
    static ArrayList<String> jewArrayList = new ArrayList<>();
    static double priceSum;
    static String cardId = "-";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text uprice;

    @FXML
    private Text rubDiscount;

    @FXML
    private CheckBox checkBoxPrint;

    @FXML
    private Text sum;

    @FXML
    private ComboBox<String> jName;

    @FXML
    private Text percentDiscount;

    @FXML
    private Button add_button;

    @FXML
    private Button setPurchaseButton;

    @FXML
    private TextField cardNumber;


    @FXML
    void back(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("../menuUser/Second.fxml", "Меню продавца");
    }

    @FXML
    void setCard(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("../menuUser/newCard.fxml", "Оформление карты покупателя");

    }

    @FXML
    void add(MouseEvent event) {

        jewArrayList.add(jName.getSelectionModel().getSelectedItem());
        Double rubDisc=Double.parseDouble(percentDiscount.getText())*priceForSelected/100;
        rubDiscount.setText(String.valueOf(rubDisc));
        double a = parseDouble(uprice.getText());
        double b = Double.parseDouble(percentDiscount.getText());
        double c = (1 - b / 100);
        priceSum = a * c;
        sum.setText(String.valueOf(priceSum));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Изделие добавлено в корзину!");
        alert.showAndWait();
    }

    @FXML
    void checkCard(MouseEvent event) {
        cl.sendMessage("checkCard");
        cl.sendMessage(cardNumber.getText());
        ArrayList<String> str = (ArrayList<String>) cl.readObject();
        if (str.get(0).equals("0")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Карта не найдена!");
            alert.showAndWait();
        } else {
            percentDiscount.setText(str.get(1));
            cardId = str.get(0);
        }
    }

    @FXML
    void setPurchase(MouseEvent event) throws IOException {
        cl.sendMessage("setPurchase");
        cl.sendObject(jewArrayList);
        cl.sendObject(cardId);

        if (checkBoxPrint.isSelected())
            try (FileWriter writer = new FileWriter("notes3.txt", false)) {
                // запись всей строки
                writer.write("Карта клиента " + cardId);
                // запись по символам
                writer.append('\n');
                for (String item : jewArrayList) {
                    writer.write("Название " + item);
                    writer.append('\n');
                }
                writer.write("Скидка " + rubDiscount.getText());
                writer.append('\n');
                writer.write("Итого: " + priceSum);
                writer.append('\n');


                writer.flush();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Покупка оформлена!");
        alert.showAndWait();
        ((Node) event.getSource()).getScene().getWindow().hide();
        loadWindow("../menuUser/Second.fxml", "Меню продавца");
    }

    public void loadList() {
        cl.sendMessage("getJewelleryNames");
        ArrayList<String> jewList = new ArrayList<>();
        jewList = (ArrayList<String>) cl.readObject();
        list.addAll(jewList);
        jName.setItems(list);

    }

    @FXML
    void initialize() {


        add_button.setVisible(false);
        setPurchaseButton.setVisible(false);
        loadList();

        jName.setOnAction(event -> {
            add_button.setVisible(true);
            setPurchaseButton.setVisible(true);
            cl.sendMessage("getPrice");
            cl.sendObject(jName.getSelectionModel().getSelectedItem());

            priceForSelected = (Double) cl.readObject();
            uprice.setText(String.valueOf(priceForSelected));

        });
    }


}
