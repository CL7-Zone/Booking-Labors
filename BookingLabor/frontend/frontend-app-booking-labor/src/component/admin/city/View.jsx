import React, {useEffect} from 'react';
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import logo from "../../../logo.svg";
import Unauthorized from "../../element/Unauthorized";
import Sidebar from "../../element/Sidebar";
import Header from "../../element/Header";
import User from "../user/User";
import Footer from "../../element/Footer";
import City from "./City";
import {getUserProfile} from "../../../redux/action/getUserProfile";
import Create from "../city/Create";

const View = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const user = useSelector(state => state.Array.user);
    const isLoading = useSelector(state => state.Array.isLoading);

    useEffect(() => {
        dispatch(getUserProfile());
    }, []);

    return (
        <div>
            {Array.isArray(user) && user.length === 0 ? (<Unauthorized />) : (
                <>
                    <Sidebar/>
                    <div className="content">
                        <Header/>
                        <Create/>
                        <City/>
                        <Footer/>

                    </div>
                </>
            )}
        </div>
    );
};

export default View;