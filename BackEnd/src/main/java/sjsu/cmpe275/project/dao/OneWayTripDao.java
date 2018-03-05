/**
 * 
 */
package sjsu.cmpe275.project.dao;

import org.springframework.data.repository.CrudRepository;

import sjsu.cmpe275.project.entity.CompleteTicket;
import sjsu.cmpe275.project.entity.OneWayTrip;

/**
 * @author tejabhishek
 *
 */
public interface OneWayTripDao extends CrudRepository<OneWayTrip, Long> {

}
