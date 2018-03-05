/**
 * 
 */
package sjsu.cmpe275.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sjsu.cmpe275.project.util.SqlTimeDeserializer;
import sjsu.cmpe275.project.util.SqlTimeSerializer;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class OneWayTrip {
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER,cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "TICKET_ID", referencedColumnName = "TICKET_ID" ,unique = false, insertable = true, updatable = true)
	private CompleteTicket ticketOneWay;

	@Column(nullable = true)
	private String name;

	@JsonFormat(pattern = "HH:mm")
	@JsonDeserialize(using = SqlTimeDeserializer.class)
	@JsonSerialize(using = SqlTimeSerializer.class)
	@Column(nullable = true)
	private Time departureTime;

	@JsonFormat(pattern = "HH:mm")
	@JsonDeserialize(using = SqlTimeDeserializer.class)
	@JsonSerialize(using = SqlTimeSerializer.class)
	@Column(nullable = true)
	private Time arrivalTime;

	@Column(nullable = true)
	private double price;
	@Column(nullable = true)
	private String depStation;
	@Column(nullable = true)
	private String arrStation;


	public OneWayTrip() {

	}

	/**
	 *
	 * @param name
	 * @param departureTime
	 * @param arrivalTime
	 * @param price
	 * @param depStation
	 * @param arrStation
	 */
	public OneWayTrip(String name, Time departureTime,
					  Time arrivalTime, double price, String depStation, String arrStation) {
		super();
		this.name = name;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.price = price;
		this.depStation = depStation;
		this.arrStation = arrStation;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the ticketOneWay
	 */
	public CompleteTicket getTicketOneWay() {
		return ticketOneWay;
	}

	/**
	 * @param ticketOneWay
	 *            the ticketOneWay to set
	 */
	public void setTicketOneWay(CompleteTicket ticketOneWay) {
		this.ticketOneWay = ticketOneWay;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the departureTime
	 */
	public Time getDepartureTime() {
		return departureTime;
	}

	/**
	 * @param departureTime
	 *            the departureTime to set
	 */
	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * @return the arrivalTime
	 */
	public Time getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @param arrivalTime
	 *            the arrivalTime to set
	 */
	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the depStation
	 */
	public String getDepStation() {
		return depStation;
	}

	/**
	 * @param depStation
	 *            the depStation to set
	 */
	public void setDepStation(String depStation) {
		this.depStation = depStation;
	}

	/**
	 * @return the arrStation
	 */
	public String getArrStation() {
		return arrStation;
	}

	/**
	 * @param arrStation
	 *            the arrStation to set
	 */
	public void setArrStation(String arrStation) {
		this.arrStation = arrStation;
	}

}
