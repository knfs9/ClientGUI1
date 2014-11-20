package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import serverclient.server.other.server.Library;
import serverclient.server.other.server.ServerClient;

import javax.naming.spi.InitialContextFactory;
import javafx.scene.control.TextField;
import serverclient.server.xmlDOM.WriteXML;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

import serverclient.client.Client;

public class Controller implements Initializable {



    @FXML
    TableView<Library> table;
    @FXML
    private TableColumn<Library,String> author;
    @FXML
    private TableColumn<Library,String> name;
    @FXML
    private TableColumn<Library,String> genre;
    @FXML
    private TableColumn<Library,Integer> date;
    @FXML
    private TableColumn<Library,Integer> count;
    @FXML
    private Button addBooksButton;
    @FXML
    private Button deleteBooksButton;
    @FXML
    private Button editButton;
    private Button addOk = new Button("Ok");
    private Button editOk = new Button("Ok");

    private TextField authorField = new TextField();
    private Label authorLabel = new Label("Author");

    private TextField nameField = new TextField();
    private Label nameLabel = new Label("Name");

    private TextField genreField = new TextField();
    private Label genreLabel = new Label("Genre");

    private TextField dateField = new TextField();
    private Label dateLabel = new Label("Date");

    private TextField countField = new TextField();
    private Label countLabel = new Label("Count");

    private Client client;
    private ArrayList<Library> books;
    private ObservableList<Library> obs;



    private void setLayout(Node node,double x, double y){
        node.setLayoutX(x);
        node.setLayoutY(y);
    }



    private void sumbitAction(){

        addOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String author = authorField.getText();
                String name = nameField.getText();
                String genre = genreField.getText();
                int date = Integer.parseInt(dateField.getText());
                int count = Integer.parseInt(countField.getText());
                books = new ArrayList<Library>();
                obs = FXCollections.observableArrayList();
                Library lib = new Library(author,name,genre,date,count,0);

                books.add(lib);
                obs.addAll(books);
                table.setItems(FXCollections.observableArrayList(lib));
                try {
                    client.addData(books);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    books.clear();
                    books.addAll(client.getAllData());
                }catch (Exception e){
                    e.printStackTrace();
                }


                cleanForm();
                try {
                    client.update();
                    updateTable();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }

    private void cleanForm(){
        authorField.clear();
        genreField.clear();
        dateField.clear();
        countField.clear();
        nameField.clear();
    }

    public void editAction(){
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage secondStage = createStage(editOk);
                String author = authorField.getText();
                String name = nameField.getText();
                String genre = genreField.getText();
                int date = Integer.parseInt(dateField.getText());
                int count = Integer.parseInt(countField.getText());

                secondStage.show();

            }
        });
    }

    Stage createStage(Button button){
        AnchorPane pane = new AnchorPane();
        Scene secondScene = new Scene(pane,400,280);
        Stage secondStage = new Stage();

        setLayout(button,50,250);
        button.setPrefSize(50,20);

        setLayout(authorField,secondScene.getHeight()/2,50);
        setLayout(authorLabel,100,50);
        authorField.setPromptText("Author");


        setLayout(nameField, secondScene.getHeight() / 2, 80);
        setLayout(nameLabel,100,80);
        nameField.setPromptText("Name");


        setLayout(genreField, secondScene.getHeight() / 2, 110);
        setLayout(genreLabel,100,110);
        genreField.setPromptText("Genre");


        setLayout(dateField, secondScene.getHeight() / 2, 140);
        setLayout(dateLabel,100,140);
        dateField.setPromptText("Date");


        setLayout(countField, secondScene.getHeight() / 2, 170);
        setLayout(countLabel, 100, 170);
        countField.setPromptText("Count");


        pane.getChildren().addAll(button,authorField,nameField,genreField,dateField,countField,
                authorLabel,nameLabel,genreLabel,dateLabel,countLabel);
        secondStage.setTitle("Add book");
        secondStage.setScene(secondScene);
        secondStage.setX(100);
        secondStage.setY(150);
        return secondStage;
    }

    public void addAction(){
        addBooksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Stage secondStage = createStage(addOk);
                sumbitAction();

                try {
                    client.update();
                    updateTable();
                }catch (Exception e){
                    e.printStackTrace();
                }
                secondStage.show();

            }
        });
    }

    public void deleteAction(){
        deleteBooksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               // int row = table.getSelectionModel().get
                int row = table.getSelectionModel().getSelectedIndex();
                try {
                    client.removeData(table.getItems().get(row).getName());
                }catch (Exception e){
                    e.printStackTrace();
                }
                table.getItems().remove(row);

                try {
                    client.update();
                    updateTable();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }



    public void upd() throws  Exception{
        obs =FXCollections.observableArrayList(client.getAllData()) ;
    }

    public void updateTable() throws  Exception{
        obs = FXCollections.observableArrayList(client.getAllData()) ;
        table.setItems(obs);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            client =  new Client(this);
            client.connect(client);
            client.update();
        }catch (RemoteException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        author.setCellValueFactory(new PropertyValueFactory<Library, String>("author"));
        name.setCellValueFactory(new PropertyValueFactory<Library, String>("name"));
        genre.setCellValueFactory(new PropertyValueFactory<Library, String>("genre"));
        date.setCellValueFactory(new PropertyValueFactory<Library, Integer>("date"));
        count.setCellValueFactory(new PropertyValueFactory<Library, Integer>("count"));

        try {
            ObservableList<Library> temp = FXCollections.observableArrayList(client.getAllData()) ;
            table.setItems(temp);
            client.update();
            updateTable();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

}
