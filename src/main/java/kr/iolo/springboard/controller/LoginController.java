package kr.iolo.springboard.controller;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.dto.LoginForm;
import kr.iolo.springboard.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final Springboard springboard;

    @Autowired
    public LoginController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "next", defaultValue = "/forum_list") String next,
            Model model) {
        final LoginForm loginForm = new LoginForm();
        loginForm.next = next;
        model.addAttribute("loginForm", loginForm);
        return "login";
    }

    @RequestMapping(value = "/login_post", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute LoginForm loginForm, HttpSession session) {
        User user = springboard.login(loginForm);
        if (user != null) {
            session.setAttribute("currentUser", user);
            return "redirect:" + loginForm.next;
        }
        session.removeAttribute("currentUser");
        // TODO: robust validation & error handling
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(
            @RequestParam(value = "next", defaultValue = "/forum_list") String next,
            HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:" + next;
    }
}
