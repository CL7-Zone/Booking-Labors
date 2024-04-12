
const initialState = true;

const ShowElement = (state = initialState, action) => {
    switch (action.type) {
        case 'SHOW':
            return true;
        case 'HIDDEN':
            return false;
        default:
            return state;
    }
}

export default ShowElement;
