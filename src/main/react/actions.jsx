import dispatcher from "./dispatcher";
import debug from "debug";
import ApiClient from "./api_client";

const trace = debug('springboard:dispatcher');

const apiClient = new ApiClient();

export const TYPES = {
    GET_FORUMS: 'GET_FORUMS',
    GET_FORUM: 'GET_FORUM',
    GET_POSTS: 'GET_POSTS',
    GET_POST: 'GET_POST',
    DELETE_POST: 'DELETE_POST',
    SAVE_POST: 'SAVE_POST',
    GET_COMMENTS: 'GET_COMMENTS',
    GET_COMMENT: 'GET_COMMENT',
    DELETE_COMMENT: 'DELETE_COMMENT',
    SAVE_COMMENT: 'SAVE_COMMENT',
    LOGIN: 'LOGIN',
    LOGOUT: 'LOGOUT',
    SIGNUP: 'SIGNUP',
};
//Object.keys(TYPES).forEach((key) => TYPES[key] = key);

export function getForums() {
    trace('GET_FORUMS');
    return apiClient.getForums().then(function (forums) {
        return dispatcher.dispatch({type: TYPES.GET_FORUMS, forums: forums});
    });
}

export function getForum(forumId) {
    trace('GET_FORUM');
    return apiClient.getForum(forumId).then(function (forum) {
        return dispatcher.dispatch({type: TYPES.GET_FORUM, forum: forum});
    });
}

export function getPosts(forumId) {
    trace('GET_POSTS: forumId=' + forumId);
    return Promise.all([
        apiClient.getForum(forumId),
        apiClient.getPosts(forumId)
    ]).then(function (result) {
        return dispatcher.dispatch({type: TYPES.GET_POSTS, forum: result[0], posts: result[1]});
    });
}

export function getPost(postId) {
    trace('GET_POST: postId=' + postId);
    return Promise.all([
        apiClient.getPost(postId),
        apiClient.getComments(postId)
    ]).then(function (result) {
        return dispatcher.dispatch({type: TYPES.GET_POST, post: result[0], comments: result[1]});
    });
}

export function deletePost(postId) {
    trace('DELETE_POST: postId=' + postId);
    return apiClient.deletePost(postId).then(function () {
        return dispatcher.dispatch({type: TYPES.DELETE_POST, postId: postId});
    });
}

export function savePost(post) {
    trace('SAVE_POST: post=' + post);
    // TODO: create or update!
    return apiClient.createPost(post).then(function (post) {
        return dispatcher.dispatch({type: TYPES.SAVE_POST, post: post});
    });
}

export function getComments(postId) {
    trace('GET_COMMENTS');
    return apiClient.getComments(postId).then(function (comments) {
        return dispatcher.dispatch({type: TYPES.GET_COMMENTS, comments: comments});
    });
}

export function getComment(commentId) {
    trace('GET_COMMENT');
    return apiClient.getComment(commentId).then(function (comment) {
        return dispatcher.dispatch({type: TYPES.GET_COMMENT, comment: comment});
    });
}

export function deleteComment(commentId) {
    trace('DELETE_COMMENT: commentId=' + commentId);
    return apiClient.deleteComment(commentId).then(function () {
        return dispatcher.dispatch({type: TYPES.DELETE_COMMENT, commentId: commentId});
    });
}

export function saveComment(comment) {
    trace('SAVE_COMMENT: comment=' + comment);
    // TODO: create or update!
    return apiClient.createComment(comment).then(function (comment) {
        return dispatcher.dispatch({type: TYPES.SAVE_COMMENT, comment: comment});
    });
}

export function login(loginForm) {
    trace('LOGIN: loginForm=' + loginForm);
    // TODO: refresh auth token
    if (!loginForm) {
        let currentUser = apiClient.getCurrentUser();
        if (currentUser) {
            trace('login via localStorage ok');
            return Promise.resolve(dispatcher.dispatch({type: TYPES.LOGIN, currentUser: currentUser}));
        }
        return logout();
    }
    return apiClient.login(loginForm).then(function (currentUser) {
        return dispatcher.dispatch({type: TYPES.LOGIN, currentUser: currentUser});
    });
}

export function logout() {
    trace('LOGOUT');
    return apiClient.logout().then(function () {
        return dispatcher.dispatch({type: TYPES.LOGOUT});
    });
}

export function signup(signupForm) {
    trace('SIGNUP: signupForm=' + signupForm);
    return apiClient.signup(signupForm).then(function (currentUser) {
        return dispatcher.dispatch({type: TYPES.SIGNUP, currentUser: currentUser});
    });
}
