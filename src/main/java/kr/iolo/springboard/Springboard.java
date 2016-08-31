package kr.iolo.springboard;

import kr.iolo.springboard.dto.LoginForm;
import kr.iolo.springboard.dto.SignupForm;
import kr.iolo.springboard.entity.Comment;
import kr.iolo.springboard.entity.Forum;
import kr.iolo.springboard.entity.Post;
import kr.iolo.springboard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Springboard {

    //-----------------------------------------------------

    Page<User> getUsers(Pageable pageable);

    User getUser(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    //-----------------------------------------------------

    Page<Forum> getForums(Pageable pageable);

    Forum getForum(Long id);

    Forum createForum(Forum forum);

    Forum updateForum(Long id, Forum forum);

    void deleteForum(Long id);

    //-----------------------------------------------------

    Page<Post> getPosts(Pageable pageable);

    Page<Post> getPosts(Long forumId, Pageable pageable);

    Post getPost(Long id);

    Post createPost(Post post);

    Post updatePost(Long id, Post post);

    void deletePost(Long id);

    //-----------------------------------------------------

    Page<Comment> getComments(Pageable pageable);

    Page<Comment> getComments(Long postId, Pageable pageable);

    Comment getComment(Long id);

    Comment createComment(Comment post);

    Comment updateComment(Long id, Comment post);

    void deleteComment(Long id);

    //---------------------------------------------------------

    User login(LoginForm login);

    User signup(SignupForm login);
}
