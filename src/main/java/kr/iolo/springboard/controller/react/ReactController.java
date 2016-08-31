package kr.iolo.springboard.controller.react;

import kr.iolo.springboard.Springboard;
import kr.iolo.springboard.j2v8.J2V8Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author iolo
 */
@Controller
@RequestMapping("/react")
public class ReactController {
    private final Springboard springboard;

    @Autowired
    public ReactController(Springboard springboard) {
        this.springboard = springboard;
    }

    @RequestMapping("/**")
    public DeferredResult<ModelAndView> react(HttpServletRequest request, HttpSession session) throws Exception {
////        // XXX: react-router 가 최초 로딩시 하는 짓을... 서버에서... 대충...
////        final User currentUser = (User) session.getAttribute("currentUser");
////        final ModelMap props = new ModelMap("currentUser", currentUser);
////        final String type;
////        if ("post_list".equals(route)) {
////            type = "PostList";
////            props.addAttribute("posts", springboard.getPosts(forumId, null));
////        } else if ("post_view".equals(route)) {
////            type = "PostView";
////            props.addAttribute("post", springboard.getPost(postId));
////        } else if ("post_form".equals(route)) {
////            type = "PostForm";
////            props.addAttribute("post", springboard.getPost(postId));
////        } else if ("login".equals(route)) {
////            type = "LoginForm";
////        } else if ("signup".equals(route)) {
////            type = "SignupForm";
////        } else { //if ("forum_list".equals(route)) {
////            type = "ForumList";
////            props.addAttribute("forums", springboard.getForums(null));
////        }
        // http://host:port/<context-path>/react/<browser-history-route>?queryString
        // --> <browser-history-route>?queryString
        //final String location = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)
        final String location = request.getRequestURI().substring(request.getContextPath().length() + "/react".length())
                + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        final DeferredResult<ModelAndView> deferredResult = new DeferredResult<>();
        J2V8Utils.renderReactRouter(location, (err, reactHtml) -> {
            if (err != null) {
                throw new RuntimeException(err); // 500!
            }
            deferredResult.setResult(new ModelAndView("react", "reactHtml", reactHtml));
        });
        return deferredResult;
    }

}
