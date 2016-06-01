var Tag = React.createClass({
  getInitialState: function() {
      return {
          checked: false,
          tag: ""
      };
  },
  handleClick: function(event) {
    this.setState({
      checked: !this.state.checked
    });
    console.log('click', event);
  },
  render: function() {
    return (
        <button type="button" className={this.state.checked ?  "btn btn-primary" : "btn  btn-secondary" } onClick = {this.handleClick}>{this.props.tag}</button>
    );
  }
});
var Tags = React.createClass({
  render: function() {

    var tags = this.props.tags.map(function(tag) {
      return (
        <Tag tag = {tag.value} />
      );
    });
    return (
        <div className="tags">
          {tags}
        </div>
    );
  }
});
var TagsBox = React.createClass({
    getInitialState: function() {
        return {
            tagsUrl: "/tags",
            tags: []
        };
    },
    componentDidMount: function() {
      //get tags from server and bind to state value
        this.serverRequest = $.get(this.state.tagsUrl, function(result) {
            this.setState({
                tags: result['tags']
            })
        }.bind(this));
    },
    componentWillUnmount: function() {
        this.serverRequest.abort();
    },
    render: function() {
        return ( < div className = "tagsBox" >

              <Tags tags={this.state.tags}/>
             < /div>
        );
    }
});
var PicturesBox = React.createClass({
    render: function() {

        return ( < div className = "picturesBox" >
            pictures will be here

            < /div>
        );
    }
});
ReactDOM.render( < TagsBox / > ,
    document.getElementById('tags')
);
