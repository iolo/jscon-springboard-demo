import React from "react";
import {Route, IndexRoute, Redirect} from "react-router";
import App from "./app";
import Welcome from "./welcome";
import ForumList from "./forum_list";
import PostList from "./post_list";
import PostView from "./post_view";
import PostForm from "./post_form";
import SignupForm from "./signup_form";
import LoginForm from "./login_form";

export default (
    <Route path="/" component={App}>
        <IndexRoute component={Welcome}/>
        <Route path="forum_list" component={ForumList}/>
        <Route path="post_list" component={PostList}/>
        <Route path="post_view" component={PostView}/>
        <Route path="post_form" component={PostForm}/>
        <Route path="signup" component={SignupForm}/>
        <Route path="login" component={LoginForm}/>
        <Redirect from="*" to="/forum_list"/>
    </Route>
);
