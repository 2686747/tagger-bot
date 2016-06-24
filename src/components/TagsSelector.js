import React from 'react';
import TagButton from './TagButton';

var TagsSelector = React.createClass({
    onSelect: function (tag) {
        if (!(this.props.selectedTags.indexOf(tag) > -1)) {
            this.props.selectTag(tag);
        } else {
            
            this.props.deselectTag(tag);
        }
    },

    render: function () {
        var currTags = [];
        if (this.props.tags.length > 0) {
            this.props.tags.forEach(function (tag) {
                var selected = this.props.selectedTags.indexOf(tag) > -1 ? true : false;
                currTags.push(
                    < TagButton
                    tag = { tag }
                    onChangeState = { this.onSelect }
                    selected = {selected}
                    key = {tag.tag}
                    />
                );
            }.bind(this));

        } else {
            currTags = 'Sorry, you have no tags.';
        }
        return (<div className = 'text-xs-center'> {currTags} </div>);
    }
});

export default TagsSelector;
