import React from "react";
import {Link} from "react-router";
import debug from "debug";
import PostListItem from "./post_list_item";
import {store, addChangeListener, removeChangeListener} from "./store";
import {getPosts} from "./actions";

const trace = debug('springboard:PostList');

export default class PostList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            forum: props.forum,
            posts: props.posts
        };
        this._onChange = this._onChange.bind(this);
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
        addChangeListener(this._onChange);
    }

    componentDidMount() {
        trace('componentDidMount:', this.props, this.state);
        if (!this.state.forum && !this.state.posts) {
            getPosts(this.props.location.query.forumId);
        }
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
        removeChangeListener(this._onChange);
    }

    render() {
        trace('render:', this.props, this.state);
        let forum = this.state.forum;
        let posts = this.state.posts;
        if (!forum || !posts) {
            return false;
        }
        let postListItems = posts.map(function (post) {
            return (
                <PostListItem key={post.id} post={post}/>
            );
        });
        return (
            <div className="sbPostList">
                <h3>posts in {forum.title}</h3>
                <ul>
                    {postListItems}
                </ul>
                <div>
                    <Link to={`/post_form?forumId=${forum.id}`}>write new post</Link>
                </div>
            </div>
        );
    }

    _onChange() {
        this.setState({
            forum: store.forum,
            posts: store.posts
        });
    }
};
