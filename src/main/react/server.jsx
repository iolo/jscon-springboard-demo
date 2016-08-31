// 'use strict';
//
// require('babel-register');
//
// const React = require('react');
// const ReactDOMServer = require('react-dom/server');
// const ReactRouter = require('react-router');
// const RouterContext = ReactRouter.RouterContext;
// const match = ReactRouter.match;
// const useRouterHistory = ReactRouter.useRouterHistory;
// const createHistory = require('history').createMemoryHistory;
// const debug = require('debug');
// const routes = require('./routes').default;
// const timeAgo = require('./time_ago').timeAgo;
// const ejs = require('ejs');
// const marked = require('marked');

import React from "react";
import ReactDOMServer from "react-dom/server";
import {RouterContext, match, useRouterHistory} from "react-router";
import {createMemoryHistory as createHistory} from "history";
import debug from "debug";
import routes from "./routes";
import {timeAgo} from "./time_ago";
import ejs from "ejs";
import marked from "marked";
//import handlebars from "handlebars";

debug.enable('springboard:*');
const trace = debug('springboard:server');
trace('***server entry point***');

const history = useRouterHistory(createHistory)({
    basename: "/springboard/react"
});

const _compiledTemplates = {};

// see J2V8Utils
// see J2V8TemplateView
export function render(template, model, url) {
    trace('render: template=', template, 'model=', model, 'url=', url);
    let compiledTemplate = _compiledTemplates[url];
    if (!compiledTemplate) {
        // XXX: dirty hack! i need more elegant way!
        var opts = {filename: url.replace(/^classpath:/, './src/main/resources/')};
        _compiledTemplates[url] = compiledTemplate = ejs.compile(template, opts);
        //_compiledTemplates[url] = compiledTemplate = handlebars.compile(template);
    }
    //---------------------------------------------------------
    // custom helpers... ejs v2 doesn't support filter :'(
    model.$timeAgo = timeAgo;
    model.$marked = marked;
    //---------------------------------------------------------
    return compiledTemplate(model);
}

// see J2V8Utils
export function renderToString(type, props) {
    trace('renderToString: type=', type, 'props=', props);
    return ReactDOMServer.renderToString(React.createElement(type, props));
}

// see J2V8Utils
export function renderReactRouter(location, callback) {
    trace('renderReactRouter: location=', location);
    match({routes: routes, history: history, location: location}, function (error, redirectLocation, renderProps) {
        trace('renderRouter match: error=', error, 'redirectLocation=', redirectLocation, 'renderProps=', renderProps);
        if (error) {
            callback("error:500", null);
        } else if (redirectLocation) {
            callback(null, 'redirect:' + redirectLocation.pathname + redirectLocation.search);
        } else if (renderProps) {
            callback(null, ReactDOMServer.renderToString(React.createElement(RouterContext, renderProps)));
        } else {
            callback("error:404", null);
        }
    })
}
