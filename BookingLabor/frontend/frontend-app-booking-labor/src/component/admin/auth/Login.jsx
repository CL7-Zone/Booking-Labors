import React, {useState} from "react";
import {loginUser} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {jwtDecode} from "jwt-decode";
import {post} from "axios";
const Login = ()=>{

    const [errorMessage, setErrorMessage] = useState("");
    const [login, setLogin] = useState({
        email: "",
        password: ""
    });
    const navigate = useNavigate();

    const handleInputChange = (e) =>{

        setLogin({...login, [e.target.name] : e.target.value});
    }

    const handleLogin = async (e)=>{

        e.preventDefault();
        const success = await loginUser(login);
        console.log(success);

        if(success){
            const token = success.accessToken;
            console.log(token)
            const decodedToken = jwtDecode(token);
            localStorage.setItem("token",token);
            console.log(decodedToken);
            localStorage.setItem("username",decodedToken.sub);
            localStorage.setItem("userId",success.id);
            localStorage.setItem("userRole",success.object.authorities[0].authority);
            navigate("/");
            window.location.reload();
        }else {
            setErrorMessage("Invalid username or password!");
        }
        setTimeout(()=>{
            setErrorMessage("");
        }, 4000)
    }

    return (
        <section className={"container"}>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <div className={"row mb-3"}>
                    <label htmlFor="email" className="col-sm-2 col-form-label">Email</label>
                    <div>
                        <input
                        type="email" id="email"
                        name="email" className="form-control"
                        value={login.email}
                        onChange={handleInputChange}
                        />
                    </div>
                    <label htmlFor="password" className="col-sm-2 col-form-label">Password</label>
                    <div>
                        <input
                        type="password" id="password"
                        name="password" className="form-control"
                        value={login.password}
                        onChange={handleInputChange}
                        />
                    </div>
                </div>
                <div>
                    <button type="submit" className="btn btn-primary">
                        LOGIN
                    </button>
                </div>
            </form>

            {errorMessage && <p className="alert alert-danger">{errorMessage}</p>}
        </section>
    );
}

export default Login