import logo from '../../logo.svg';
import Sidebar from "../element/Sidebar";
import Header from "../element/Header";
import Booking from "./booking/Booking";
import Job from "./job/Job";
import Footer from "../element/Footer";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import { getUserProfile } from "../../redux/action/getUserProfile";
import Unauthorized from "../element/Unauthorized";
import User from "./user/User";
import Statistical from "../element/Statistical";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {set} from "mdb-ui-kit/src/js/mdb/perfect-scrollbar/lib/css";
import JobDetail from "./jobdetail/JobDetail";
import Post from "./post/Post";


const Home = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const user = useSelector(state => state.Array.user);
    const isLoading = useSelector(state => state.Array.isLoading);

    useEffect(() => {
        dispatch(getUserProfile());
    }, [dispatch]);

    useEffect(() => {

        console.log(isLoading);
    }, [dispatch]);


    return (
        <div>
            {Array.isArray(user) && user.length === 0 ? (<Unauthorized/>) : (
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
                        <JobDetail/>
                        <Post/>
                        <Booking/>
                        <Footer/>
                    </div>
                </>
            )}
        </div>
    );


}

export default Home;