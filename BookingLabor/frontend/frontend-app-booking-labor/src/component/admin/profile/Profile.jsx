import React, {useState, useEffect} from "react";
import {getHeader, getHeaders, getUserProfile, loginUser} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {post} from "axios";
import Cookies from "js-cookie";
import {useDispatch, useSelector} from "react-redux";

const Profile = () =>{

    const [user, setUser] = useState({});
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);

    useEffect(() => {
        const fetchUser = async ()=>{
            try{
                const user = await getUserProfile();
                if(user){
                    dispatch({ type: 'SHOW' });
                }
                setUser(user);

            }catch (e) {
                dispatch({ type: 'HIDDEN' });
                console.log(e);
            }
        }

        fetchUser().then(r => r);

    }, []);

    useEffect(() => {
        console.log('User login:', user);
    }, [user]);


    useEffect(() => {
        const fetchHeader = async ()=>{
            try{
                const header = await getHeaders();

            }catch (e) {

                console.log(e);
            }
        }

        fetchHeader().then(r => r);

    }, []);

    useEffect(() => {
        setTimeout(()=>{
            if(!show){
                console.log("not user!!!")
                navigate("/login")
            }
        },3000);
    }, [show]);

    if(!show){

        return (
            <div>
                <h1>Not user</h1>
            </div>
        );
    }

    if(show){
        return (
            <div className="container">
                <div>
                    {user && user.account && (<span>{user.account.username}</span>)}
                </div>
                <div>
                    {user && <span>{user.userId}</span>}
                </div>
                <div>
                    {user && user.account && user.account.authorities[0] &&
                        (<span>{user.account.authorities[0].authority}</span>)}
                </div>
            </div>
        );
    }


}

export default Profile