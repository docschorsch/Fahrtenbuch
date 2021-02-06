package datamodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Trip {
    private String startLocation;
    private String endLocation;
    private int distance;
    private LocalDateTime tripBegin;
    private LocalDateTime tripEnd;


    public Trip(String startLocation, String endLocation, int distance, LocalDateTime tripBegin, LocalDateTime tripEnd) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.tripBegin = tripBegin;
        this.tripEnd = tripEnd;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public LocalDateTime getTripBegin() {
        return tripBegin;
    }

    public void setTripBegin(LocalDateTime tripBegin) {
        this.tripBegin = tripBegin;
    }

    public LocalDateTime getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(LocalDateTime tripEnd) {
        this.tripEnd = tripEnd;
    }
    @Override
    public String toString() {
        return startLocation + " --> " + endLocation + " at " + tripBegin.format(DateTimeFormatter.ofPattern("d MMM uuuu : HH mm")) + " to " + tripEnd.format(DateTimeFormatter.ofPattern("d MMM uuuu : HH mm"));
    }
}
