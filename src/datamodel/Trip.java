package datamodel;

import com.docschorsch.fahrtenbuch.MyLocalDateTime;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Trip {
    private SimpleStringProperty startLocation = new SimpleStringProperty("");
    private SimpleStringProperty endLocation = new SimpleStringProperty("");
    private SimpleIntegerProperty distance = new SimpleIntegerProperty();
    private MyLocalDateTime tripBegin;
    private MyLocalDateTime tripEnd;




    public Trip(String startLocation, String endLocation, int distance, LocalDateTime tripBegin, LocalDateTime tripEnd) {
//        this.startLocation = startLocation;
        this.startLocation.set(startLocation);
        this.endLocation.set(endLocation);
        this.distance.set(distance);
        this.tripBegin = new MyLocalDateTime(tripBegin);
        this.tripEnd = new MyLocalDateTime(tripEnd);

    }

    public String getStartLocation() {
        return startLocation.get();
    }

    public SimpleStringProperty startLocationProperty() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation.set(startLocation);
    }

    public String getEndLocation() {
        return endLocation.get();
    }

    public SimpleStringProperty endLocationProperty() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation.set(endLocation);
    }

    public int getDistance() {
        return distance.get();
    }

    public SimpleIntegerProperty distanceProperty() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance.set(distance);
    }

    public MyLocalDateTime getTripBegin() {
        return tripBegin;
    }

    public void setTripBegin(MyLocalDateTime tripBegin) {
        this.tripBegin = tripBegin;
    }

    public MyLocalDateTime getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(MyLocalDateTime tripEnd) {
        this.tripEnd = tripEnd;
    }
    @Override
    public String toString() {
//        return startLocation + " --> " + endLocation + " at " + tripBegin.format(DateTimeFormatter.ofPattern("d MMM uuuu : HH mm")) + " to " + tripEnd.format(DateTimeFormatter.ofPattern("d MMM uuuu : HH mm"));
        return startLocation.toString();
    }


}
