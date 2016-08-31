package kr.iolo.springboard.controller;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.dto.SignupForm;
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
public class SignupController {
    private final Springboard springboard;

    @Autowired
    public SignupController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(
            @RequestParam(value = "next", defaultValue = "/forum_list") String next,
            Model model) {
        final SignupForm signupForm = new SignupForm();
        signupForm.next = next;
        model.addAttribute("signupForm", signupForm);
        return "signup";
    }

    @RequestMapping(value = "/signup_post", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute SignupForm signupForm, HttpSession session) {
        final User user = springboard.signup(signupForm);
        if (user != null) {
            session.setAttribute("currentUser", user);
            return "redirect:" + signupForm.next;
        }
        session.removeAttribute("currentUser");
        // TODO: robust validation & error handling
        return "signup";
    }
}
