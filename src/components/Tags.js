import React from 'react';

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

export default Tags;
