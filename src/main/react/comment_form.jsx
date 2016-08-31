import React from "react";
import debug from "debug";
import {store, addChangeListener, removeChangeListener} from "./store";
import {getPost, saveComment} from "./actions";

const trace = debug('springboard:CommentForm');

export default class CommentForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: store.currentUser,
            post: props.post,
            content: props.content || '',
        };
        this._onChangeContent = this._onChangeContent.bind(this);
        this._onSubmitForm = this._onSubmitForm.bind(this);
        this._onChange = this._onChange.bind(this);
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
        addChangeListener(this._onChange);
    }

    componentDidMount() {
        trace('componentDidMount:', this.props, this.state);
        if (!this.state.post) {
            getPost(this.props.location.query.postId);
        }
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
        removeChangeListener(this._onChange);
    }

    render() {
        trace('render:', this.props, this.state);
        if (!this.state.currentUser || !this.state.post) {
            return false;
        }
        return (
            <div className="sbCommentForm">
                <form onSubmit={this._onSubmitForm}>
                    <textarea id="sbCommentContentEdit" value={this.state.content}
                              onChange={this._onChangeContent}/>
                    <button type="submit">save</button>
                </form>
            </div>
        );
    }

    _onChangeContent(e) {
        this.setState({content: e.target.value});
    }

    _onSubmitForm(e) {
        e.preventDefault();
        let self = this;
        saveComment({
            user: this.state.currentUser,
            post: this.state.post,
            content: this.state.content
        }).then(function (res) {
            trace('save comment ok:', res);
            self.setState({content: ''});
        }).catch(function (err) {
            trace('save comment error:', err);
            alert('save comment error');
        });
    }

    _onChange() {
        this.setState({
            currentUser: store.currentUser,
            post: store.post
        });
    }
};
