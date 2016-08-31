import React from "react";
import {Link} from "react-router";
import debug from "debug";

const trace = debug('springboard:ForumListItem');

export default class ForumListItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            forum: props.forum
        };
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
    }

    render() {
        trace('render:', this.props, this.state);
        let forum = this.state.forum;
        return (
            <li className="sbForumListItem">
                <h5><Link to={`/post_list?forumId=${forum.id}`}>{forum.title}</Link></h5>
            </li>
        );
    }
};
