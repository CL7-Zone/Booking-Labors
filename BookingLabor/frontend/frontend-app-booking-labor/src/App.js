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

function App() {

    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);

    useEffect(() => {
        const fetchUser = async ()=>{
            try{
                await getUserProfile();

            }catch (e) {
                document.cookie.split(";").forEach(function(c) {
                    document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/");
                });
                console.log("ERROR: ",e);
            }
        }
        fetchUser().then(r => r);

    }, []);

  return (
      <div className="App">
          <Routes>
              <Route path="/" element={<Home/>}/>
              <Route path="/404" element={<Error/>}/>
              <Route path="/admin/booking" element={<Booking/>}/>
              <Route path="/admin/profile" element={<Profile/>}/>
              <Route path="/login" element={<Login/>}></Route>
          </Routes>
      </div>
  );
}

export default App;
