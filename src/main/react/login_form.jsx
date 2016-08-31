import React from "react";
import debug from "debug";
import {login} from "./actions";

const trace = debug('springboard:LoginForm');

export default class LoginForm extends React.Component {
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
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
    }

    render() {
        trace('render:', this.props, this.state);
        return (
            <div className="sbLoginForm">
                <h3>login</h3>
                <form id="loginForm" onSubmit={this._onSubmitForm}>
                    <p>
                        <label htmlFor="sbLoginUsername">username:</label>
                        <input id="sbLoginUsername" type="text" name="username" value={this.state.username}
                               placeholder="u1, u2, u3, ..."
                               onChange={this._onChangeUsername}/>
                    </p>
                    <p>
                        <label htmlFor="sbLoginPassword">password:</label>
                        <input id="sbLoginPassword" type="password" name="password" value={this.state.password}
                               placeholder="p1, p2, p3, ..."
                               onChange={this._onChangePassword}/>
                    </p>
                    <p>
                        <button type="submit">login</button>
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
        login({
            username: this.state.username,
            password: this.state.password
        }).then(function () {
            trace('login ok... next=' + next);
            router.push(next);
        }).catch(function (err) {
            trace('login error:', err);
            alert('login error');
        });
    }
};

LoginForm.contextTypes = {
    router: React.PropTypes.func.isRequired
};
