import logo from './logo.svg';
import { Routes, Route, useNavigate, Link, useLocation } from 'react-router-dom';
import React, {useState, useEffect, useContext} from 'react';
import Login from "./component/admin/auth/Login";
import Logout from "./component/admin/auth/Logout";
import './App.css';
import Profile from "./component/admin/profile/Profile";
import { useDispatch, useSelector } from 'react-redux';
import {getUserProfile} from "./api/apiFunction";
import Home from "./component/admin/Home";
import Error from "./component/admin/404";
import Booking from "./component/admin/booking/Booking";
import Header from "./component/element/Header";
import Footer from "./component/element/Footer";
import {toast, ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import { getJob } from "./redux/action/getJob";
import Job from "./component/admin/job/Job";
import ViewJob from "./component/admin/job/View";
import ViewRole from "./component/admin/role/View";
import ViewCity from "./component/admin/city/View";
function App() {

    const dispatch = useDispatch();

    useEffect(() => {
        const fetchUser = async ()=>{
            try{
                await getUserProfile();

            }catch (e) {
                document.cookie.split(";").forEach(function(c) {
                    document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/");
                });
            }
        }
        fetchUser().then(r => r);

    }, []);

  return (
      <div className="App">
          <Routes>
              <Route path="/" element={<Home/>}/>
              <Route path="/jobs" element={<ViewJob/>}/>
              <Route path="/roles" element={<ViewRole/>}/>
              <Route path="/cities" element={<ViewCity/>}/>
              <Route path="/users" element={<Profile/>}/>
              <Route path="/admin/profile" element={<Profile/>}/>
              <Route path="/login" element={<Login/>}></Route>
          </Routes>
          <ToastContainer />
      </div>
  );
}

export default App;
