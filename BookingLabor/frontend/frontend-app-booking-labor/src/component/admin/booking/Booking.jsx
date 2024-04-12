import React, {useEffect} from "react";
import {getUserProfile} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";


const Booking = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);

    useEffect(() => {
        const fetchUser = async ()=>{
            try{
                const user = await getUserProfile();
                dispatch({ type: 'SHOW' });
            }catch (e) {
                navigate("/login");
                dispatch({ type: 'HIDDEN' });
            }
        }

        fetchUser().then(r => r);

    }, []);

    if(show){
        return (
            <div>
                <h1>Booking</h1>
            </div>
        );
    }


}

export default Booking