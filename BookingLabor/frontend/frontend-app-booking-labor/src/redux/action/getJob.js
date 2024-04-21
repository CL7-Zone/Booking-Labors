import axios, {get} from "axios";
import KEY from "../constant/constant";
import {getApi} from "../../api/apiFunction";

export const getJob = () => async (dispatch) => {
    dispatch({
        type: KEY.LOAD_JOB,
    });
    try {
        const res = await getApi("/admin/api/job");
        dispatch({
            type: KEY.LOAD_JOB_SUCCESS,
            jobs: res,
            isError: false,
        });
    } catch (e) {
        dispatch({
            type: KEY.LOAD_JOB_SUCCESS,
            jobs: [],
            isError: true,
        });
    }
};