package kr.iolo.springboard.controller.api;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.entity.Comment;
import kr.iolo.springboard.entity.Post;
import kr.iolo.springboard.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author iolo
 */
@RestController
@RequestMapping("/apis/v1/posts")
//@Secured
public class PostApiController {
    private final Springboard springboard;

    @Autowired
    public PostApiController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Post> getPost(@PageableDefault Pageable pageable) {
        return springboard.getPosts(pageable).getContent();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Post createPost(@RequestBody Post post) {
        return springboard.createPost(post);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable("id") Long id) {
        return springboard.getPost(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        return springboard.updatePost(id, post);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable("id") Long id) {
        springboard.deletePost(id);
    }

    //---------------------------------------------------------

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getComments(@PathVariable("id") Long id, @PageableDefault Pageable pageable) {
        return springboard.getComments(id, pageable).getContent();
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST)
    public Comment createComment(@PathVariable("id") Long id, @RequestBody Comment comment, HttpSession session) {
        comment.post = springboard.getPost(id);
        comment.user = (User) session.getAttribute("currentUser");
        return springboard.createComment(comment);
    }
}
