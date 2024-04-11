import axios from "axios"


export const api = axios.create({
    baseURL:"http://localhost:8080"
});

export const getHeader = () =>{

    const token = localStorage.getItem("token");

    return {
        Authorization: `Bearer ${token}`,
        "Content-Type" : "application/json",
    }

}

export async function loginUser(login){

    try{
        const res = await api.post("/auth-login", login)
        if(res.status >= 200 && res.status < 300){
            return res.data;
        }else{
            return null;
        }
    }catch (error) {
        console.log(error);
        return null;
    }

}

export async function getUserProfile() {
    try{
        const res = await api.get(`/admin/profile`,
    {headers: getHeader()});

        console.log(res);

        return res.data;
    }catch (error) {
        throw error;
    }
}