'use strict';

const webpack = require('webpack');
const path = require('path');
const fs = require('fs');

const clientConfig = {
    entry: {
        client: './src/main/react/client.jsx'
    },
    output: {
        //publicPath: 'https://.../',
        path: './target/classes/static/',
        filename: '[name].bundle.js'
    },
    resolve: {
        extensions: ['', '.js', '.jsx']
    },
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loaders: ['babel']
            }
        ]
    },
    //devtool: 'source-map',
    //debug: true,
    plugins: [
        //new webpack.optimize.UglifyJsPlugin(),
        //new webpack.SourceMapDevToolPlugin(),
        //new webpack.EnvironmentPlugin(['NODE_ENV']),
        new webpack.NoErrorsPlugin()
    ]
};

const serverConfig = {
    entry: {
        server: './src/main/react/server.jsx'
    },
    target: 'node',
    node: {
        __dirname: false,
        __filename: false,
    },
    // XXX: 상대 경로로 require 모듈(내가 만든 모듈)만 번들에 포함하고 나머진 그대로 두자!
    // see https://github.com/webpack/webpack/issues/603
    externals: /^[^.]/,
    output: {
        //publicPath: 'https://.../',
        path: './target/',
        filename: '[name].bundle.js',
        // XXX: 번들을 require 해서 쓸꺼니까 module.exports 해줘!
        libraryTarget: 'commonjs2'
    },
    resolve: {
        extensions: ['', '.js', '.jsx']
    },
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loaders: ['babel']
            }
        ]
    },
    //devtool: 'source-map',
    //debug: true,
    plugins: [
        //new webpack.optimize.UglifyJsPlugin(),
        //new webpack.SourceMapDevToolPlugin(),
        //new webpack.EnvironmentPlugin(['NODE_ENV']),
        new webpack.NoErrorsPlugin()
    ]
};

module.exports = [
    clientConfig,
    serverConfig
];
