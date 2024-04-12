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
import Booking from "./component/admin/booking/Booking";
import Header from "./component/element/Header";


function App() {

    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);

  return (
    <div className="App">
        <div className="container">

            <Header></Header>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/admin/booking" element={<Booking/>}/>
                <Route path="/admin/profile" element={<Profile/>}/>
                <Route path="/login" element={<Login/>}></Route>
            </Routes>
        </div>
    </div>
  );
}

export default App;
