const initialState = [];

const ShowBooking = (state = initialState, action) => {
    switch (action.type) {
        case "BOOKING":
            return action.payload;
        default:
            return state;
    }
}


export default ShowBooking;