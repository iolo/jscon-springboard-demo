import React from "react";
import debug from "debug";
import CommentListItem from "./comment_list_item";
import {store, addChangeListener, removeChangeListener} from "./store";

const trace = debug('springboard:CommentList');

export default class CommentList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            comments: props.comments
        };
        this._onChange = this._onChange.bind(this);
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
        addChangeListener(this._onChange);
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
        removeChangeListener(this._onChange);
    }

    render() {
        trace('render:', this.props, this.state);
        let comments = this.state.comments;
        return (
            <div className="sbCommentList">
                <h5>comments</h5>
                <ul>
                    {comments.map(function (comment) {
                        return (
                            <CommentListItem key={comment.id} comment={comment}/>
                        );
                    })}
                </ul>
            </div>
        );
    }

    _onChange() {
        this.setState({
            comments: store.comments
        });
    }
};
