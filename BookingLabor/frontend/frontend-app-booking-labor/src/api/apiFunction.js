import axios from "axios"
import Cookies from "js-cookie";

export const api = axios.create({
    baseURL:"http://localhost:8080"
});



export async function loginUser(login){

    try{
        const res = await api.post("/auth-login", login)
        if(res.status >= 200 && res.status < 300){
            return res.data;
        }
    }catch (error) {
        console.log("error: "+error);
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