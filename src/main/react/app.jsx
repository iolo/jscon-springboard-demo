import React from "react";
import debug from "debug";
import Header from "./header";
import Footer from "./footer";
import {store, addChangeListener,removeChangeListener} from "./store";
import {login} from "./actions";

const trace = debug('springboard:App');

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: store.currentUser
        };
        this._onChange = this._onChange.bind(this);
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
        addChangeListener(this._onChange);
    }

    componentDidMount() {
        trace('componentDidMount:', this.props, this.state);
        login();
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
        removeChangeListener(this._onChange);
    }

    render() {
        trace('render:', this.props, this.state);
        let currentUser = this.state.currentUser;
        return (
            <div className="sbApp">
                <Header currentUser={currentUser}/>
                {this.props.children}
                <Footer />
            </div>
        );
    }

    _onChange() {
        this.setState({
            currentUser: store.currentUser
        });
    }
};
