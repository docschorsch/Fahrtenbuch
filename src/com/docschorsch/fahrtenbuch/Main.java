package com.docschorsch.fahrtenbuch;

import datamodel.TripData;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        try {
            TripData.getInstance().loadTripData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        try {
            TripData.getInstance().storeTripData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
