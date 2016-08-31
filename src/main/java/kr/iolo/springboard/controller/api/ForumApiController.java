package kr.iolo.springboard.controller.api;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.entity.Forum;
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
@RequestMapping("/apis/v1/forums")
//@Secured
public class ForumApiController {
    private final Springboard springboard;

    @Autowired
    public ForumApiController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Forum> getForums(@PageableDefault Pageable pageable) {
        return springboard.getForums(pageable).getContent();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Forum createForum(@RequestBody Forum comment) {
        return springboard.createForum(comment);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Forum getForum(@PathVariable("id") Long id) {
        return springboard.getForum(id);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Forum updateForum(@PathVariable("id") Long id, @RequestBody Forum comment) {
        return springboard.updateForum(id, comment);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteForum(@PathVariable("id") Long id) {
        springboard.deleteForum(id);
    }

    //---------------------------------------------------------

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public List<Post> getPosts(@PathVariable("id") Long id, @PageableDefault Pageable pageable) {
        return springboard.getPosts(id, pageable).getContent();
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.POST)
    public Post createPost(@PathVariable("id") Long id, @RequestBody Post post, HttpSession session) {
        post.forum = springboard.getForum(id);
        post.user = (User) session.getAttribute("currentUser");
        return springboard.createPost(post);
    }
}
