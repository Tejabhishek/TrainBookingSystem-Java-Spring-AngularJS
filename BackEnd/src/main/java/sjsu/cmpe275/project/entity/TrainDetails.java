package sjsu.cmpe275.project.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by shiva on 12/10/17.
 */
@Entity
public class TrainDetails  implements Serializable {

    private static final long serialVersionUID = -36799099869704383L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String trainName;
    @Column(nullable = false)
    private Date date;

    private boolean cancelled;
    private int availableSeats;

    public TrainDetails()
    {}

    public TrainDetails(String trainName, Date date, boolean cancelled, int availableSeats) {
        this.trainName = trainName;
        this.date = date;
        this.cancelled = cancelled;
        this.availableSeats = availableSeats;
    }

    public String getTrainName() {
        return this.trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void decrementAvailableSeats() {
        this.availableSeats--;
    }
}

