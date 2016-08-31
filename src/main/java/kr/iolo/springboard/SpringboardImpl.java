package kr.iolo.springboard;

import kr.iolo.springboard.dto.LoginForm;
import kr.iolo.springboard.dto.SignupForm;
import kr.iolo.springboard.entity.Comment;
import kr.iolo.springboard.entity.Forum;
import kr.iolo.springboard.entity.Post;
import kr.iolo.springboard.entity.User;
import kr.iolo.springboard.repository.CommentRepository;
import kr.iolo.springboard.repository.ForumRepository;
import kr.iolo.springboard.repository.PostRepository;
import kr.iolo.springboard.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("springboard")
public class SpringboardImpl implements Springboard {

    private final UserRepository userRepository;
    private final ForumRepository forumRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public SpringboardImpl(UserRepository userRepository, ForumRepository forumRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.forumRepository = forumRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    //-----------------------------------------------------

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = userRepository.findOne(id);
        BeanUtils.copyProperties(user, existing, "password");
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    //-----------------------------------------------------

    @Override
    public Page<Forum> getForums(Pageable pageable) {
        return forumRepository.findAll(pageable);
    }

    @Override
    public Forum getForum(Long id) {
        return forumRepository.findOne(id);
    }

    @Override
    public Forum createForum(Forum forum) {
        return forumRepository.save(forum);
    }

    @Override
    public Forum updateForum(Long id, Forum forum) {
        Forum existing = forumRepository.findOne(id);
        BeanUtils.copyProperties(forum, existing, "title");
        return forumRepository.save(forum);
    }

    @Override
    public void deleteForum(Long id) {
        forumRepository.delete(id);
    }

    //-----------------------------------------------------

    @Override
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getPosts(Long forumId, Pageable pageable) {
        return postRepository.findByForum(forumRepository.findOne(forumId), pageable);
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findOne(id);
    }

    @Override
    public Post createPost(Post post) {
//        post.forum = getForum(post.forum.id);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post post) {
        Post existing = postRepository.findOne(id);
        BeanUtils.copyProperties(post, existing, "title", "content");
        return postRepository.save(existing);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.delete(id);
    }

    //-----------------------------------------------------

    @Override
    public Page<Comment> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> getComments(Long postId, Pageable pageable) {
        return commentRepository.findByPost(postRepository.findOne(postId), pageable);
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findOne(id);
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Comment comment) {
        Comment existing = commentRepository.findOne(id);
        BeanUtils.copyProperties(comment, existing, "content");
        return commentRepository.save(existing);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }

    //---------------------------------------------------------

    @Override
    public User login(LoginForm loginForm) {
        return userRepository.findOneByUsernameAndPassword(loginForm.username, loginForm.password);
    }

    @Override
    public User signup(SignupForm signupForm) {
        final User user = new User();
        user.username = signupForm.username;
        user.password = signupForm.password;
        return userRepository.save(user);
    }
}
