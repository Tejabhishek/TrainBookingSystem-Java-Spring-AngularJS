package sjsu.cmpe275.project.dao;

import sjsu.cmpe275.project.entity.Train;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Time;
import java.util.List;

/**
 * Created by shiva on 11/27/17.
 */

public class TrainDaoImpl implements TrainDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Train> getTrainsforDeparture(String DepartureStation, Time DepartureTime){

        TypedQuery<Train> query = entityManager.createQuery("FROM Train " +
                        "WHERE "+ DepartureStation + " >= :time", Train.class);


        query.setParameter("time", DepartureTime);
        System.out.println(query);
        List<Train> list = query.getResultList();
        //System.out.println(CollectionUtils.isEmpty(list) ? null : list);
        return list;
    }

}