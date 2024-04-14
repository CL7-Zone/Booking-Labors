import logo from '../../logo.svg';
import Sidebar from "../element/Sidebar";
import Header from "../element/Header";
import Booking from "./booking/Booking";
import Footer from "../element/Footer";
import React, {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getUserProfile} from "../../api/apiFunction";
import NotFound from "../element/NotFound";
import { SetUser } from '../../redux/action/SetUser';
import User from "./user/User";
import Statistical from "../element/Statistical";

const Home = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);
    const user = useSelector(state => state.userProfile);

    useEffect(() => {
        console.log(show);
    }, [show]);

    useEffect(() => {
        const fetchUser = async ()=>{
            try{
                const user = await getUserProfile();
                dispatch(SetUser(user));
                dispatch({ type: 'SHOW' });
                await new Promise(resolve => setTimeout(resolve, 500));

                if(user){
                    dispatch({ type: 'HIDDEN' });
                }

            }catch (e) {
                dispatch({ type: 'HIDDEN' });
                console.error("ERROR: ",e);
            }
        }
        fetchUser().then(r => r);

    }, []);

    if(Array.isArray(user) && user.length === 0){

    }
    if(show){
        return (
            <div className="mt-5 pt-5"><img className="App-logo" width="30%" src={logo} alt=""/></div>
        );
    }
    return (
        <div>
            {Array.isArray(user) && user.length === 0 ? (<NotFound />) : (
                <>
                    <Sidebar/>
                    <div className="content">
                        <Header/>
                        <div className="container-fluid pt-4 px-4">
                            <div className="row g-4">
                                <Statistical></Statistical>
                                <Statistical></Statistical>
                            </div>
                        </div>
                        <User/>
                        <Footer/>
                    </div>
                </>
            )}
        </div>
    );


}

export default Home;