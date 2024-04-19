import axios, {get} from "axios";
import KEY from "../constant/constant";
import {getApi} from "../../api/apiFunction";

export const getCity = () => async (dispatch) => {
    dispatch({
        type: KEY.LOAD_CITIES,
    });
    try {
        const res = await getApi("/admin/api/city");
        dispatch({
            type: KEY.LOAD_CITIES_SUCCESS,
            cities: res,
            isError: false,
        });
    } catch (e) {
        dispatch({
            type: KEY.LOAD_CITIES_SUCCESS,
            cities: [],
            isError: true,
        });
    }
};