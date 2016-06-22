import React, {
    PropTypes, Component
}
from 'react'

export default class Tag extends Component {
    render() {
        const {
            tag, checked
        } = this.props;
        console.debug('tag:', tag);
        console.debug('checked:', checked);
        return <button type = 'button'
        className = 'btn btn-primary' > {
            tag
        } < /button>
    }
}

Tag.propTypes = {
    tag: PropTypes.string.isRequired,
    checked: PropTypes.bool.isRequired
}
