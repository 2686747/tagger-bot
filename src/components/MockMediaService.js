import Immutable from 'immutable';

var Media = {
    tags:[],
    url:'',
    id:''
};

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
        this.medias = this.medias.map(function(oldMedia, index, array){
            if (oldMedia.id === media.id) {

                return media;
            }
            return oldMedia;
        }).slice(); 
     
    }
};

export default MockMediaService;
