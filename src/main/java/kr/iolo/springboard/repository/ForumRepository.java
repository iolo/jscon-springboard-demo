package kr.iolo.springboard.repository;

import kr.iolo.springboard.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author iolo
 */
public interface ForumRepository extends JpaRepository<Forum, Long> {
}
