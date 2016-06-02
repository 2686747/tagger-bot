import React, {
    PropTypes, Component
}
from 'react'
import Tag from './Tag'

export default class Tags extends Component {
    render() {
        const {
            tags
        } = this.props
        var tagsBtn = tags.map(tag => {
            return <Tag tag = {
                tag
            }
            />; 
        });
        return <div > {
            tagsBtn
        } < /div >
    }
}

Tags.propTypes = {
    tags: PropTypes.array.isRequired
}