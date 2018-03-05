package sjsu.cmpe275.project.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Train implements Serializable {

	private static final long serialVersionUID = -3679909999869704383L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

	@Column(nullable = false)
	private String Name;
	@Column(nullable = false)
	private String origin;
	@Column(nullable = false)
	private String destination;
	@Column(nullable = false)
	private String trainType;

	private Time startTime;
	private Time endTime;


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getName() {return this.Name;}
	public void setName(String name) {
		this.Name = name;
	}

	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String type) {
		trainType = type;
	}
}
