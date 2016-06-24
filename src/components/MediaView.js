import React from 'react';
import Tags from './Tags';

var MediaView = React.createClass({
    strToTags: function(tagsString) {
        //just split by " "
        return tagsString.split(' ').filter(function(tag){
            return tag !== null && tag.trim().length > 0;
        })
        //string tag to Tag
        .map(function(strTag){
            return {tag: strTag};
        });
    },
    onChangeTags: function(tagsString) {
        this.props.media.tags = this.strToTags(tagsString);
        this.props.onChangeMedia(this.props.media);
        // this.props.onChangeTags(this.strToTags(tagsString));
        // this.setState({
            // tags: this.strToTags(tagsString)
        // });
    }, 
    render: function() {
        // var tags = [];
        // this.props.tags.forEach(function(tag){
            // tags.push(<Tag tag = {tag.tag} key = {tag.tag} />);
        // });
        var tags = this.props.media.tags.map(function(tag){
            return tag.tag;
        }).reduce(function(prev, curr){
            return prev + "  " + curr;
        });
        return (
            <div className='media-left'>
                <div><Tags tags = {tags} onChangeTags = {this.onChangeTags} /></div>
                <img src={this.props.media.url} alt={this.props.media.url}/>
            </div>
        );
    }
});

export default MediaView; 
