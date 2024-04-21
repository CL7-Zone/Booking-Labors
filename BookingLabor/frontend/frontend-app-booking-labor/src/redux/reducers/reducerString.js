import KEY from "../constant/constant";

const initalState = {

    messageSuccess: "",
    message: "",
    token: "",
};

const reducerString = (state = initalState, action) => {
    switch (action.type) {
        case "SET_MESSAGE":
            return {
                ...state,
                message: action.message,
            };
        case "SET_MESSAGE_SUCCESS":
            return {
                ...state,
                messageSuccess: action.messageSuccess,
            };
        case "SET_TOKEN":
            return {
                ...state,
                token: action.token,
            };
        default:
            return state;
    }
};

export default reducerString;