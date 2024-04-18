import axios from "axios"
import Cookies from "js-cookie";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';


export const api = axios.create({
    baseURL:"http://localhost:8080"
});


export const notify = (text, time)=>{
    toast.error(text, {

        position: "top-center",
        autoClose: time,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",

    })
}


export async function loginUser(login){

    try{
        const res = await api.post("/api/login", login);
        if(res.status >= 200 && res.status < 300){
            return res.data;
        }
    }catch (error) {
        console.error("Login failed: ",error);
        return null;
    }

}

export async function getHeaders() {
    try{
        const res = await api.get(`/header-api`);

        return res.data;
    }catch (error) {
        throw error;
    }
}


export async function getApi(url) {
    try{
        const res = await api.get(url,
            {headers: getHeader()});

        return res.data;
    }catch (error) {
        throw error;
    }
}

export const getHeader = () =>{

    const token = Cookies.get("token");

    return {
        Authorization: `Bearer ${token}`,
        "Content-Type" : "application/json",
    }
}
export async function getUserProfile() {
    try{
        const res = await api.get(`/admin/profile`,
    {headers: getHeader()});

        return res.data;
    }catch (error) {
        throw error;
    }
}


export async function logoutUser() {
    try{
        const res = await api.post(`/logout`);
        document.cookie.split(";").forEach(function(c) {

            document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/");
        });

        return res.data;
    }catch (error) {
        throw error;
    }
}