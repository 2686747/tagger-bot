import React, { Component } from 'react';

export default class  Tag extends Component {
    render: function () {
        return ( < div > {
            this.props.tag
        } < /div>);
    }
};
