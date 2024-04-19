import axios, {get} from "axios";
import KEY from "../constant/constant";
import {getApi} from "../../api/apiFunction";


export const getBooking = () => async (dispatch) => {
    dispatch({
        type: KEY.LOAD_BOOKINGS,
    });
    try {
        const res = await getApi("/admin/api/booking");
        dispatch({
            type: KEY.LOAD_BOOKINGS_SUCCESS,
            bookings: res,
            isError: false,
        });
    } catch (e) {
        dispatch({
            type: KEY.LOAD_BOOKINGS_SUCCESS,
            bookings: [],
            isError: true,
        });
    }
};