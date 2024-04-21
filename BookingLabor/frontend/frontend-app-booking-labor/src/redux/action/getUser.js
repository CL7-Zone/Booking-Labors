import axios, {get} from "axios";
import KEY from "../constant/constant";
import {getApi} from "../../api/apiFunction";

export const getUser = () => async (dispatch) => {
    dispatch({
        type: KEY.LOAD_USERS,
    });
    try {
        const res = await getApi("/admin/api/user");
        dispatch({
            type: KEY.LOAD_USERS_SUCCESS,
            users: res,
            isError: false,
        });
    } catch (e) {
        dispatch({
            type: KEY.LOAD_USERS_SUCCESS,
            users: [],
            isError: true,
        });
    }
};