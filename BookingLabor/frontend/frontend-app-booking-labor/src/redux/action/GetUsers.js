const initialState = [];

const GetUsers = (state = initialState, action) => {
    switch (action.type) {
        case "USERS":
            return action.payload;
        default:
            return state;
    }
}


export default GetUsers;