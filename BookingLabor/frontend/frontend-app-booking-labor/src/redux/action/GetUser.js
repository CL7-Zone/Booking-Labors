const initialState = [];

const GetUser = (state = initialState, action) => {
    switch (action.type) {
        case "USER":
            return action.payload;
        default:
            return state;
    }
}


export default GetUser;