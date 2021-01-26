package com.docschorsch.fahrtenbuch;

import datamodel.Trip;
import datamodel.TripData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
//import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Controller {

//    public static ArrayList<Trip> trips = new ArrayList<Trip>();
    @FXML
    private ListView<Trip> tripListView;
    @FXML
    private TextField startLocationArea;
    @FXML
    private TextField endLocationArea;
    @FXML
    private TextField distanceArea;

    @FXML
    private TextField tripBeginHour;
    @FXML
    private TextField tripBeginMinute;
    @FXML
    private TextField tripEndHour;
    @FXML
    private TextField tripEndMinute;


    @FXML
    private DatePicker tripBeginDatePicker;
    @FXML
    private DatePicker tripEndDatePicker;


    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {

//        Trip trip = new Trip("muc", "ber", 600, LocalDateTime.now(),LocalDateTime.now());
//        Trip trip2 = new Trip("ber", "muc", 20, LocalDateTime.now(),LocalDateTime.now());
//        Trip trip3 = new Trip("bra", "bar", 300, LocalDateTime.now(),LocalDateTime.now());

//        ArrayList<Trip> trips = new ArrayList<>();
//        TripData.getInstance().addTrip(trip);
//        TripData.getInstance().addTrip(trip2);
//        TripData.getInstance().addTrip(trip3);

//        trips.add(trip2);
//        trips.add(trip3);

        tripListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Trip>() {
            @Override
            public void changed(ObservableValue<? extends Trip> observableValue, Trip trip, Trip t1) {
                if(t1 != null) {
                    Trip selTrip = tripListView.getSelectionModel().getSelectedItem();
                    startLocationArea.setText(selTrip.getStartLocation());
                    endLocationArea.setText(selTrip.getEndLocation());
                    distanceArea.setText(Integer.toString(selTrip.getDistance()));

                }
            }
        });

        tripListView.getItems().setAll(TripData.getInstance().getTrips());
        tripListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tripListView.getSelectionModel().selectFirst();

    }

    public void addTrip() {
        String newStartLocation = startLocationArea.getText();
        String newEndLocation = endLocationArea.getText();
        int newDistance = Integer.parseInt(distanceArea.getText());
        LocalDateTime tripBeginDateTime;
        LocalDateTime tripEndDateTime;

        //set default of Hour, Min Textfields to 00
//        tripBeginHour.setText("00");
//        tripBeginMinute.setText("00");
//        tripEndHour.setText("00");
//        tripEndMinute.setText("00");

        LocalTime tripBeginTime;
        LocalTime tripEndTime;

        // datePicker must not be null/default
        if (tripBeginDatePicker.getValue()!=null && tripEndDatePicker.getValue()!=null) {
            tripBeginTime = LocalTime.parse(tripBeginHour.getText() + ":" + tripBeginMinute.getText());
            System.out.println(tripBeginTime);
            tripBeginDateTime = LocalDateTime.of(tripBeginDatePicker.getValue(), tripBeginTime);
            tripEndTime = LocalTime.parse(tripEndHour.getText() + ":" + tripEndMinute.getText());
            tripEndDateTime = LocalDateTime.of(tripEndDatePicker.getValue(), tripEndTime);

        } else {
            return;
        }

        Trip trip = new Trip(newStartLocation,newEndLocation,newDistance,tripBeginDateTime,tripEndDateTime);
        if((newStartLocation == null) || (newEndLocation == null) || (newDistance == 0) || (tripBeginDateTime == null) || (tripEndDateTime == null)) {
            System.out.println("Error - trying to add empty or 0 field!");
        } else {
            TripData.getInstance().addTrip(trip);
            tripListView.getItems().setAll(TripData.getInstance().getTrips());
        }
    }

}
