package sjsu.cmpe275.project.dao;

import sjsu.cmpe275.project.entity.Train;

import java.sql.Time;
import java.util.List;

/**
 * created by shiva on 11/28/17.
 */
public interface TrainDaoCustom  {
    public List<Train> getTrainsforDeparture(String departurestation, Time departuretime);
}
