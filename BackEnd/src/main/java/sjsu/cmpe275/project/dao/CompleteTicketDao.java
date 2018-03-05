package sjsu.cmpe275.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import sjsu.cmpe275.project.entity.CompleteTicket;

public interface CompleteTicketDao extends JpaRepository<CompleteTicket, Long> {

    CompleteTicket findById(long id);
    List<CompleteTicket> findAllByUserNameEquals(String userName);
    List<CompleteTicket> findAllByAutomateRebookEquals(boolean automateRebook);

}
