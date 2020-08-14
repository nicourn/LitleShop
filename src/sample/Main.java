package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    ObservableList<Shoes> tableInfo;
    int WIDTH = 210;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
//        Панель управления (левая)
        Label top = new Label("Пошук товару:");
        VBox.setVgrow(top, Priority.ALWAYS);
//        Находжение цены по артиклю
        HBox searchByArtcInput = new HBox(5);
        Label searchByArtcLabel = new Label("Введіть артикил");
        HBox.setHgrow(searchByArtcLabel, Priority.ALWAYS);
        TextField searchByArtcText = new TextField();
        HBox.setHgrow(searchByArtcText, Priority.ALWAYS);
        searchByArtcInput.getChildren().addAll(searchByArtcLabel, searchByArtcText);
//        Кнопка для поиска
        HBox searchByArtcOutput = new HBox(20);
        Button searchByArtcButton = new Button("Шукати");
        HBox.setHgrow(searchByArtcButton, Priority.ALWAYS);
        searchByArtcButton.setMaxWidth(WIDTH);
        Label searchByArtcAnswer = new Label();
        HBox.setHgrow(searchByArtcAnswer, Priority.ALWAYS);
        searchByArtcOutput.getChildren().addAll(searchByArtcButton, searchByArtcAnswer);
//        Нахождение женской обуви
        Button searchByWomanButton = new Button("Пошук дамського взуття");
        VBox.setVgrow(searchByWomanButton, Priority.ALWAYS);
        searchByWomanButton.setMaxWidth(WIDTH);
//        Количесво обуви для взрослых
        HBox searchForAdult = new HBox(20);
        Button searchForAdultButton = new Button("Кількість дорослого взуття");
        HBox.setHgrow(searchForAdultButton, Priority.ALWAYS);
        searchForAdultButton.setMaxWidth(WIDTH);
        Label searchForAdultLabel = new Label();
        HBox.setHgrow(searchForAdultLabel, Priority.ALWAYS);
        searchForAdult.getChildren().addAll(searchForAdultButton, searchForAdultLabel);

        VBox.setVgrow(searchForAdult, Priority.ALWAYS);

        VBox control = new VBox(20, top, searchByArtcInput, searchByArtcOutput,
                searchByWomanButton, searchForAdult);
        HBox.setHgrow(control, Priority.ALWAYS);

//        Таблица (правая)
        TableView<Shoes> table = new TableView<>();
//        TableColumn<Shoes, String> artics = new TableColumn<Shoes, String>("Артикул");
        TableColumn<Shoes, String> names = new TableColumn<Shoes, String>("Назва");
        TableColumn<Shoes, Integer> counts = new TableColumn<Shoes, Integer>("Кількість");
//        TableColumn<Shoes, Integer> prises = new TableColumn<Shoes, Integer>("Ціна");
//        artics.setCellValueFactory(new PropertyValueFactory<>("artic"));
        names.setCellValueFactory(new PropertyValueFactory<>("name"));
        counts.setCellValueFactory(new PropertyValueFactory<>("count"));
//        prises.setCellValueFactory(new PropertyValueFactory<>("prise"));

        table.getColumns().addAll(names, counts);
        VBox.setVgrow(table, Priority.ALWAYS);

//        Поиск данных
        Button searchFile = new Button("Знайти документ");
        VBox.setVgrow(searchByArtcAnswer, Priority.ALWAYS);
        searchFile.setMaxWidth(Double.MAX_VALUE);

        VBox tableSetup = new VBox(table, searchFile);
        HBox.setHgrow(tableSetup, Priority.ALWAYS);


        HBox root = new HBox(20, control, tableSetup);

        Scene scene = new Scene(root);

//        Функции после инициализации всех обьектов
        searchByArtcButton.setOnAction(event -> {
            String needArtc = searchByArtcText.getText();
            searchByArtcAnswer.setText(searchByArtc(needArtc));
        });
        searchFile.setOnAction(event -> {
            try {
                table.setItems(readFile(stage));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        searchByWomanButton.setOnAction(event -> {
            table.setItems(searchByWoman());
        });
        searchForAdultButton.setOnAction(event -> {
            searchForAdultLabel.setText(String.valueOf(countForAdult()));
        });

        stage.setTitle("Shoes Shop");
        stage.setScene(scene);
        stage.show();
    }


    private ObservableList<Shoes> readFile(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Откройте документ");
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter("Текстовый документ (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(stage);
        FileReader readerFile = new FileReader(file);
        Scanner scan = new Scanner(readerFile);
        ArrayList<Shoes> peopleList = new ArrayList<Shoes>();
        while (scan.hasNext()){
            String artic = scan.next();
            String name = scan.next();
            int count = scan.nextInt();
            int price = scan.nextInt();
            Shoes people = new Shoes(artic, name, count, price);
            peopleList.add(people);
        }
        scan.close();
        readerFile.close();
        tableInfo = FXCollections.observableArrayList(peopleList);
        return tableInfo;
    }
    private String searchByArtc(String artc){
        for (Shoes shoes: this.tableInfo){
            if (shoes.getArtic().equals(artc)){
                return "Ціна: " + shoes.getPrise();
            }
        }
        return "Немає";
    }
    private ObservableList<Shoes> searchByWoman(){
        ArrayList<Shoes> newTable = new ArrayList<>();
        for (Shoes shoes: tableInfo){
            if (shoes.getArtic().startsWith("Д")){
                newTable.add(shoes);
            }
        }
        return FXCollections.observableArrayList(newTable);
    }
    private int countForAdult(){
        int willRet = 0;
        for (Shoes shoes: this.tableInfo){
            if (shoes.getArtic().startsWith("Д") || shoes.getArtic().startsWith("Ч")){
                willRet++;
            }
        }
        return willRet;
    }
}
