package sjsu.cmpe275.project.service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.project.dao.CompleteTicketDao;
import sjsu.cmpe275.project.dao.TrainDao;
import sjsu.cmpe275.project.dao.TrainDetailsDao;
import sjsu.cmpe275.project.entity.CompleteTicket;
import sjsu.cmpe275.project.entity.Train;
import sjsu.cmpe275.project.entity.TrainDetails;
import sjsu.cmpe275.project.util.ItineraryUtil;
import sjsu.cmpe275.project.util.TrainTripUtil;
import static java.lang.Math.abs;
import java.sql.Date;

@CrossOrigin(origins = "*")
@RestController
public class TrainService {

	@Autowired
	private TrainDao trainDao;

	@Autowired
	private TrainDetailsDao trainDetailsDao;


	private List<TrainTripUtil> getRegularTrainTrips(String depTime, String depStation, String arrStation, char startSation, java.sql.Date tripDate,boolean exactTimeFalg, int numOfTrains) {

		int depTimeFactor = 0;
		int arrTimeFactor = 0;
		int priceFactor = 0;
		List<TrainTripUtil> regularTrips = new ArrayList<TrainTripUtil>();
		List<Train> validTrains;
		Time departureTime = Time.valueOf(depTime + ":00");
		LocalTime depLocalTime = departureTime.toLocalTime();

		depTimeFactor = abs(depStation.charAt(0) - startSation) * 8;
		arrTimeFactor = abs(arrStation.charAt(0) - startSation) * 8 - 3;
		priceFactor = (int) Math.ceil((abs(arrStation.charAt(0) - depStation.charAt(0))) / 5.0);

		LocalTime depStartStationTime = depLocalTime.minusMinutes(depTimeFactor);
		//System.out.println(depStartStationTime);

		if (exactTimeFalg) {
			validTrains = trainDao.findTrainsByTrainTypeEqualsAndStartTimeEqualsAndOriginEqualsOrderByStartTime("Regular", Time.valueOf(depStartStationTime), Character.toString(startSation));
		} else {
			validTrains = trainDao.findTrainsByTrainTypeEqualsAndStartTimeGreaterThanEqualAndOriginEqualsOrderByStartTime("Regular", Time.valueOf(depStartStationTime), Character.toString(startSation));

		}//List<Train> tt = trainDao.getTrainsforDeparture("A", departureTime);
		//Map<String,HashMap<String,String>> responseMap = new HashMap<String,HashMap<String,String>> ();
		int TrainCount = 0;
		for (Train t : validTrains) {

			String startTimeString = t.getName().substring(2, 4) + ":" + t.getName().substring(4, 6) + ":00";
			//System.out.println(startTimeString);
			Time trainStartTime = Time.valueOf(startTimeString);
			LocalTime trainStartLocalTime = trainStartTime.toLocalTime();
			LocalTime depStationLocalTime = trainStartLocalTime.plusMinutes(depTimeFactor);
			LocalTime arrStationLocalTime = trainStartLocalTime.plusMinutes(arrTimeFactor);

			//System.out.println(t.getName() + tripDate);
			TrainDetails td = trainDetailsDao.findTopByTrainNameEqualsAndDateEquals(t.getName(),tripDate);
			if(td.isCancelled())
				continue;

			//System.out.println(t.getTrainName() + " -> " + depStationLocalTime  + "-----" + arrStationLocalTime + "-----" + priceFactor);
			TrainTripUtil trip = new TrainTripUtil(t.getName(), depStationLocalTime.toString(), arrStationLocalTime.toString(), depStation, arrStation, priceFactor,td.getAvailableSeats(),false);
			regularTrips.add(trip);

			TrainCount = TrainCount + 1;
			if (TrainCount == numOfTrains) {
				break;
			}
		}
		return regularTrips;
	}


	private List<TrainTripUtil> getExpressTrainTrips(String depTime, String depStation, String arrStation, char startSation,java.sql.Date tripDate, boolean exactTimeFalg, int numOfTrains) {

		int depTimeFactor = 0;
		int arrTimeFactor = 0;
		int priceFactor = 0;
		List<TrainTripUtil> regularTrips = new ArrayList<TrainTripUtil>();
		List<Train> validTrains;
		Time departureTime = Time.valueOf(depTime + ":00");
		LocalTime depLocalTime = departureTime.toLocalTime();

		// diff*5 + diff*(3/5)
		depTimeFactor = abs(depStation.charAt(0) - startSation) * 28 / 5;
		arrTimeFactor = (abs(arrStation.charAt(0) - startSation) * 28 / 5) - 3;
		priceFactor = (int) (abs(arrStation.charAt(0) - depStation.charAt(0)) / 5) * 2;

		LocalTime depStartStationTime = depLocalTime.minusMinutes(depTimeFactor);
		//System.out.println(depStartStationTime);

		if (exactTimeFalg) {
			validTrains = trainDao.findTrainsByTrainTypeEqualsAndStartTimeEqualsAndOriginEqualsOrderByStartTime("Express", Time.valueOf(depStartStationTime), Character.toString(startSation));
		} else {
			validTrains = trainDao.findTrainsByTrainTypeEqualsAndStartTimeGreaterThanEqualAndOriginEqualsOrderByStartTime("Express", Time.valueOf(depStartStationTime), Character.toString(startSation));

		}//List<Train> tt = trainDao.getTrainsforDeparture("A", departureTime);
		//Map<String,HashMap<String,String>> responseMap = new HashMap<String,HashMap<String,String>> ();
		int TrainCount = 0;
		for (Train t : validTrains) {

			String startTimeString = t.getName().substring(2, 4) + ":" + t.getName().substring(4, 6) + ":00";
			//System.out.println(startTimeString);
			Time trainStartTime = Time.valueOf(startTimeString);
			LocalTime trainStartLocalTime = trainStartTime.toLocalTime();
			LocalTime depStationLocalTime = trainStartLocalTime.plusMinutes(depTimeFactor);
			LocalTime arrStationLocalTime = trainStartLocalTime.plusMinutes(arrTimeFactor);

			TrainDetails td = trainDetailsDao.findTopByTrainNameEqualsAndDateEquals(t.getName(),tripDate);
			if(td.isCancelled())
				continue;

			//System.out.println(t.getTrainName() + " -> " + depStationLocalTime  + "-----" + arrStationLocalTime + "-----" + priceFactor);
			TrainTripUtil trip = new TrainTripUtil(t.getName(), depStationLocalTime.toString(), arrStationLocalTime.toString(), depStation, arrStation, priceFactor, td.getAvailableSeats(),false);
			regularTrips.add(trip);

			TrainCount = TrainCount + 1;
			if (TrainCount == numOfTrains) {
				break;
			}
		}
		return regularTrips;
	}

	private String getWaitTimeAdded(String inpTime) {
		Time departureTime = Time.valueOf(inpTime + ":00");
		LocalTime localTime = departureTime.toLocalTime();
		localTime = localTime.plusMinutes(3);

		return localTime.toString();

	}

	private List<ItineraryUtil> mergeTrips(List<TrainTripUtil> firstList, List<TrainTripUtil> secondList,Date tripDate) {
		List<ItineraryUtil> responseList = new ArrayList<>();
		List<TrainTripUtil> tempItinerary;
		ItineraryUtil itineraryUtil;
		//System.out.println(firstList.size());
		//System.out.println(secondList.size());
		for (TrainTripUtil first : firstList) {
			for (TrainTripUtil second : secondList) {
				//System.out.println("firstArrival -" + first.getArrivalTime());
				//System.out.println("SecondDeparture -" + second.getDepartureTime());
				if (Time.valueOf(first.getArrivalTime() + ":00").getTime() <= Time.valueOf(second.getDepartureTime() + ":00").getTime()) {
					tempItinerary = new ArrayList<TrainTripUtil>();
					tempItinerary.add(first);
					tempItinerary.add(second);
					itineraryUtil = new ItineraryUtil(tempItinerary,tripDate);
					responseList.add(itineraryUtil);
				}
			}
		}
		Collections.sort(responseList);
		if (responseList.size()>=5) return responseList.subList(0, 5);
		else return responseList.subList(0, responseList.size());
	}

	private List<ItineraryUtil> mergeTrips(List<TrainTripUtil> firstList, List<TrainTripUtil> secondList,List<TrainTripUtil> thirdList,Date tripDate) {
		List<ItineraryUtil> responseList = new ArrayList<>();
		List<TrainTripUtil> tempItinerary;
		ItineraryUtil itineraryUtil;
		//System.out.println(firstList.size());
		//System.out.println(secondList.size());
		//System.out.println(thirdList.size());
		for (TrainTripUtil first : firstList) {
			for (TrainTripUtil second : secondList) {
				//System.out.println("firstArrival -" + first.getArrivalTime());
				//System.out.println("SecondDeparture -" + second.getDepartureTime());
				if (Time.valueOf(first.getArrivalTime() + ":00").getTime() <= Time.valueOf(second.getDepartureTime() + ":00").getTime()) {
					for (TrainTripUtil third : thirdList) {
						if (Time.valueOf(second.getArrivalTime() + ":00").getTime() <= Time.valueOf(third.getDepartureTime() + ":00").getTime()) {
							tempItinerary = new ArrayList<TrainTripUtil>();
							tempItinerary.add(first);
							tempItinerary.add(second);
							tempItinerary.add(third);
							itineraryUtil = new ItineraryUtil(tempItinerary,tripDate);
							responseList.add(itineraryUtil);

						}
					}
				}
			}
		}

		Collections.sort(responseList);
		if (responseList.size()>=5) return responseList.subList(0, 5);
		else return responseList.subList(0, responseList.size());
	}


	@RequestMapping(path = "/train",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ItineraryUtil> searchTrains(@RequestParam(value = "depStation", required = false) String depStation,
								@RequestParam(value = "arrStation", required = false) String arrStation,
								@RequestParam(value = "type", required = false) String type,
								@RequestParam(value = "depTime", required = false) String depTime,
								// @RequestParam(value = "arrTime", required = false) String arrTime
								@RequestParam(value = "exactTimeFalg", required = false) boolean exactTimeFalg,
								@RequestParam(value = "NumofConnections", required = false) String NumofConnections,
								@RequestParam(value =  "tripDate") String tripDateString

	) throws ParseException {

/*
		String depStation = "A";
		String arrStation = "F";
		String depTime = "06:10";
		String arrTime = null;
		String type = "Express";

*/


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed = format.parse(tripDateString);
		java.sql.Date tripDate = new java.sql.Date(parsed.getTime());

		//System.out.println(tripDate);

		//java.sql.Date tripDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());


		Time departureTime = Time.valueOf(depTime + ":00");
		LocalTime depLocalTime = departureTime.toLocalTime();
		int depTimeFactor = 0;
		int arrTimeFactor = 0;
		int priceFactor = 0;
		char startSation = 'A';
		//boolean exactTimeFalg = false;

		int connections = 0; //NumofConnections = None
		if (NumofConnections.equals("Any")) connections = 2;
		else if (NumofConnections.equals("One")) connections = 1;


		String expressStations = "AFKPUZ";

		//NorthBound check
		if (depStation.charAt(0) > arrStation.charAt(0)) {
			startSation = 'Z';
			expressStations = "ZUPKFA";
		}

		List<TrainTripUtil> responseTrips;
		List<List<TrainTripUtil>> response = new ArrayList();
		List<ItineraryUtil> responseNew = new ArrayList();
		List<TrainTripUtil> Itinerary = new ArrayList<TrainTripUtil>();

		if ((type.equals("Regular") || type.equals("Any")) && (NumofConnections.equals("Any") || NumofConnections.equals("None"))) {

			responseTrips = getRegularTrainTrips(depTime, depStation, arrStation,startSation,tripDate, exactTimeFalg, 5);
			for (TrainTripUtil t : responseTrips) {
				List<TrainTripUtil> regularItinerary = new ArrayList<TrainTripUtil>();
				regularItinerary.add(t);
				//response.add(regularItinerary);
				ItineraryUtil itineraryUtil = new ItineraryUtil(regularItinerary,tripDate);
				responseNew.add(itineraryUtil);
			}
			//response.add(Itinerary);
			//return response;
			//return responseNew;

		}

		if (type.equals("Express") || type.equals("Any")) {
			String interimDepStation = "";
			String interimArrStation = "";

			if (expressStations.contains(depStation) && expressStations.contains(arrStation)) {
				responseTrips = getExpressTrainTrips(depTime, depStation, arrStation, startSation,tripDate, exactTimeFalg, 5);
				for (TrainTripUtil t : responseTrips) {
					List<TrainTripUtil> bothExpressItinerary = new ArrayList<TrainTripUtil>();
					bothExpressItinerary.add(t);
					//response.add(bothExpressItinerary);
					ItineraryUtil itineraryUtil = new ItineraryUtil(bothExpressItinerary,tripDate);
					responseNew.add(itineraryUtil);
				}
				//Itinerary.add(responseTrips.get(0));
				//response.add(Itinerary);
				//return response;
			} else if (connections > 0 && expressStations.contains(depStation)) {

				for (int i = 1; i < expressStations.length(); i++) {
					if (startSation == 'A' && expressStations.charAt(i) > arrStation.charAt(0)) {
						interimArrStation = String.valueOf(expressStations.charAt(i - 1));
						break;
					} else if (startSation == 'Z' && expressStations.charAt(i) < arrStation.charAt(0)) {
						interimArrStation = String.valueOf(expressStations.charAt(i - 1));
						break;
					}
				}
				//System.out.println(interimArrStation);

				if ((startSation == 'A' && interimArrStation.charAt(0) < arrStation.charAt(0) && depStation.charAt(0) != interimArrStation.charAt(0))
						|| (startSation == 'Z' && interimArrStation.charAt(0) > arrStation.charAt(0) && depStation.charAt(0) != interimArrStation.charAt(0))) {
					//dep -> interimArr -> arr
					List<TrainTripUtil> firstTrips = getExpressTrainTrips(depTime, depStation, interimArrStation, startSation,tripDate, exactTimeFalg, 2);
					if (!firstTrips.isEmpty()) {
						List<TrainTripUtil> secondTrips = getRegularTrainTrips(firstTrips.get(0).getArrivalTime(), interimArrStation, arrStation, startSation,tripDate, false, 5);
						//Itinerary.add(firstTrips.get(0));
						//Itinerary.add(secondTrips.get(0));
						//ItineraryUtil itineraryUtil = new ItineraryUtil(Itinerary);
						//responseNew.add(itineraryUtil);

						List<ItineraryUtil> itineraryUtilList;
						itineraryUtilList = mergeTrips(firstTrips, secondTrips,tripDate);
						for (ItineraryUtil i : itineraryUtilList) {
							responseNew.add(i);
						}

					}
				}
			} else if (connections > 0 && expressStations.contains(arrStation)) {

				for (int i = 1; i < expressStations.length(); i++) {
					if (startSation == 'A' && expressStations.charAt(i) > depStation.charAt(0)) {
						interimDepStation = String.valueOf(expressStations.charAt(i));
						break;
					} else if (startSation == 'Z' && expressStations.charAt(i) < depStation.charAt(0)) {
						interimDepStation = String.valueOf(expressStations.charAt(i));
						break;
					}
				}

				//System.out.println(interimDepStation);

				if ((startSation == 'A' && interimDepStation.charAt(0) > depStation.charAt(0) && arrStation.charAt(0) != interimDepStation.charAt(0))
						|| (startSation == 'Z' && interimDepStation.charAt(0) < depStation.charAt(0) && arrStation.charAt(0) != interimDepStation.charAt(0))) {
					//dep -> interimDep -> arr
					List<TrainTripUtil> firstTrips = getRegularTrainTrips(depTime, depStation, interimDepStation, startSation,tripDate, exactTimeFalg, 5);
					if (!firstTrips.isEmpty()) {
						List<TrainTripUtil> secondTrips = getExpressTrainTrips(firstTrips.get(0).getArrivalTime(), interimDepStation, arrStation, startSation,tripDate, false, 2);
						//Itinerary.add(firstTrips.get(0));
						//Itinerary.add(secondTrips.get(0));
						//response.add(Itinerary);
						//return response;

						//ItineraryUtil itineraryUtil = new ItineraryUtil(Itinerary);
						//responseNew.add(itineraryUtil);

						List<ItineraryUtil> itineraryUtilList;
						itineraryUtilList = mergeTrips(firstTrips, secondTrips,tripDate);
						for (ItineraryUtil i : itineraryUtilList) {
							responseNew.add(i);
						}
					}
				}
			}

				else if (connections == 2) {
					for (int i = 1; i < expressStations.length(); i++) {
						if (startSation == 'A' && expressStations.charAt(i) > depStation.charAt(0)) {
							interimDepStation = String.valueOf(expressStations.charAt(i));
							break;
						} else if (startSation == 'Z' && expressStations.charAt(i) < depStation.charAt(0)) {
							interimDepStation = String.valueOf(expressStations.charAt(i));
							break;
						}
					}
					for (int i = 1; i < expressStations.length(); i++) {
						if (startSation == 'A' && expressStations.charAt(i) > arrStation.charAt(0)) {
							interimArrStation = String.valueOf(expressStations.charAt(i - 1));
							break;
						} else if (startSation == 'Z' && expressStations.charAt(i) < arrStation.charAt(0)) {
							interimArrStation = String.valueOf(expressStations.charAt(i - 1));
							break;
						}
					}

					if ((startSation == 'A' && interimArrStation.charAt(0) > interimDepStation.charAt(0)) ||
							(startSation == 'Z' && interimArrStation.charAt(0) < interimDepStation.charAt(0))) {
						//dep -> interimDep -> interimArr -> arr
						List<TrainTripUtil> firstTrips = getRegularTrainTrips(depTime, depStation, interimDepStation, startSation,tripDate, exactTimeFalg, 5);
						List<TrainTripUtil> secondTrips;
						List<TrainTripUtil> thirdTrips;
						if (!firstTrips.isEmpty()) {
							secondTrips = getExpressTrainTrips(firstTrips.get(0).getArrivalTime(), interimDepStation, interimArrStation, startSation,tripDate, false, 2);

							if (!secondTrips.isEmpty()) {
								thirdTrips = getRegularTrainTrips(secondTrips.get(0).getArrivalTime(), interimArrStation, arrStation, startSation,tripDate, false, 5);


								//Itinerary.add(firstTrips.get(0));
								//Itinerary.add(secondTrips.get(0));
								//Itinerary.add(thirdTrips.get(0));
								//response.add(Itinerary);
								//return response;

								//ItineraryUtil itineraryUtil = new ItineraryUtil(Itinerary);
								//responseNew.add(itineraryUtil);
								List<ItineraryUtil> itineraryUtilList;
								itineraryUtilList = mergeTrips(firstTrips, secondTrips,thirdTrips,tripDate);
								for (ItineraryUtil i : itineraryUtilList) {
									responseNew.add(i);
								}

							}
						}

					}
				}
			}

		Collections.sort(responseNew);
		return responseNew;
	}




}
