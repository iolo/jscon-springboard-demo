package kr.iolo.springboard.controller;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.entity.Comment;
import kr.iolo.springboard.entity.Forum;
import kr.iolo.springboard.entity.Post;
import kr.iolo.springboard.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostListController {
    private final Springboard springboard;

    @Autowired
    public PostListController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping("/post_list")
    public String postList(
            @RequestParam("forumId") Long forumId,
            @PageableDefault Pageable pageable,
            Model model,
            HttpSession session) {
        model
                .addAttribute("forumId", forumId)
                .addAttribute("currentUser", session.getAttribute("currentUser"));
        Forum forum = springboard.getForum(forumId);
        if (forum == null) {
            model.addAttribute("error", "forum not found");
        } else {
            List<Post> posts = springboard.getPosts(forumId, pageable).getContent();
            model
                    .addAttribute("forum", forum)
                    .addAttribute("posts", posts);
        }
        return "post_list";
    }

    @RequestMapping(value = "/comment_save", method = RequestMethod.POST)
    public String commentSave(
            @RequestParam("postId") Long postId,
            @RequestParam("content") String content,
            Model model,
            HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model
                .addAttribute("postId", postId)
                .addAttribute("currentUser", currentUser);
        Post post = springboard.getPost(postId);
        if (post == null) {
            model.addAttribute("error", "invalid post!");
            return "post_view";
        }
        model.addAttribute("post", post);
        Comment comment = new Comment();
        comment.user = currentUser;
        comment.post = post;
        comment.content = content;
        try {
            comment = springboard.createComment(comment);
            return "redirect:/post_view?postId=" + comment.post.id + "#comment_" + comment.id;
        } catch (Exception e) {
            model.addAttribute("error", "comment save error!");
            return "post_view";
        }
    }
}
