import { combineReducers } from 'redux';
import ShowElement from "../action/ShowElement";
import ShowBooking from "../action/ShowBooking";
import SetBooking from "../action/SetBooking";
import GetUser from "../action/GetUser";
import GetUsers from "../action/GetUsers";


const rootReducer = combineReducers({
    showElement: ShowElement,
    bookings : ShowBooking,
    userProfile: GetUser,
    users : GetUsers
});

export default rootReducer;
