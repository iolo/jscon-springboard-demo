package kr.iolo.springboard.controller;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.entity.Forum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ForumListController {
    private final Springboard springboard;

    @Autowired
    public ForumListController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping("/forum_list")
    public String forumList(
            @PageableDefault Pageable pageable,
            Model model, HttpSession session) {
        List<Forum> forums = springboard.getForums(pageable).getContent();
        model
                .addAttribute("forums", forums)
                .addAttribute("currentUser", session.getAttribute("currentUser"));
        return "forum_list";
    }

}
