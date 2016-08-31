import React from "react";
import debug from "debug";

const trace = debug('springboard:Footer');

export default class Footer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        trace('render:', this.props, this.state);
        return (
            <div className="sbFooter">
                <hr/>
                <p>copyright &copy; 2016 @iolothebard</p>
            </div>
        );
    }
};
