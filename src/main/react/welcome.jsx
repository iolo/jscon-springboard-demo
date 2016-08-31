import React from "react";
import debug from "debug";

const trace = debug('springboard:Welcome');

export default class Welcome extends React.Component {
    render() {
        trace('render:', this.props, this.state);
        return (
            <div>
                <h3>welcome!</h3>
            </div>
        );
    }
};
