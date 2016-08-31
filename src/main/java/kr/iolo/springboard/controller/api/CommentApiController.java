package kr.iolo.springboard.controller.api;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author iolo
 */
@RestController
@RequestMapping("/apis/v1/comments")
//@Secured
public class CommentApiController {
    private final Springboard springboard;

    @Autowired
    public CommentApiController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Comment> getComments(@PageableDefault Pageable pageable) {
        return springboard.getComments(pageable).getContent();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Comment createComment(@RequestBody Comment comment) {
        return springboard.createComment(comment);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Comment getComment(@PathVariable("id") Long id) {
        return springboard.getComment(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        return springboard.updateComment(id, comment);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable("id") Long id) {
        springboard.deleteComment(id);
    }
}
