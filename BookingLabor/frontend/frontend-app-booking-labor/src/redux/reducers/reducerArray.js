import KEY from "../constant/constant";

//State manager
const initalState = {
    jobs: [],
    user: [],
    roles : [],
    users: [],
    cities: [],
    bookings: [],
    isLoading: false,
    isError: false,
};

const reducerArray = (state = initalState, action) => {
    switch (action.type) {
        case KEY.LOAD_JOB:
            return {
                ...state,
                isLoading: true,
                isError: false,
            };
        case KEY.LOAD_JOB_SUCCESS:
            return {
                ...state,
                jobs: action.jobs,
                isLoading: false,
            };
        case KEY.LOAD_USER_PROFILE:
            return {
                ...state,
                isLoading: true,
                isError: false,
            };
        case KEY.LOAD_USER_PROFILE_SUCCESS:
            return {
                ...state,
                user: action.user,
                isLoading: false,
            };
        case KEY.LOAD_USERS:
            return {
                ...state,
                isLoading: true,
                isError: false,
            };
        case KEY.LOAD_USERS_SUCCESS:
            return {
                ...state,
                users: action.users,
                isLoading: false,
            };
        case KEY.LOAD_BOOKINGS:
            return {
                ...state,
                isLoading: true,
                isError: false,
            };
        case KEY.LOAD_BOOKINGS_SUCCESS:
            return {
                ...state,
                bookings: action.bookings,
                isLoading: false,
            };
        case KEY.LOAD_CITIES:
            return {
                ...state,
                isLoading: true,
                isError: false,
            };
        case KEY.LOAD_CITIES_SUCCESS:
            return {
                ...state,
                cities: action.cities,
                isLoading: false,
            };
        case KEY.LOAD_ROLES:
            return {
                ...state,
                isLoading: true,
                isError: false,
            };
        case KEY.LOAD_ROLES_SUCCESS:
            return {
                ...state,
                roles: action.roles,
                isLoading: false,
            };
        default:
            return state;
    }
};

export default reducerArray;