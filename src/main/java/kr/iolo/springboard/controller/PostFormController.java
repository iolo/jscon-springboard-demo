package kr.iolo.springboard.controller;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.entity.Forum;
import kr.iolo.springboard.entity.Post;
import kr.iolo.springboard.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PostFormController {
    private final Springboard springboard;

    @Autowired
    public PostFormController(Springboard springboard) {
        this.springboard = springboard;
    }

    @Data
    public static class PostForm {
        public Long forumId;
        public String title;
        public String content;
    }

    @RequestMapping("/post_form")
    public String postForm(
            @RequestParam("forumId") Long forumId,
            Model model,
            HttpSession session) {
        final User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        final PostForm postForm = new PostForm();
        postForm.forumId = forumId;
        model
                .addAttribute("postForm", postForm)
                .addAttribute("currentUser", currentUser);
        return "post_form";
    }

    @RequestMapping(value = "/post_save", method = RequestMethod.POST)
    public String postSave(
            @ModelAttribute PostForm postForm,
            Model model,
            HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        final Forum forum = springboard.getForum(postForm.forumId);
        final Post post = new Post();
        post.title = postForm.title;
        post.content = postForm.content;
        post.forum = forum;
        post.user = currentUser;
        final Post createdPost = springboard.createPost(post);
        return "redirect:/post_list?forumId=" + postForm.forumId + "#post_" + createdPost.id;
    }
}
