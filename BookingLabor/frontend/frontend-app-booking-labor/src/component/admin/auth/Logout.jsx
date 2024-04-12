import React, {useEffect, useState} from "react";
import {loginUser, logoutUser} from "../../../api/apiFunction";
import {useNavigate, Link} from "react-router-dom";
import Cookies from "js-cookie";
import { useDispatch, useSelector } from 'react-redux';

const Logout =()=>{

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);

    useEffect(() => {
        console.log(show);
    }, []);

    const handleLogout = async (e)=>{

        e.preventDefault();
        const success = await logoutUser();
        if(success){
            console.log("Logout successfully");
            navigate("/login");
        }
    }

    return (
        <span>
             <Link to="#" onClick={handleLogout}>Logout</Link>
        </span>
    );
}


export default Logout