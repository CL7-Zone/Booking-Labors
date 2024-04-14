import React, {useEffect, useState} from "react";
import {loginUser} from "../../../api/apiFunction";
import {Link, useNavigate} from "react-router-dom";
import Cookies from "js-cookie";
import Header from "../../element/Header";
import logo from '../../../logo.svg';

import {jwtDecode} from "jwt-decode";
import {post} from "axios";
import {useDispatch, useSelector} from "react-redux";
import Logout from "./Logout";
const Login = ()=>{

    const [errorMessage, setErrorMessage] = useState("");
    const [login, setLogin] = useState({
        email: "",
        password: ""
    });
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);

    useEffect(() => {
        dispatch({ type: 'HIDDEN' });
    }, []);

    const handleInputChange = (e) =>{

        setLogin({...login, [e.target.name] : e.target.value});
    }
    const handleLogin = async (e)=>{

        e.preventDefault();
        const success = await loginUser(login);

        if(success){

            dispatch({ type: 'SHOW' });
            await new Promise(resolve => setTimeout(resolve, 1000));
            dispatch({ type: 'HIDDEN' });
            Cookies.set("token", success.accessToken, { expires: Date.now() + 3000000, path: "/" });
            navigate("/");
        }else {
            console.log("Invalid username or password!");
            setErrorMessage("Invalid username or password!");
        }
        setTimeout(()=>{
            setErrorMessage("");
        }, 3000)
    }

    return (

        <div className="container">

            <div
                className="row h-100 align-items-center justify-content-center"
                style={{minHeight: "100vh"}}
            >
                <div className="col-12 col-sm-8 col-md-6 col-lg-5 col-xl-4">
                    <div className="bg-light rounded p-4 p-sm-5 my-4 mx-3">
                        <div className="d-flex align-items-center justify-content-between mb-3">

                            <Link style={{marginRight: "20px"}} to={"/"}>
                                <h3 className="text-primary">
                                    <i className="fa fa-home"/>
                                    {" Home"}
                                </h3>
                            </Link>
                            <h3>Sign In</h3>
                        </div>
                        <form onSubmit={handleLogin}>

                            <div className="form-floating mb-3">
                                <input
                                    type="email"
                                    className="form-control"
                                    id="email"
                                    name="email"
                                    placeholder="Email..."
                                    value={login.email}
                                    onChange={handleInputChange}
                                />
                                <label htmlFor="email">Email address</label>
                            </div>
                            <div className="form-floating mb-4">
                                <input
                                    type="password"
                                    className="form-control"
                                    id="password"
                                    name="password" className="form-control"
                                    value={login.password}
                                    onChange={handleInputChange}
                                    placeholder="Password"
                                />
                                <label htmlFor="password">Password</label>
                            </div>
                            <div className="d-flex align-items-center justify-content-between mb-4">
                                <div className="form-check">
                                    <input
                                        type="checkbox"
                                        className="form-check-input"
                                        id="exampleCheck1"
                                    />
                                    <label className="form-check-label" htmlFor="exampleCheck1">
                                        Check me out
                                    </label>
                                </div>
                                <a href="">Forgot Password</a>
                            </div>
                            <button  type="submit" className="btn btn-dark py-3 w-100 mb-4" style={{color:"white"}}>
                                Sign In {show && (<img className="App-logo-load" width={"20%"} src={logo} alt=""/>)}
                            </button>
                        </form>

                        <p className="text-center mb-0">
                            Don't have an Account? <a href="">Sign Up</a>
                        </p>
                        <div className="mt-3">
                            {errorMessage && <p className="text-danger">{errorMessage}</p>}
                        </div>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default Login