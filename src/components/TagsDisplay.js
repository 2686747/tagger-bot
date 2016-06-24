import React from 'react';
import MediaView from './MediaView';
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

export default TagsDisplay;
