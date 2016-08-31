import React from "react";
import ReactDOM from "react-dom";
import {Router, useRouterHistory} from "react-router";
import {createHistory} from "history";
import routes from "./routes";
import debug from "debug";

const trace = debug('springboard:client');
trace('***client entry point***');

const history = useRouterHistory(createHistory)({
    basename: "/springboard/react"
});

const mountPoint = document.getElementById('main');

ReactDOM.render((<Router history={history} routes={routes}/>), mountPoint);

