import debug from "debug";
import dispatcher from "./dispatcher";
import {TYPES} from "./actions";

const trace = debug('springboard:store');

const _changeListeners = [];

export const store = {};

export function emitChange() {
    _changeListeners.forEach(function (listener) {
        listener();
        //setTimeout(listener, 1);
    });
}

export function addChangeListener(listener) {
    _changeListeners.push(listener);
}

export function removeChangeListener(listener) {
    _changeListeners.filter(function (l) {
        return listener !== l;
    });
}

dispatcher.register(function (action) {
    trace('dispatch action:', action);
    switch (action.type) {
        case TYPES.GET_FORUMS:
            store.forums = action.forums;
            emitChange();
            break;
        case TYPES.GET_FORUM:
            store.forum = action.forum;
            emitChange();
            break;
        case TYPES.GET_POSTS:
            store.forum = action.forum;
            store.posts = action.posts;
            emitChange();
            break;
        case TYPES.GET_POST:
            store.post = action.post;
            store.comments = action.comments;
            emitChange();
            break;
        case TYPES.DELETE_POST:
            store.posts = store.posts.filter((post) => post.id == action.postId);
            delete store.post;
            emitChange();
            break;
        case TYPES.SAVE_POST:
            // TODO: create or update!
            store.posts = [].concat(action.post, store.posts);
            delete store.post;
            emitChange();
            break;
        case TYPES.GET_COMMENTS:
            store.comments = action.comments;
            emitChange();
            break;
        case TYPES.GET_COMMENT:
            store.comment = action.comment;
            emitChange();
            break;
        case TYPES.DELETE_COMMENT:
            store.comments = store.comments.filter((comment) => comment.id == action.commentId);
            delete store.comment;
            emitChange();
            break;
        case TYPES.SAVE_COMMENT:
            // TODO: create or update!
            store.comments = [].concat(action.comment, store.comments);
            delete store.comment;
            emitChange();
            break;
        case TYPES.LOGIN:
            store.currentUser = action.currentUser;
            emitChange();
            break;
        case TYPES.LOGOUT:
            delete store.currentUser;
            emitChange();
            break;
        case TYPES.SIGNUP:
            store.currentUser = action.currentUser;
            emitChange();
            break;
    }
    return true;
});

export default store;
