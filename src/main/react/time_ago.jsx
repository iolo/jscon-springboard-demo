import React from "react";

const SECOND = 1000;
const MINUTE = 60 * SECOND;
const HOUR = 60 * MINUTE;
const DAY = 24 * HOUR;
const WEEK = 7 * DAY;

export function timeAgo(timestamp) {
    let diff = Date.now() - timestamp;
    if (diff < MINUTE) {
        return Math.round(diff / SECOND) + ' sec. ago';
    } else if (diff < HOUR) {
        return Math.round(diff / MINUTE) + ' min. ago';
    } else if (diff < DAY) {
        return Math.round(diff / HOUR) + ' hours ago';
    } else if (diff < WEEK) {
        return Math.round(diff / DAY) + ' days ago';
    } else {
        return new Date(timestamp).toDateString();
    }
}

export default class TimeAgo extends React.Component {
    render() {
        return (
            <span className="sbTimeAgo">{timeAgo(this.props.timestamp)}</span>
        );
    }
};
