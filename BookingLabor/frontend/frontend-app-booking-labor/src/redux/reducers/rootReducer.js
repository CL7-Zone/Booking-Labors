import { combineReducers } from 'redux';
import ShowElement from "../action/ShowElement";

const rootReducer = combineReducers({
    showElement: ShowElement,
});

export default rootReducer;
