const initialState = {
    tag: name,
    checked: false
}

export default function tag(state = initialState, action) {

    switch (action.type) {
    case 'SET_TAG':
        return {
            ...state,
            tag: action.payload.tag,
                checked: action.payload.checked
        }

    default:
        return state;
    }
}