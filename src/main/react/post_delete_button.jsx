import React from "react";
import debug from "debug";
import {store, addChangeListener,removeChangeListener} from "./store";
import {deletePost} from "./actions";

const trace = debug('springboard:PostDeleteButton');

export default class PostDeleteButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: store.currentUser,
            post: props.state
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
        let post = this.state.post;
        if (currentUser && post && currentUser.id == post.user.id) {
            return (
                <button onClick={this._onClickDelete}>delete</button>
            );
        }
        return false;
    }

    _onClickDelete() {
        deletePost(this.state.post.id);
        this.setState({post: null});
    }

    _onChange() {
        this.setState({
            currentUser: store.currentUser
        });
    }
};
