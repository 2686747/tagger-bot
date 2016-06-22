
import React from 'react'
import ReactDOM from 'react-dom'
var Media = {
    tags:[],
    url:'',
    id:''
};
var MediaView = React.createClass({
    onChangeTags: function(tagsString) {
        this.setState({
            tags: this.strToTags(tagsString)
        });
    }, 
    render: function() {
        console.debug(' this.props.tags',  this.props.tags);
        // var tags = [];
        // this.props.tags.forEach(function(tag){
            // tags.push(<Tag tag = {tag.tag} key = {tag.tag} />);
        // });
        return (
            <div className='media-left '>
                <div><Tags tags = {this.props.tags} /></div>
                <img src={this.props.url} alt={this.props.url}/>
            </div>
        );
    }
});
var Tags = React.createClass({
    onChangeTags: function() {
        this.props.onChangeTags(this.refs.tagsInput.value);
    },
    render: function () {
        var tags = this.props.tags.map(function(tag){
            return tag.tag;
        }).reduce(function(prev, curr){
            return prev + "  " + curr;
        });
        return (
                <div className="input-group">
                <span className="input-group-addon">
                    <input type="checkbox" aria-label="Checkbox for following text input"  />
                    </span>
                    <input type="text" className="form-control"
                        aria-label="Text input with checkbox"
                        value = {this.props.tags} onChange = {this.onChangeTags}
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
var TagsDisplay = React.createClass({
    render: function () {
        var medias = [];
        //medias to mediasView
        this.props.medias.forEach(function (media) {
           medias.push(
                < MediaView tags = {media.tags}  url = {media.url} key = {media.id} />);
        }.bind(this));
        console.debug('medias', medias);
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
        return ( < div className = 'text-xs-center'> {
            currTags
        } < /div>     );
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
        });
        return result;
    }
};
var TagsEditor = React.createClass({
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
    getInitialState: function () {
        return {
            selectedTags: []
        };
    },
    //converts tags to medias
    getMedias: function(tags) {
        var loaded = MockMediaService.loadMedias(tags);
        console.debug('loaded:', loaded);
        return loaded;
    },
    render: function () {
        var medias = this.getMedias(this.state.selectedTags);
        return (
            < div className='container' >
            < TagsSelector
            tags = {
                this.props.tags
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
        < TagsDisplay
        medias = {
            medias
        }
        />
    < /div >
        );
    }
});
export default TagsEditor;
