package datamodel;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class TripData {
    private static TripData instance = new TripData();
    private static String filename = "TripsDataFile.txt";

    private List<Trip> trips;
    private DateTimeFormatter formatter;

    private TripData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm");
    }

    public static TripData getInstance() {
        return instance;
    }

    public static String getFilename() {
        return filename;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    public void loadTripData() throws IOException {
        trips = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input=br.readLine()) != null) {
                String[] tripParameters = input.split("\t");
                String startLocation = tripParameters[0];
                String endLocation = tripParameters[1];
                String distanceString = tripParameters[2];
                String dateStringTripBegin = tripParameters[3];
                String dateStringTripEnd = tripParameters[4];
                LocalDateTime dateTripBegin = LocalDateTime.parse(dateStringTripBegin);
                LocalDateTime dateTripEnd = LocalDateTime.parse(dateStringTripEnd);
                int distance = Integer.parseInt(distanceString);
                Trip trip = new Trip(startLocation,endLocation,distance,dateTripBegin,dateTripEnd);
                trips.add(trip);
            }
        } finally {
            if(br!=null) {
                br.close();
            }
        }

    }

    public void storeTripData() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {
            Iterator<Trip> iterator = trips.iterator();
            while (iterator.hasNext()) {
                Trip trip = iterator.next();
                bw.write(String.format("%s\t%s\t%s\t%s\t%s\t",trip.getStartLocation(),trip.getEndLocation(),
                    trip.getDistance(),trip.getTripBegin(),trip.getTripEnd()));
                bw.newLine();
            }
        } finally {
            if(bw != null ) {
                bw.close();
            }
        }
    }

}
