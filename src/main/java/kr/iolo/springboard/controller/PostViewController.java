package kr.iolo.springboard.controller;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.entity.Comment;
import kr.iolo.springboard.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostViewController {
    private final Springboard springboard;

    @Autowired
    public PostViewController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping("/post_view")
    public String postView(
            @RequestParam("postId") Long postId,
            @PageableDefault Pageable pageable,
            Model model,
            HttpSession session) {
        model
                .addAttribute("postId", postId)
                .addAttribute("currentUser", session.getAttribute("currentUser"));
        Post post = springboard.getPost(postId);
        if (post == null) {
            model.addAttribute("error", "post not found!");
        } else {
            List<Comment> comments = springboard.getComments(postId, pageable).getContent();
            model
                    .addAttribute("post", post)
                    .addAttribute("comments", comments);
        }
        return "post_view";
    }

}
