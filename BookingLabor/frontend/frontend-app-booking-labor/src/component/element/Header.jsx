import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import Logout from "../admin/auth/Logout";
import React from "react";


const Header = () => {
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);

    return (
        <header>
            {!show && ( <Link style={{marginRight: "20px"}} to={"/login"}>login</Link>)}
            <Link style={{marginRight: "20px"}} to={"/"}>home</Link>
            {show && (<Link style={{marginRight: "20px"}} to={"/admin/booking"}>booking</Link>)}
            <Link style={{marginRight: "20px"}} to={"/admin/profile"}>profile</Link>
            {show && (<Logout/>)}
        </header>
    );
}

export default Header;