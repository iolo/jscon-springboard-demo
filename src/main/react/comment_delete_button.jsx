import React from "react";
import debug from "debug";
import {store, addChangeListener,removeChangeListener} from "./store";
import {deleteComment} from "./actions";

const trace = debug('springboard:CommentDeleteButton');

export default class CommentDeleteButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: store.currentUser,
            comment: props.comment
        };
        this._onClickDelete = this._onClickDelete.bind(this);
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
        let currentUser = this.state.currentUser;
        let comment = this.state.comment;
        if (currentUser && comment && currentUser.id == comment.user.id) {
            return (
                <button onClick={this._onClickDelete}>delete</button>
            );
        }
        return false;
    }

    _onClickDelete() {
        deleteComment(this.state.comment.id);
        this.setState({comment: null});
    }

    _onChange() {
        this.setState({
            currentUser: store.currentUser
        });
    }
};
