import React from 'react';

var TagButton = React.createClass({
    onClick: function () {
        // this.props.selected = !this.props.selected;
           this.props.onChangeState(this.props.tag)
    },
    render: function () {
        var btnState = this.props.selected ? 'btn btn-primary' : 'btn btn-secondary';
        return (
            < button type='button' className={btnState} onClick = { this.onClick } > { this.props.tag.tag } < /button>
        );
    }
});

export default TagButton;
