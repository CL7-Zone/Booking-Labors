import React, {useEffect, useState} from "react";
import {loginUser, logoutUser} from "../../../api/apiFunction";
import {useNavigate, Link} from "react-router-dom";
import Cookies from "js-cookie";
import { useDispatch, useSelector } from 'react-redux';

const Logout =()=>{

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);

    const handleLogout = async (e)=>{

        e.preventDefault();
        const success = await logoutUser();
        if(success){
            document.cookie.split(";").forEach(function(c) {
                document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/");
            });
            console.log("Logout successfully");
            navigate("/login");
            window.location.reload();
        }
    }
    return (
        <>
           <Link className="dropdown-item" to="#" onClick={handleLogout}>Logout</Link>
        </>
    );
}


export default Logout