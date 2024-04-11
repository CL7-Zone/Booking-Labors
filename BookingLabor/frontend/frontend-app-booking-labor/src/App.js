import logo from './logo.svg';
import { Routes, Route, useNavigate, Link, useLocation } from 'react-router-dom';
import React, {useState, useEffect, useContext} from 'react';
import Login from "./component/admin/auth/Login";
import './App.css';
import Profile from "./component/admin/profile/Profile";

function App() {
  return (
    <div className="App">
        <div className="container">
            <header>
                <Link style={{marginRight: "20px"}} to={"/login"}>login</Link>
                <Link style={{marginRight: "20px"}} to={"/"}>home</Link>
                <Link to={"/logout"}>logout</Link>
            </header>
            <Routes>
                <Route path="/" element={<Profile/>}/>
                <Route path="/login" element={<Login></Login>}></Route>
            </Routes>
        </div>
    </div>
  );
}

export default App;
