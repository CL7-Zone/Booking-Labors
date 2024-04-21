import React, {useState, useEffect} from "react";
import {getHeader, getHeaders, loginUser} from "../../../api/apiFunction";
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
import User from "../user/User";
import Statistical from "../../element/Statistical";
import { getUserProfile } from "../../../redux/action/getUserProfile";
import logo from "../../../logo.svg";

const Profile = () =>{

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const user = useSelector(state => state.Array.user);
    const isLoading = useSelector(state => state.Array.isLoading);

    useEffect(() => {
        dispatch(getUserProfile());
    }, [dispatch]);

    return (
        <div>
            {Array.isArray(user) && user.length === 0 ? (<Unauthorized />) : (
                <>
                    <Sidebar/>
                    <div className="content">
                        <Header/>
                        <User/>
                        <Footer/>

                    </div>
                </>
            )}
        </div>
    );


}

export default Profile