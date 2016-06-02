const initialState = {
    tags: ['tag1', 'tag2']
}

export default function tags(state = initialState, action) {

    switch (action.type) {
    case 'SET_TAG':
        return {...state, tags: action.payload
        }

    default:
        return state;
    }
}