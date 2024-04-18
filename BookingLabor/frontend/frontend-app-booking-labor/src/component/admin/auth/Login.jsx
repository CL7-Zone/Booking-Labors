import React, {useEffect, useState} from "react";
import {loginUser, notify} from "../../../api/apiFunction";
import {Link, useNavigate} from "react-router-dom";
import Cookies from "js-cookie";
import Header from "../../element/Header";
import logo from '../../../logo.svg';
import {jwtDecode} from "jwt-decode";
import {post} from "axios";
import {useDispatch, useSelector} from "react-redux";
import Logout from "./Logout";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

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
            console.log(success);
            dispatch({ type: 'SHOW' });
            await new Promise(resolve => setTimeout(resolve, 1000));
            dispatch({ type: 'HIDDEN' });
            Cookies.set("token", success.accessToken, { expires: Date.now() + 3000000, path: "/" });
            navigate("/");
            window.location.reload();
        }else {
            await notify("Login failed!!!", 3000);
            console.log("Invalid username or password!");
        }
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
                                {/*<div>*/}

                                {/*    <Link className="form-check-label" htmlFor="exampleCheck1">*/}
                                {/*        {login.email}*/}
                                {/*    </Link>*/}
                                {/*</div>*/}
                                {/*<div >*/}
                                {/*    <Link to="/login" className="form-check-label">*/}
                                {/*        {login.password}*/}
                                {/*    </Link>*/}
                                {/*</div>*/}
                            </div>
                            <button type="submit" className="btn btn-dark py-3 w-100 mb-4" style={{color:"white"}}>
                                Sign In {show && (<img className="App-logo-load" width={"20%"} src={logo} alt=""/>)}
                            </button>
                        </form>

                        <p className="text-center mb-0">
                            Don't have an Account? <a href="">Sign Up</a>
                        </p>

                    </div>
                </div>
            </div>

        </div>
    );
}

export default Login