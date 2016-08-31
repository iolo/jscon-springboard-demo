package kr.iolo.springboard.repository;

import kr.iolo.springboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author iolo
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByUsernameAndPassword(String username, String password);
}
