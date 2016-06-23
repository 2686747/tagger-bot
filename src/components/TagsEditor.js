import Immutable from 'immutable'
import React from 'react'
import ReactDOM from 'react-dom'
var Media = {
    tags:[],
    url:'',
    id:''
};
var Tags = React.createClass({
    onBlur: function(event) {

         this.props.onChangeTags(this.refs.tagsInput.value);
    },
    render: function () {

        return (
                <div className="input-group">
                <span className="input-group-addon">
                    <input type="checkbox" aria-label="Checkbox for following text input"  />
                    </span>
                    <input type="text" className="form-control"
                        aria-label="Text input with checkbox"
                        defaultValue = {this.props.tags} onBlur = {this.onBlur}
                        ref="tagsInput"
                    />
                </div>
        );
    }
});
var Medias= React.createClass({
    render: function () {
        return ( < div > 
            Medias    
         < /div>);
    }
});
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

//display selected medias
var TagsDisplay = React.createClass({
      render: function () {
        var medias = [];
        //medias to mediasView
        this.props.medias.forEach(function (media) {
           medias.push(
                < MediaView media = {media}  key = {media.id} onChangeMedia = {this.props.onChangeMedia}/>);
        }.bind(this));
        return ( < div className='container'>  {medias}  </div> );
    }
});
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
var MockMediaService = {
    medias: [
        {
            tags: [
                {tag: 'tag11'},
                {tag: 'tag12'}
            ],
            url: 'http://icons.iconarchive.com/icons/bokehlicia/captiva/128/browser-web-icon.png',
            id:  'http://icons.iconarchive.com/icons/bokehlicia/captiva/128/browser-web-icon.png'
        },
        {
            tags: [
                {tag: 'tag11'},
                {tag: 'tag21'},
                {tag: 'tag22'}
            ],
            url: 'http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/256/Apps-preferences-web-browser-shortcuts-icon.png',
            id: 'http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/256/Apps-preferences-web-browser-shortcuts-icon.png'

        }
    ],
    loadTags: function() {
        var result = Immutable.Map();
        this.medias.map(
            function(media){
                return media.tags;
            }
            ).forEach(function(tags){
                tags.forEach(function(tag){
                    result = result.set(tag.tag, tag);                    // result.push(tag);
                })
            }
        );
        return  result.toArray();
    },
    loadMedias: function(tags) {
        var result = this.medias.filter(
            function(media){
                for (let mTag of media.tags){
                    for (let tag of tags) {
                        if (tag.tag === mTag.tag) {
                            return true;
                        }
                    };
                }
                return false;
            }
        );
        return result;
    },
    updateMedia: function(media) {
        console.debug('updateMedia', this);

        this.medias = this.medias.map(function(oldMedia, index, array){
            if (oldMedia.id === media.id) {

                return media;
            }
            return oldMedia;
        }).slice(); 
     
    }
};
var TagsEditor = React.createClass({
    mediaService: MockMediaService 
    ,
    getTags: function() {
        console.debug('getTags');

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
        console.debug("onDeleteMedia", media);
    },
    onChangeMedia: function(media) {
        console.debug("onChangeMedia", media);
        this.mediaService.updateMedia(media);
        this.updateTags();
    },
    render: function () {
        console.debug('render get tags', this.state.tags);
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
