package sample;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.*;

import javafx.scene.shape.Line;
import javafx.stage.Stage;


import java.sql.SQLException;
import java.util.ArrayList;



public class Main extends Application {

    public ArrayList<Line> lines = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{



        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("A D A D W" );
        primaryStage.setScene(scene);

        primaryStage.show();


    }


    public static void main(String[] args) throws SQLException {
        launch(args);


    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }










}
