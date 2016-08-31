package kr.iolo.springboard.controller.api;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.dto.LoginForm;
import kr.iolo.springboard.dto.SignupForm;
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
@RequestMapping("/apis/v1/users")
//@Secured
public class UserApiController {
    private final Springboard springboard;

    @Autowired
    public UserApiController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers(@PageableDefault Pageable pageable) {
        return springboard.getUsers(pageable).getContent();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody User comment) {
        return springboard.createUser(comment);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") Long id) {
        return springboard.getUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable("id") Long id, @RequestBody User comment) {
        return springboard.updateUser(id, comment);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Long id) {
        springboard.deleteUser(id);
    }

    //---------------------------------------------------------


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginForm loginForm, HttpSession session) {
        final User user = springboard.login(loginForm);
        session.setAttribute("currentUser", user);
        return user;
    }

    @RequestMapping(value = "/logout")
    public void logout(
            HttpSession session) {
        session.removeAttribute("currentUser");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User signup(@RequestBody SignupForm signupForm, HttpSession session) {
        final User user = springboard.signup(signupForm);
        session.setAttribute("currentUser", user);
        return user;
    }
}
