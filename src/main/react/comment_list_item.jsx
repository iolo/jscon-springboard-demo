import React from "react";
import debug from "debug";
import TimeAgo from "./time_ago";
import Markdown from "./markdown";
import CommentDeleteButton from "./comment_delete_button";

const trace = debug('springboard:CommentListItem');

export default class CommentListItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            comment: props.comment
        }
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
    }

    render() {
        trace('render:', this.props, this.state);
        let comment = this.state.comment;
        return (
            <li className="sbCommentListItem">
                <p><Markdown markdown={comment.content}/></p>
                <p>
                    <small>at <TimeAgo timestamp={comment.createdAt}/></small>
                    <span>&middot;</span>
                    <small>by {comment.user.username}</small>
                    <CommentDeleteButton comment={comment}/>
                </p>
            </li>
        );
    }
};
