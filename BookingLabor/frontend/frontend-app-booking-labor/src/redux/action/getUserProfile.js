import axios, {get} from "axios";
import KEY from "../constant/constant";
import {getApi} from "../../api/apiFunction";

export const getUserProfile = () => async (dispatch) => {
    dispatch({
        type: KEY.LOAD_USER_PROFILE,
    });
    try {
        const res = await getApi("/admin/profile");
        await new Promise(resolve => setTimeout(resolve, 500));
        dispatch({
            type: KEY.LOAD_USER_PROFILE_SUCCESS,
            user: res,
            isError: false,
        });
    } catch (e) {
        await new Promise(resolve => setTimeout(resolve, 1000));
        dispatch({
            type: KEY.LOAD_USER_PROFILE_SUCCESS,
            user: [],
            isError: true,
        });
    }
};