import React, {
    Component
}
from 'react'
import {
    connect
}
from 'react-redux'
import Tags from '../components/Tags'

class App extends Component {
    render() {
        const {
            tags
        } = this.props.tags
        return <div > < Tags tags = {
            tags
        }
        />< /div >
    }
}

function mapStateToProps(state) {
    return {
        tags: state.tags
    }
}

export default connect(mapStateToProps)(App)
