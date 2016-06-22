import React from 'react';
import TagsEditor from './components/TagsEditor';


const App = React.createClass({

    render: function() {

        var TAGS = [
            {
                tag: 'tag11'
            },
            {
                tag: 'tag12'
            },
            {
                tag: 'tag21'
            },
            {
                tag: 'tag22'
            }
        ];

        return (<div> <TagsEditor tags = {TAGS} /></div>) ;
    }
}
);
export default App;
