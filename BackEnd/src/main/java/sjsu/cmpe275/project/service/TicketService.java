package sjsu.cmpe275.project.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import sjsu.cmpe275.project.dao.OneWayTripDao;
import sjsu.cmpe275.project.dao.CompleteTicketDao;
import sjsu.cmpe275.project.dao.TrainDao;
import sjsu.cmpe275.project.dao.TrainDetailsDao;
import sjsu.cmpe275.project.entity.CompleteTicket;
import sjsu.cmpe275.project.entity.OneWayTrip;
import sjsu.cmpe275.project.entity.ReturnTrip;
import sjsu.cmpe275.project.entity.TrainDetails;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@EnableAsync
@CrossOrigin(origins = "*")
@RestController
public class TicketService {

	@Autowired
	private TrainDao trainDao;

	@Autowired
	private CompleteTicketDao completeTicketDao;

	@Autowired
	private OneWayTripDao oneWayTripDao;

	@Autowired
	private TrainDetailsDao trainDetailsDao;

	@Autowired
	private JavaMailSender sender;

	@Async
	public void sendEmail(String emailTo, CompleteTicket completeTicket, long tid) {
		try {
			MimeMessage mimeMessage = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

			StringBuilder htmlBuilder = new StringBuilder();
			//htmlBuilder.append("<html>");
			htmlBuilder.append("<!DOCTYPE html>\n" +
					"<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
					"  <head>\n" +
					"    <title th:remove=\"all\">Template for HTML email with inline image</title>\n" +
					"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
					"  </head>\n" +
					"  <body>");
			htmlBuilder.append("<table align=\"center\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"800\">");
			htmlBuilder.append("<tr><td style=\"font-size: 0; line-height: 0;\" height=\"10\">&nbsp;</td></tr>");

			htmlBuilder.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
					"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
					"<head>\n" +
					"    <title>Ticket Confirmation</title>\n" +
					"\n" +
					"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
					"\n" +
					"    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>\n" +
					"\n" +
					"    <!-- use the font -->\n" +
					"    <style>\n" +
					"        body {\n" +
					"            font-family: 'Roboto', sans-serif;\n" +
					"            font-size: 48px;\n" +
					"        }\n" +
					"    </style>\n" +
					"</head>\n" +
					"<body style=\"margin: 0; padding: 0;\">\n" +
					"\n" +
					"    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" +
					"        <tr>\n" +
					"            <td align=\"center\" bgcolor=\"#78ab46\" style=\"padding: 40px 0 30px 0;\">\n" +
					"                <img src=\"https://dummyimage.com/700x80/000/fff&text=California Ultra Speed Rail\" alt=\"www.cusr.com\" style=\"display: block;\" />\n" +
					"            </td>\n" +
					"        </tr>\n" +
					"        <tr>\n" +
					"            <td bgcolor=\"#eaeaea\" style=\"padding: 40px 10px 40px 10px;\">\n" +
					"                <p>Dear " +completeTicket.getPassengerNames()+",</p>\n" +
					"				 <p>Your Tickets have been confirmed!<p>"+
					"                <p>Below are your ticket details </p>\n" +
					"                <p>Thanks</p>\n" +
					"            </td>\n" +
					"        </tr>\n" +
					"    </table>\n" +
					"\n" +
					"</body>\n" +
					"</html>\n");

			htmlBuilder.append("<body><p> Confirmation Number: "+tid+"</p></body>");
			htmlBuilder.append("<body><p> Passenger Names: "+completeTicket.getPassengerNames()+"</p></body>");
			htmlBuilder.append("<body><p> Total Price: $"+completeTicket.getTotalTripPrice()+"</p></body>");


			List<OneWayTrip> oneWayTrips = completeTicket.getOneWayTrips();
			List<ReturnTrip> returnTrips = completeTicket.getReturnTrips();

			if( oneWayTrips != null) {
				htmlBuilder.append("<body><p> <b> OneWay Trips : <b></p></body>");
				htmlBuilder.append(" <table border=\"1\" cellpadding=\"1\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\">");
				htmlBuilder.append("<tr><td>Train Name</td><td>Departure Station</td><td>Arrival Station</td><td>Departure Time</td><td>Arrival Time</td>");
				htmlBuilder.append("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
				for (OneWayTrip oneWayTrip : oneWayTrips) {
					htmlBuilder.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
							oneWayTrip.getName(),oneWayTrip.getDepStation(), oneWayTrip.getArrStation(),oneWayTrip.getDepartureTime(),oneWayTrip.getArrivalTime()));
				}
				htmlBuilder.append("</table>");
			}

			if( returnTrips != null) {
				htmlBuilder.append("<body><p> <b>Return Trips : <b> </p></body>");
				htmlBuilder.append(" <table border=\"1\" cellpadding=\"1\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\">");
				htmlBuilder.append("<tr><td>Train Name</td><td>Departure Station</td><td>Arrival Station</td><td>Departure Time</td><td>Arrival Time</td></tr>");
				htmlBuilder.append("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
				for (ReturnTrip returnTrip : returnTrips) {
					htmlBuilder.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
							returnTrip.getName(),returnTrip.getDepStation(), returnTrip.getArrStation(),returnTrip.getDepartureTime(),returnTrip.getArrivalTime()));
				}
				htmlBuilder.append("</table>");
			}

			htmlBuilder.append("        <tr>\n" +
					"            <td bgcolor=\"#78ab46\" style=\"padding: 10px 20px 10px 20px;\">\n" +
					"                <p>© California Ultra Speed Rail</p>\n" +
					"                <p>San Jose</p>\n" +
					"            </td>\n" +
					"        </tr>\n" );


			htmlBuilder.append("<p>\n" +
					"      <img src=\"https://dummyimage.com/100x50/000/fff&text=CUSR\" />\n" +
					"    </p>\n" +
					"    <p>");
			htmlBuilder.append("</html>");
			mimeMessage.setContent(htmlBuilder.toString(), "text/html");
			helper.setFrom("no-reply@cusr.us");
			helper.setTo(emailTo);
			helper.setSubject("CUSR Ticket Booking Confirmation");


			this.sender.send(mimeMessage);
		}
		catch (Exception ex) {
			//Logger.getLogger(HTMLMail.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Async
	public void sendCancelEmail(String emailTo, CompleteTicket completeTicket,long ticketId) {

		try {
			MimeMessage mimeMessage = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

			StringBuilder htmlBuilder = new StringBuilder();
			//htmlBuilder.append("<html>");
			htmlBuilder.append("<!DOCTYPE html>\n" +
					"<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
					"  <head>\n" +
					"    <title th:remove=\"all\"></title>\n" +
					"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
					"  </head>\n" +
					"  <body>");
			htmlBuilder.append("<table align=\"center\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"800\">");
			htmlBuilder.append("<tr><td style=\"font-size: 0; line-height: 0;\" height=\"10\">&nbsp;</td></tr>");

			htmlBuilder.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
					"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
					"<head>\n" +
					"    <title>Ticket Cancellation</title>\n" +
					"\n" +
					"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
					"\n" +
					"    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>\n" +
					"\n" +
					"    <!-- use the font -->\n" +
					"    <style>\n" +
					"        body {\n" +
					"            font-family: 'Roboto', sans-serif;\n" +
					"            font-size: 48px;\n" +
					"        }\n" +
					"    </style>\n" +
					"</head>\n" +
					"<body style=\"margin: 0; padding: 0;\">\n" +
					"\n" +
					"    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" +
					"        <tr>\n" +
					"            <td align=\"center\" bgcolor=\"#78ab46\" style=\"padding: 40px 0 30px 0;\">\n" +
					"                <img src=\"https://dummyimage.com/700x80/000/fff&text=California Ultra Speed Rail\" alt=\"www.cusr.com\" style=\"display: block;\" />\n" +
					"            </td>\n" +
					"        </tr>\n" +
					"        <tr>\n" +
					"            <td bgcolor=\"#eaeaea\" style=\"padding: 40px 30px 40px 30px;\">\n" +
					"                <p>Dear " +completeTicket.getPassengerNames()+",</p>\n" +
					"				 <p>Your Ticket with Id: "+ ticketId +" is cancelled.<p>"+
					"                <p>Please re-book your ticket if needed.</p>\n" +
					"                <p>Thanks</p>\n" +
					"            </td>\n" +
					"        </tr>\n" +
					"    </table>\n" +
					"\n" +
					"</body>\n" +
					"</html>\n");

			htmlBuilder.append("        <tr>\n" +
					"            <td bgcolor=\"#777777\" style=\"padding: 0px 0px 0px 0px;\">\n" +
					"                <p>© California Ultra Speed Rail</p>\n" +
					"                <p>San Jose</p>\n" +
					"            </td>\n" +
					"        </tr>\n" );

			htmlBuilder.append("<p>\n" +
					"      <img src=\"https://dummyimage.com/100x50/000/fff&text=CUSR\" />\n" +
					"    </p>\n" +
					"    <p>");
			htmlBuilder.append("</html>");
			mimeMessage.setContent(htmlBuilder.toString(), "text/html");
			helper.setFrom("no-reply@cusr.us");
			helper.setTo(emailTo);
			helper.setSubject("CUSR Ticket Cancellation Confirmation");


			this.sender.send(mimeMessage);
		}
		catch (Exception ex) {
			//Logger.getLogger(HTMLMail.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@ResponseBody
	@RequestMapping(path = "/ticket", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createTicket(@RequestBody CompleteTicket completeTicket) {

		//System.out.println(completeTicket.getUserName());
		//System.out.println(completeTicket.getOneWayTrips());
		List<OneWayTrip> oneWayTrips = completeTicket.getOneWayTrips();
		List<ReturnTrip> returnTrips = completeTicket.getReturnTrips();
		TrainDetails oneWayTrainDetails;
		TrainDetails returnTrainDetails;
		if( oneWayTrips != null) {
			for (OneWayTrip oneWayTrip : oneWayTrips) {
				oneWayTrip.setTicketOneWay(completeTicket);
				oneWayTrainDetails = trainDetailsDao.findTopByTrainNameEqualsAndDateEquals(oneWayTrip.getName(),completeTicket.getDepDate());
				oneWayTrainDetails.decrementAvailableSeats();
				trainDetailsDao.save(oneWayTrainDetails);
			}
			completeTicket.setDepStation(oneWayTrips.get(0).getDepStation());
			completeTicket.setArrStation(oneWayTrips.get(oneWayTrips.size()-1).getArrStation());
		}

		if(returnTrips !=null) {
			for (ReturnTrip returnTrip : returnTrips) {
				returnTrip.setTicketReturn(completeTicket);
				returnTrainDetails = trainDetailsDao.findTopByTrainNameEqualsAndDateEquals(returnTrip.getName(),completeTicket.getArrDate());
				returnTrainDetails.decrementAvailableSeats();
				trainDetailsDao.save(returnTrainDetails);
			}
		}


		completeTicket.setBookingDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));

		CompleteTicket ticketGenerated = completeTicketDao.saveAndFlush(completeTicket);
		//System.out.println(ticketGenerated.getId());

		Map<String, Object> result = new HashMap<String,Object>();

		result.put("ticket_id", ticketGenerated.getId());
		result.put("booking_date",ticketGenerated.getBookingDate());
		sendEmail(completeTicket.getUserName(),completeTicket,ticketGenerated.getId());
		return new ResponseEntity<Map>(result, HttpStatus.CREATED);

	}

	@RequestMapping(path = "/ticket", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompleteTicket> searchUserTickets(@RequestParam(value = "userName", required = false) String userName) {

		List<CompleteTicket> completeTickets = completeTicketDao.findAllByUserNameEquals(userName);
		return completeTickets;

	}

	@RequestMapping(path = "/ticket/{ticket_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  CompleteTicket searchTicket(@PathVariable(value = "ticket_id") long ticketId) {

		CompleteTicket completeTicket = completeTicketDao.findById(ticketId);
		return completeTicket;

	}

	@RequestMapping(path = "/ticket/{ticket_id}/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cancelTicket(@PathVariable(value = "ticket_id") long ticketId) {

		CompleteTicket completeTicket = completeTicketDao.findById(ticketId);
		completeTicket.setCancelled(true);
		completeTicket.setAutomateRebook(false);
		completeTicketDao.save(completeTicket);
		sendCancelEmail(completeTicket.getUserName(),completeTicket,ticketId);

		Map<String, Object> result = new HashMap<String,Object>();

		result.put("ticket_id", completeTicket.getId());
		result.put("cancelled",completeTicket.isCancelled());
		return new ResponseEntity<Map>(result, HttpStatus.OK);

	}

	@RequestMapping(path = "/ticket/reset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resetTicket(@RequestParam(value = "capacity", required = true) int availableSeats) {
		//completeTicketDao.deleteAll();
		trainDetailsDao.resetTrains(availableSeats);
		return new ResponseEntity(HttpStatus.OK);
	}


}


/*

{
  "id": 15,
  "userName": "qqqqqq@gmail.com",
  "noOfPassengers": 2,
  "passengerNames": "Abhi,Huzaifa",
  "oneWayTripTime": 45,
  "returnTripTime": 60,
  "totalTripTime": 105,
  "totalTripPrice": 15,
  "bookingDate": "2017-12-18",
  "depDate": "2017-12-17",
  "arrDate": "2017-12-17",
  "cancelled": true,
  "oneWayTrips": [
    {
      "name": "SB0615",
      "departureTime": "06:23",
      "arrivalTime": "07:40",
      "price": 2,
      "depStation": "B",
      "arrStation": "L"
    }
  ],
  "returnTrips": [
    {
      "name": "SB0615",
      "departureTime": "06:23",
      "arrivalTime": "07:40",
      "price": 2,
      "depStation": "B",
      "arrStation": "L"
    }
  ]
}

 */