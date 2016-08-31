import React from "react";
import {Link} from "react-router";
import debug from "debug";
import {store, addChangeListener,removeChangeListener} from "./store";
import {logout} from "./actions";

const trace = debug('springboard:Header');

export default class Header extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: store.currentUser
        };
        this._onClickLogout = this._onClickLogout.bind(this);
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
        if (currentUser) {
            return (
                <div className="sbHeader">
                    <span><Link to="/forum_list">forums</Link></span>
                    <span>|</span>
                    <span>{currentUser.username}</span>
                    <span>|</span>
                    <span onClick={this._onClickLogout}>logout</span>
                    <hr/>
                </div>
            );
        } else {
            return (
                <div className="sbHeader">
                    <span><Link to="/forum_list">forums</Link></span>
                    <span>|</span>
                    <span><Link to="/login">login</Link></span>
                    <span>|</span>
                    <span><Link to="/signup">signup</Link></span>
                    <hr/>
                </div>
            );
        }
    }

    _onClickLogout() {
        trace('logout!');
        logout();
    }

    _onChange() {
        this.setState({
            currentUser: store.currentUser
        });
    }
};
