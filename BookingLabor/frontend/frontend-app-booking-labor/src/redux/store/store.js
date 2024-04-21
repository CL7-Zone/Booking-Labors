import { createStore , applyMiddleware } from 'redux';
import reducerArray from "../reducers/reducerArray";
import allReducers from "../reducers/index";
import {thunk} from "redux-thunk"

const store = createStore(allReducers, applyMiddleware(thunk));

export default store;





