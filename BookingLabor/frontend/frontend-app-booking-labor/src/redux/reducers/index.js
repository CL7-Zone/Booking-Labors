import { combineReducers } from "redux";

import reducerArray from "./reducerArray";
import reducerString from "./reducerString";

const allReducers = combineReducers({
    Array: reducerArray,
    String : reducerString,
});

export default allReducers;