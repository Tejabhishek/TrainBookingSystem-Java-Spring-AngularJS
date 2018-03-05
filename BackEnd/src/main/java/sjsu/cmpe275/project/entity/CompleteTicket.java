/**
 * 
 */
package sjsu.cmpe275.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "TRAIN_TICKET")
public class CompleteTicket implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "TICKET_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private String userName;
	@Column(nullable = false)
	private long noOfPassengers;
	@Column(nullable = false)
	private String passengerNames;
	@Column(nullable = false)
	private long oneWayTripTime;
	@Column(nullable = false)
	private long returnTripTime;

	@JsonIgnore
	@Column(nullable = false)
	private long totalTripTime;
	@Column(nullable = false)
	private long totalTripPrice;

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	private Date bookingDate;

	public Date getDepDate() {
		return depDate;
	}

	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}

	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone="UTC",locale = "US")
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "America/Los_Angeles")
	private Date depDate;

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone="UTC",locale = "US")
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "America/Los_Angeles")
	private Date arrDate;

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	private boolean cancelled;

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

	@Column(nullable = true)
	private String depStation;

	@Column(nullable = true)
	private String arrStation;

	public boolean isAutomateRebook() {
		return automateRebook;
	}

	public void setAutomateRebook(boolean automateRebook) {
		this.automateRebook = automateRebook;
	}

	@Column(nullable = true)
	private boolean automateRebook;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	//@Transient
	@OneToMany(mappedBy = "ticketOneWay",cascade = javax.persistence.CascadeType.ALL)
	private List<OneWayTrip> oneWayTrips;

	//@Transient
	@OneToMany(mappedBy = "ticketReturn",cascade = javax.persistence.CascadeType.ALL)
	private List<ReturnTrip> returnTrips;

	public CompleteTicket() {

	}

	/**
	 * @param userName
	 * @param noOfPassengers
	 * @param passengerNames
	 * @param oneWayTripTime
	 * @param returnTripTime
	 * @param totalTripTime
	 * @param totalTripPrice
	 * @param oneWayTrips
	 * @param returnTrips
	 */
	public CompleteTicket(String userName, long noOfPassengers, String passengerNames,Date depDate,Date arrDate,long oneWayTripTime,
						  long returnTripTime, long totalTripTime, long totalTripPrice, List<OneWayTrip> oneWayTrips,
						  List<ReturnTrip> returnTrips) {
		super();
		this.userName = userName;
		this.noOfPassengers = noOfPassengers;
		this.passengerNames = passengerNames;
		this.oneWayTripTime = oneWayTripTime;
		this.returnTripTime = returnTripTime;
		this.totalTripTime = totalTripTime;
		this.totalTripPrice = totalTripPrice;
		this.oneWayTrips = oneWayTrips;
		this.returnTrips = returnTrips;
		this.depDate = depDate;
		this.arrDate = arrDate;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the noOfPassengers
	 */
	public long getNoOfPassengers() {
		return noOfPassengers;
	}

	/**
	 * @param noOfPassengers
	 *            the noOfPassengers to set
	 */
	public void setNoOfPassengers(long noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}

	/**
	 * @return the passengerNames
	 */
	public String getPassengerNames() {
		return passengerNames;
	}

	/**
	 * @param passengerNames
	 *            the passengerNames to set
	 */
	public void setPassengerNames(String passengerNames) {
		this.passengerNames = passengerNames;
	}

	/**
	 * @return the oneWayTripTime
	 */
	public long getOneWayTripTime() {
		return oneWayTripTime;
	}

	/**
	 * @param oneWayTripTime
	 *            the oneWayTripTime to set
	 */
	public void setOneWayTripTime(long oneWayTripTime) {
		this.oneWayTripTime = oneWayTripTime;
	}

	/**
	 * @return the returnTripTime
	 */
	public long getReturnTripTime() {
		return returnTripTime;
	}

	/**
	 * @param returnTripTime
	 *            the returnTripTime to set
	 */
	public void setReturnTripTime(long returnTripTime) {
		this.returnTripTime = returnTripTime;
	}

	/**
	 * @return the totalTripTime
	 */
	public long getTotalTripTime() {
		return totalTripTime;
	}

	/**
	 * @param totalTripTime
	 *            the totalTripTime to set
	 */
	public void setTotalTripTime(long totalTripTime) {
		this.totalTripTime = totalTripTime;
	}

	/**
	 * @return the totalTripPrice
	 */
	public long getTotalTripPrice() {
		return totalTripPrice;
	}

	/**
	 * @param totalTripPrice
	 *            the totalTripPrice to set
	 */
	public void setTotalTripPrice(long totalTripPrice) {
		this.totalTripPrice = totalTripPrice;
	}

	/**
	 * @return the oneWayTrips
	 */
	public List<OneWayTrip> getOneWayTrips() {
		return oneWayTrips;
	}

	/**
	 * @param oneWayTrips
	 *            the oneWayTrips to set
	 */
	public void setOneWayTrips(List<OneWayTrip> oneWayTrips) {
		this.oneWayTrips = oneWayTrips;
	}

	/**
	 * @return the returnTrips
	 */
	public List<ReturnTrip> getReturnTrips() {
		return returnTrips;
	}

	/**
	 * @param returnTrips
	 *            the returnTrips to set
	 */
	public void setReturnTrips(List<ReturnTrip> returnTrips) {
		this.returnTrips = returnTrips;
	}

}