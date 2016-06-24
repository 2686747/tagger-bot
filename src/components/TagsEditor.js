import React from 'react';
import MockMediaService from './MockMediaService';
import TagsDisplay from './TagsDisplay';
import TagsSelector from './TagsSelector';

var TagsEditor = React.createClass({
    mediaService: MockMediaService 
    ,
    getTags: function() {

        return this.mediaService.loadTags()
    },
    updateTags: function() {
        this.setState({
            tags: this.getTags()
        });
    },
    selectTag: function (tag) {
        var selected = this.state.selectedTags;
        selected.push(tag);
        this.setState(
            {
                selected: selected
            }
        );
    },
    deselectTag: function (tag) {
        var selected = this.state.selectedTags;
        selected.splice(selected.indexOf(tag), 1); 
        this.setState(
            {
                selected: selected
            }
        );
    },
    componentDidMount: function() {
        this.updateTags();
    },
    getInitialState: function () {
        return {
            tags: [],
            selectedTags: []
        };
    },
    //converts tags to medias
    getMedias: function(tags) {
        var loaded = [];
        if (tags !== null && tags.length > 0){
            loaded = this.mediaService.loadMedias(tags);
        }
        return loaded;
    },
    onDeleteMedia: function(media) {
    },
    onChangeMedia: function(media) {
        this.mediaService.updateMedia(media);
        this.updateTags();
    },
    render: function () {
        var medias = this.getMedias(this.state.selectedTags);
        return (
            <div className='container' >
                <TagsSelector
                    tags = {
                        this.state.tags
                    }
                    selectTag = {
                        this.selectTag
                    }
                    deselectTag = {
                        this.deselectTag
                    }
                    selectedTags = {
                        this.state.selectedTags
                    }
                />
                <TagsDisplay
                    medias = {medias} 
                    onChangeMedia = {this.onChangeMedia}
                    onDeleteMedia = {this.onDeleteMedia}
                />
            </div>
        );
    }
});
export default TagsEditor;
