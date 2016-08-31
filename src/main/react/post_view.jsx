import React from "react";
import debug from "debug";
import PostDeleteButton from "./post_delete_button";
import CommentList from "./comment_list";
import CommentForm from "./comment_form";
import TimeAgo from "./time_ago";
import Markdown from "./markdown";
import {store, addChangeListener,removeChangeListener} from "./store";
import {getPost} from "./actions";

const trace = debug('springboard:PostView');

export default class PostView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            post: props.post,
            comments: props.comments
        };
        this._onChange = this._onChange.bind(this);
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
        addChangeListener(this._onChange);
    }

    componentDidMount() {
        trace('componentDidMount:', this.props, this.state);
        if (!this.state.post || this.state.comments) {
            getPost(this.props.location.query.postId);
        }
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
        removeChangeListener(this._onChange);
    }

    render() {
        trace('render:', this.props, this.state);
        let post = this.state.post;
        let comments = this.state.comments;
        if (!post || !comments) {
            return (
                <div>loading...</div>
            );
        }
        return (
            <div className="sbPostView">
                <h3>{post.title}</h3>
                <p>at <TimeAgo timestamp={post.createdAt}/></p>
                <p>by {post.user.username}</p>
                <p><Markdown markdown={post.content}/></p>
                <p><PostDeleteButton post={post}/></p>
                <hr />
                <CommentList comments={comments}/>
                <CommentForm post={post}/>
            </div>
        );
    }

    _onChange() {
        this.setState({
            post: store.post,
            comments: store.comments
        });
    }
};
