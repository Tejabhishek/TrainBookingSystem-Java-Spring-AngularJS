package sjsu.cmpe275.project.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.project.dao.CompleteTicketDao;
import sjsu.cmpe275.project.dao.TrainDetailsDao;
import sjsu.cmpe275.project.entity.CompleteTicket;
import sjsu.cmpe275.project.entity.OneWayTrip;
import sjsu.cmpe275.project.entity.ReturnTrip;
import sjsu.cmpe275.project.entity.TrainDetails;
import sjsu.cmpe275.project.util.ItineraryUtil;
import sjsu.cmpe275.project.util.TrainTripUtil;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shiva on 12/20/17.
 */
@CrossOrigin(origins = "*")
@RestController
public class AutoTicketService {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private CompleteTicketDao completeTicketDao;

    @Autowired
    private TrainDetailsDao trainDetailsDao;

    private void reBookTickets(List<CompleteTicket> reBookTickets){

        try {


            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            for (CompleteTicket ticket : reBookTickets) {

               // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String depTime = sdf.format(ticket.getOneWayTrips().get(0).getDepartureTime());

                List<ItineraryUtil> itineraryUtilList = trainService.searchTrains(ticket.getDepStation(), ticket.getArrStation(), "Regular", depTime, false,
                        "None", ticket.getDepDate().toString());
                if (itineraryUtilList != null && itineraryUtilList.size() > 0) {
                    List<OneWayTrip> oneWayTrips = new ArrayList<OneWayTrip>();
                    TrainTripUtil trip = itineraryUtilList.get(0).getTrips().get(0);

                    OneWayTrip oneWayTrip = new OneWayTrip(trip.getName(), Time.valueOf(trip.getDepartureTime() + ":00"),
                            Time.valueOf(trip.getArrivalTime() + ":00"), trip.getPrice(), trip.getDepStation(), trip.getArrStation());
                    oneWayTrips.add(oneWayTrip);
                    ticket.setOneWayTrips(oneWayTrips);
                }

                //book return tickets
                if (ticket.getReturnTrips() != null && ticket.getReturnTrips().size() > 0) {
                    String arrTime = sdf.format(ticket.getReturnTrips().get(0).getDepartureTime());

                    List<ItineraryUtil> itineraryUtilListforReturn = trainService.searchTrains(ticket.getArrStation(), ticket.getDepStation(), "Regular", arrTime, false,
                            "None", ticket.getArrDate().toString());
                    if (itineraryUtilListforReturn != null && itineraryUtilListforReturn.size() > 0) {
                        List<ReturnTrip> returnTrips = new ArrayList<ReturnTrip>();
                        TrainTripUtil trip = itineraryUtilListforReturn.get(0).getTrips().get(0);

                        ReturnTrip returnTrip = new ReturnTrip(trip.getName(), Time.valueOf(trip.getDepartureTime() + ":00"),
                                Time.valueOf(trip.getArrivalTime() + ":00"), trip.getPrice(), trip.getDepStation(), trip.getArrStation());
                        returnTrips.add(returnTrip);
                        ticket.setReturnTrips(returnTrips);
                    }
                }
                    //String arrTime = sdf.format(ticket.getOneWayTrips().get(0).getDepartureTime());
                    CompleteTicket newTicket = new CompleteTicket();
                    BeanUtils.copyProperties(ticket,newTicket);
                    newTicket.setId(0);
                    newTicket.setAutomateRebook(false);
                    ticket.setCancelled(true);
                    ticket.setAutomateRebook(false);
                    completeTicketDao.saveAndFlush(ticket);
                    ticketService.sendCancelEmail(ticket.getUserName(),ticket,ticket.getId());

                    ticketService.createTicket(newTicket);
                }
            }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(path = "/train/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelTrain(@RequestParam(value = "trainName") String trainName,
                                         @RequestParam(value = "tripDate") String tripDateString) throws ParseException{

        //CompleteTicket completeTicket = completeTicketDao.findById(ticketId);
        //completeTicket.setCancelled(true);
        //completeTicketDao.save(completeTicket);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = format.parse(tripDateString);
        java.sql.Date tripDate = new java.sql.Date(parsed.getTime());


        TrainDetails td = trainDetailsDao.findTopByTrainNameEqualsAndDateEquals(trainName,tripDate);
        td.setCancelled(true);
        trainDetailsDao.save(td);
        //Trigger updates automate rebook flag in train_ticket table

        List<CompleteTicket> reBookTicketsList = completeTicketDao.findAllByAutomateRebookEquals(true);
        reBookTickets(reBookTicketsList);
        //result.put("ticket_id", completeTicket.getId());
        //result.put("cancelled",completeTicket.isCancelled());
        return new ResponseEntity<Map>(HttpStatus.OK);

    }

    @RequestMapping(path = "/ticket/{ticket_id}/rebook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rebookTicket(@PathVariable(value = "ticket_id") long ticketId) {

        CompleteTicket completeTicket = completeTicketDao.findById(ticketId);
        completeTicket.setAutomateRebook(true);
        completeTicketDao.save(completeTicket);

        List<CompleteTicket> reBookTicketsList = new ArrayList<CompleteTicket>();
        reBookTicketsList.add(completeTicket);
        reBookTickets(reBookTicketsList);

        return new ResponseEntity<Map>(HttpStatus.OK);

    }




}
