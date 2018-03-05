package sjsu.cmpe275.project.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by shiva on 12/9/17.
 */
public class ItineraryUtil implements Comparable<ItineraryUtil>{

    private List<TrainTripUtil> trips;
    private long totalTripTime;

    public int getTotalTripPrice() {
        return totalTripPrice;
    }

    public void setTotalTripPrice(int totalTripPrice) {
        this.totalTripPrice = totalTripPrice;
    }

    private int totalTripPrice;

    public long getArrTime() {
        return arrTime;
    }

    @JsonIgnore
    private long arrTime;

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    private Date tripDate;

    public ItineraryUtil(List<TrainTripUtil> trips,Date tripDate) {
        this.trips = trips;
        this.totalTripTime = this.generateTotalTripTime(trips);
        this.totalTripPrice = this.generateTotalTripPrice(trips);
        this.arrTime = this.getItineraryArrivalTime(trips);

        this.tripDate = tripDate;
    }


    public List<TrainTripUtil> getTrips() {
        return trips;
    }

    public void setTrips(List<TrainTripUtil> trips) {
        this.trips = trips;
    }

    public long getTotalTripTime() {
        return totalTripTime;
    }

    public void setTotalTripTime(long totalTripTime) {
        this.totalTripTime = totalTripTime;
    }

    private long generateTotalTripTime(List<TrainTripUtil> trips){
        String depTime;
        String arrTime;

        depTime = trips.get(0).getDepartureTime();
        arrTime = trips.get(trips.size()-1).getArrivalTime();

        Time departureTime = Time.valueOf(depTime + ":00");
        Time arrivalTime = Time.valueOf(arrTime + ":00");
        long diff = Math.abs(departureTime.getTime() - arrivalTime.getTime())/60000;
        return diff;

    }

    private long getItineraryArrivalTime(List<TrainTripUtil> trips){
        return Time.valueOf(trips.get(trips.size()-1).getArrivalTime()+":00").getTime();
    }

    private int generateTotalTripPrice(List<TrainTripUtil> trips){
        int  totalPrice = 0;

        for (TrainTripUtil t:trips){
            totalPrice = totalPrice + t.getPrice();
        }
        return totalPrice;
    }

    public int compareTo(ItineraryUtil itineraryUtil){
        return this.getArrTime() > itineraryUtil.getArrTime() ? 1 : this.getArrTime() < itineraryUtil.getArrTime() ? -1 : this.totalTripTime > itineraryUtil.getTotalTripTime() ? 1 : this.totalTripTime < itineraryUtil.getTotalTripTime() ? -1 : 0;
    }


}