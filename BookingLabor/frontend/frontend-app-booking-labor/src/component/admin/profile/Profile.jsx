import React, {useState, useEffect} from "react";
import {getHeader, getHeaders, getUserProfile, loginUser} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {post} from "axios";
import Cookies from "js-cookie";
import {useDispatch, useSelector} from "react-redux";
import Header from "../../element/Header";
import Footer from "../../element/Footer";
import Sidebar from "../../element/Sidebar";
import Booking from "../booking/Booking";
import {Link} from "react-router-dom";
import styles from "../../element/element.module.css";
import Unauthorized from "../../element/Unauthorized";
import { SetUser } from '../../../redux/action/SetUser';
import User from "../user/User";
import Statistical from "../../element/Statistical";

const Profile = () =>{

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);
    const user = useSelector(state => state.userProfile);

    useEffect(() => {
        const fetchUser = async ()=>{
            try{
                const user = await getUserProfile();
                if(user){
                    dispatch({ type: 'SHOW' });
                }
                dispatch(SetUser(user));

            }catch (e) {

                console.log("ERROR: ",e);
            }
        }

        fetchUser().then(r => r);

    }, []);

    useEffect(() => {
        console.log("user: ", user);
    }, [user]);

    useEffect(() => {
        setTimeout(()=>{

        },3000);
    }, [user]);

    if (Array.isArray(user) && user.length === 0) {

    }
    return (
        <div>
            {Array.isArray(user) && user.length === 0 ? (<Unauthorized />) : (
                <>
                    <Sidebar/>
                    <div className="content">
                        <Header/>
                        <Booking></Booking>
                        <User></User>
                        <Footer/>

                    </div>
                </>
            )}
        </div>
    );


}

export default Profile