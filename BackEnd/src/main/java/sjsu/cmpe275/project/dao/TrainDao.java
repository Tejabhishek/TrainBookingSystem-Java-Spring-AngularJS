package sjsu.cmpe275.project.dao;

import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import sjsu.cmpe275.project.entity.Train;

public interface TrainDao extends CrudRepository<Train,Long>,TrainDaoCustom {

	List<Train> findTrainsByTrainTypeEqualsAndStartTimeGreaterThanEqualAndOriginEqualsOrderByStartTime(String type,Time estimatedStartTime,String origin);
	List<Train> findTrainsByTrainTypeEqualsAndStartTimeEqualsAndOriginEqualsOrderByStartTime(String type,Time estimatedStartTime,String origin);

}
