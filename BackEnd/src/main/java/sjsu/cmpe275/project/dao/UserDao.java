package sjsu.cmpe275.project.dao;

import org.springframework.data.repository.CrudRepository;
import sjsu.cmpe275.project.entity.User;

public interface UserDao extends CrudRepository<User,Long> {
	User getById(long id);
	User getByLogin(String login);
}
