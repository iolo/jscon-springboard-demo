import React from "react";
import debug from "debug";
import ForumListItem from "./forum_list_item";
import {store, addChangeListener,removeChangeListener} from "./store";
import {getForums} from "./actions";

const trace = debug('springboard:ForumList');

export default class ForumList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            forums: props.forums
        };
        this._onChange = this._onChange.bind(this);
    }

    componentWillMount() {
        trace('componentWillMount:', this.props, this.state);
        addChangeListener(this._onChange);
    }

    componentDidMount() {
        trace('componentDidMount:', this.props, this.state);
        if (!this.state.forums) {
            getForums();
        }
    }

    componentWillUnmount() {
        trace('componentWillUnmount:', this.props, this.state);
        removeChangeListener(this._onChange);
    }

    render() {
        trace('render:', this.props, this.state);
        let forums = this.state.forums;
        if (!forums) {
            return false;
        }
        let forumListItems = forums.map(function (forum) {
            return (
                <ForumListItem key={forum.id} forum={forum}/>
            );
        });
        return (
            <div className="sbForumList">
                <h3>forum list</h3>
                <ul>
                    {forumListItems}
                </ul>
            </div>
        );
    }

    _onChange() {
        this.setState({
            forums: store.forums
        });
    }
};

ForumList.contextTypes = {
    router: React.PropTypes.func.isRequired
};
