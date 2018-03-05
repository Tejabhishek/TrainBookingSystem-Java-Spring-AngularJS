package sjsu.cmpe275.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sjsu.cmpe275.project.entity.Train;
import sjsu.cmpe275.project.entity.TrainDetails;

import java.sql.Date;
import java.util.List;

/**
 * Created by shiva on 12/17/17.
 */
public interface TrainDetailsDao extends CrudRepository<TrainDetails,Long> {

    TrainDetails findTopByTrainNameEqualsAndDateEquals(String trainName, Date date);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update TrainDetails td set td.availableSeats = :capacity, td.cancelled = false")
    void resetTrains(@Param("capacity") int availableSeats);
    //(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})

}
