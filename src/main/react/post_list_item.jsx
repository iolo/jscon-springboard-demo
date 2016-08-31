import React from "react";
import {Link} from "react-router";
import TimeAgo from "./time_ago";
import debug from "debug";

const trace = debug('springboard:PostListItem');

export default class PostListItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            post: props.post
        };
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
    }

    render() {
        trace('render:', this.props, this.state);
        let post = this.state.post;
        return (
            <li className="sbPostListItem">
                <p><Link to={`/post_view?postId=${post.id}`}>{post.title}</Link></p>
                <p>
                    <span>at <TimeAgo timestamp={post.createdAt}/></span>
                    <span>&middot;</span>
                    <span>by {post.user.username}</span>
                </p>
            </li>
        );
    }
};
