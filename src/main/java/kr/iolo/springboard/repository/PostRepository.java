package kr.iolo.springboard.repository;

import kr.iolo.springboard.entity.Forum;
import kr.iolo.springboard.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author iolo
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByForum(Forum forum, Pageable pageable);
}
