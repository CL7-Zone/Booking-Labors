import React, {useState, useEffect} from "react";
import {getUserProfile, loginUser} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {post} from "axios";

const Profile = () =>{

    const [user, setUser] = useState({});
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");
    const username = localStorage.getItem("username");
    const userRole = localStorage.getItem("userRole");

    useEffect(() => {
        const fetchUser = async ()=>{
            try{
                const user = await getUserProfile();

            }catch (e) {
                console.log(e);
            }
        }

        fetchUser().then(r => r);

    }, []);


    return (
        <div>

        </div>
    );
}

export default Profile