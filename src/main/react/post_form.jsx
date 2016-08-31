import React from "react";
import debug from "debug";
import {store, addChangeListener, removeChangeListener} from "./store";
import {getForum, savePost} from "./actions";

const trace = debug('springboard:PostForm');

export default class PostForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: store.currentUser,
            forum: props.forum,
            title: props.title || '',
            content: props.content || '',
        };
        this._onChangeTitle = this._onChangeTitle.bind(this);
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
        if (!this.state.forum) {
            getForum(this.props.location.query.forumId);
        }
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
        removeChangeListener(this._onChange);
    }

    render() {
        trace('render:', this.props, this.state);
        if (!this.state.currentUser || !this.state.forum) {
            return false;
        }
        return (
            <div className="sbPostForm">
                <h3>post form in {this.state.forum.title}</h3>
                <form onSubmit={this._onSubmitForm}>
                    <p>
                        <label htmlFor="sbCommentTitleEdit">title:</label>
                        <input id="sbCommentTitleEdit" name="title" value={this.state.title}
                               onChange={this._onChangeTitle}/>
                    </p>
                    <p>
                        <label htmlFor="sbCommentContentEdit">content:</label>
                        <textarea id="sbCommentContentEdit" name="content" value={this.state.content}
                                  onChange={this._onChangeContent}/>
                    </p>
                    <p>
                        <button type="submit">save</button>
                    </p>
                </form>
            </div>
        );
    }

    _onChangeTitle(e) {
        this.setState({title: e.target.value});
    }

    _onChangeContent(e) {
        this.setState({content: e.target.value});
    }

    _onSubmitForm(e) {
        e.preventDefault();
        let forum = this.state.forum;
        let router = this.context.router;
        savePost({
            user: this.state.currentUser,
            forum: this.state.forum,
            title: this.state.title,
            content: this.state.content
        }).then(function (res) {
            trace('save post ok:', res);
            router.push('/post_list?forumId=' + forum.id);
        }).catch(function (err) {
            trace('save post error:', err);
            alert('save post error');
        });
    }

    _onChange() {
        this.setState({
            currentUser: store.currentUser,
            forum: store.forum
        });
    }
};

PostForm.contextTypes = {
    router: React.PropTypes.func.isRequired
};
