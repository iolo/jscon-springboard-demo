//import 'whatwg-fetch';
import "isomorphic-fetch";
import debug from "debug";

const trace = debug('springboard:ApiClient');

const DEF_CONFIG = {
    endpoint: 'http://localhost:9090/springboard/apis/v1'
    // TODO: authentication with api_key ...
};

function handleJsonResponse(res) {
    if (res.status < 200 && res.status >= 300) {
        let err = new Error(res.statusText);
        err.res = res;
        trace('failed to api request:', err);
        throw err;
    }
    return res.json();
}

export default class ApiClient {

    constructor(config) {
        this.config = Object.assign({}, DEF_CONFIG, config);
    }

    //---------------------------------------------------------

    getForums() {
        let url = this.config.endpoint + '/forums';
        return fetch(url).then(handleJsonResponse);
    }

    getForum(forumId) {
        let url = this.config.endpoint + '/forums/' + forumId;
        return fetch(url).then(handleJsonResponse);
    }

    //---------------------------------------------------------

    getPosts(forumId) {
        let url = this.config.endpoint + '/forums/' + forumId + '/posts';
        return fetch(url).then(handleJsonResponse);
    }

    getPost(postId) {
        let url = this.config.endpoint + '/posts/' + postId;
        return fetch(url).then(handleJsonResponse);
    }

    createPost(post) {
        let url = this.config.endpoint + '/posts';
        let req = {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json'
            },
            body: JSON.stringify(post)
        };
        return fetch(url, req).then(handleJsonResponse);
    }

    deletePost(postId) {
        let url = this.config.endpoint + '/posts/' + postId;
        let req = {
            method: 'DELETE'
        };
        return fetch(url, req);
    }

    //---------------------------------------------------------

    getComments(postId) {
        let url = this.config.endpoint + '/posts/' + postId + '/comments';
        return fetch(url).then(handleJsonResponse);
    }

    getComment(commentId) {
        let url = this.config.endpoint + '/comments/' + commentId;
        return fetch(url).then(handleJsonResponse);
    }

    createComment(comment) {
        let url = this.config.endpoint + '/comments';
        let req = {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json'
            },
            body: JSON.stringify(comment)
        };
        return fetch(url, req).then(handleJsonResponse);
    }

    deleteComment(commentId) {
        let url = this.config.endpoint + '/comments/' + commentId;
        let req = {
            method: 'DELETE'
        };
        return fetch(url, req);
    }

    //---------------------------------------------------------

    login(loginForm) {
        let url = this.config.endpoint + '/users/login';
        let req = {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json'
            },
            body: JSON.stringify(loginForm)
        };
        return fetch(url, req).then(handleJsonResponse).then(function (currentUser) {
            trace('login ok', currentUser);
            try {
                localStorage.setItem('currentUser', JSON.stringify(currentUser));
            } catch (e) {
                //trace(e);
            }
            return currentUser;
        });
    }

    logout() {
        try {
            localStorage.removeItem('currentUser');
        } catch (e) {
            //trace(e);
        }
        let url = this.config.endpoint + '/users/logout';
        return fetch(url);
    }

    signup(signupForm) {
        let url = this.config.endpoint + '/users/signup';
        let req = {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json'
            },
            body: JSON.stringify(signupForm)
        };
        return fetch(url, req).then(handleJsonResponse).then(function (currentUser) {
            trace('login ok', currentUser);
            try {
                localStorage.setItem('currentUser', JSON.stringify(currentUser));
            } catch (e) {
                //trace(e);
            }
            return currentUser;
        });
    }

    //---------------------------------------------------------

    getCurrentUser() {
        try {
            return JSON.parse(localStorage.getItem('currentUser'));
        } catch (e) {
            //trace(e);
            return false;
        }
    }

    //---------------------------------------------------------

};
