import axios, {get} from "axios";
import KEY from "../constant/constant";
import {getApi} from "../../api/apiFunction";


export const getRole = () => async (dispatch) => {
    dispatch({
        type: KEY.LOAD_ROLES,
    });
    try {
        const res = await getApi("/admin/api/role");
        dispatch({
            type: KEY.LOAD_ROLES_SUCCESS,
            roles: res,
            isError: false,
        });
    } catch (e) {
        dispatch({
            type: KEY.LOAD_ROLES_SUCCESS,
            roles: [],
            isError: true,
        });
    }
};