package sjsu.cmpe275.project.util;

import java.sql.Date;

/**
 * Created by shiva on 11/29/17.
 */
public class TrainTripUtil {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getDepStation() {
        return depStation;
    }

    public void setDepStation(String depStation) {
        this.depStation = depStation;
    }



    public String getArrStation() {
        return arrStation;
    }

    public void setArrStation(String arrStation) {
        this.arrStation = arrStation;
    }
    private String name;
    private String departureTime;
    private String arrivalTime;
    private int price;
    private int availableSeats;
    private String depStation;
    private String arrStation;

    private boolean cancelled = false;

    public TrainTripUtil(String name, String departureTime, String arrivalTime,String depStation,String arrStation, int price, int availableSeats,boolean cancelled){
        this.name = name;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.depStation = depStation;
        this.arrStation = arrStation;
        this.price = price;


        this.availableSeats = availableSeats;
        this.cancelled = cancelled;

    }
}
