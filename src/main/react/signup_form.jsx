import React from "react";
import debug from "debug";
import {addChangeListener, removeChangeListener} from "./store";
import {signup} from "./actions";

const trace = debug('springboard:SignupForm');

export default class SignupForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            next: props.location.query.next || '/forum_list'
        };
        this._onChangeUsername = this._onChangeUsername.bind(this);
        this._onChangePassword = this._onChangePassword.bind(this);
        this._onSubmitForm = this._onSubmitForm.bind(this);
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
        return (
            <div className="sbSignupForm">
                <h3>signup</h3>
                <form id="signupForm" onSubmit={this._onSubmitForm}>
                    <p>
                        <label htmlFor="sbSignupUsername">username:</label>
                        <input id="sbSignupUsername" type="text" name="username" value={this.state.username}
                               onChange={this._onChangeUsername}/>
                    </p>
                    <p>
                        <label htmlFor="sbSignupPassword">password:</label>
                        <input id="sbSignupPassword" type="password" name="password" value={this.state.password}
                               onChange={this._onChangePassword}/>
                    </p>
                    <p>
                        <button type="submit">signup</button>
                    </p>
                </form>
            </div>
        );
    }

    _onChangeUsername(e) {
        this.setState({username: e.target.value});
    }

    _onChangePassword(e) {
        this.setState({password: e.target.value});
    }

    _onSubmitForm(e) {
        e.preventDefault();
        let next = this.state.next;
        let router = this.context.router;
        signup({
            username: this.state.username,
            password: this.state.password
        }).then(function () {
            trace('signup ok... next=' + next);
            router.push(next);
        }).catch(function (err) {
            trace('signup error:', err);
            alert('signup error');
        });
    }
};

SignupForm.contextTypes = {
    router: React.PropTypes.func.isRequired
};
