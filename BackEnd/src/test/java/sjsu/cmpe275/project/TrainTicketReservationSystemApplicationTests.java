package sjsu.cmpe275.project;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sjsu.cmpe275.project.TrainTicketReservationSystemApplication;
import sjsu.cmpe275.project.service.TrainService;
import sjsu.cmpe275.project.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TrainTicketReservationSystemApplication.class)
@WebAppConfiguration
public class TrainTicketReservationSystemApplicationTests {

	@Autowired
	private TrainService trainService;
	
	@Autowired
	private TicketService ticketService;
	
	private final Date dateFrom = DateUtils.round(DateUtils.addDays(new Date(), 1),Calendar.DATE);
	private final Date dateTo = DateUtils.round(DateUtils.addDays(new Date(), 2),Calendar.DATE);
	/*
	@Test
	public void testSearchFlight() {
		Map<String, Collection<Train>> searchFlights = trainService.searchFlights(1, 2, dateFrom, dateTo);
		Assert.assertFalse("Search Flights", searchFlights.isEmpty());
	}
	
	@Test
	public void testBookFlight() {
		Map<String, Collection<Train>> searchFlights = trainService.searchFlights(1, 2, dateFrom, dateTo);
		
		Train flight = searchFlights.get("oneway").iterator().next();
		
		SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("user","credential"));
		
		Collection<Ticket> bookFlight = ticketService.bookFlight(flight.getId(), null, 1, 0, 0);
		Assert.assertFalse("Search Flights", bookFlight.isEmpty());
	}
*/
}
