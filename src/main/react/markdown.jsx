import React from "react";
import marked from "marked";

export default class Markdown extends React.Component {
    render() {
        return (
            <span className="sbMarkdown" dangerouslySetInnerHTML={{__html: marked(this.props.markdown)}}/>
        );
    }
};

