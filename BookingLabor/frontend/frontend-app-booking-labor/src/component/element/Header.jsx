import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import Logout from "../admin/auth/Logout";
import React, {useEffect} from "react";
import avatar from '../../image/user.jpg';

const Header = () => {
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);
    const user = useSelector(state => state.userProfile);

    return (
        <div>

            <div>
                {/* Navbar Start */}

                <nav className="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
                    <a href="index.html" className="navbar-brand d-flex d-lg-none me-4">
                        <h2 className="text-primary mb-0">
                            <i className="fa fa-hashtag"/>
                        </h2>
                    </a>
                    <a href="#" className="sidebar-toggler flex-shrink-0">
                        <i className="fa fa-bars"/>
                    </a>
                    <form className="d-none d-md-flex ms-4">
                        <input
                            className="form-control border-0"
                            type="search"
                            placeholder="Search"
                        />
                    </form>
                    <div className="navbar-nav align-items-center ms-auto">
                        <div className="nav-item dropdown">
                            <a
                                href="#"
                                className="nav-link dropdown-toggle"
                                data-bs-toggle="dropdown"
                            >
                                <i className="fa fa-envelope me-lg-2"/>
                                <span className="d-none d-lg-inline-flex">Message</span>
                            </a>
                            <div
                                className="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" className="dropdown-item">
                                    <div className="d-flex align-items-center">
                                        <img
                                            className="rounded-circle"
                                            src={avatar}
                                            alt=""
                                            style={{width: 40, height: 40}}
                                        />
                                        <div className="ms-2">
                                            <h6 className="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr className="dropdown-divider"/>
                                <a href="#" className="dropdown-item">
                                    <div className="d-flex align-items-center">
                                        <img
                                            className="rounded-circle"
                                            src={avatar}
                                            alt=""
                                            style={{width: 40, height: 40}}
                                        />
                                        <div className="ms-2">
                                            <h6 className="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr className="dropdown-divider"/>
                                <a href="#" className="dropdown-item">
                                    <div className="d-flex align-items-center">
                                        <img
                                            className="rounded-circle"
                                            src={avatar}
                                            alt=""
                                            style={{width: 40, height: 40}}
                                        />
                                        <div className="ms-2">
                                            <h6 className="fw-normal mb-0">Jhon send you a message</h6>
                                            <small>15 minutes ago</small>
                                        </div>
                                    </div>
                                </a>
                                <hr className="dropdown-divider"/>
                                <a href="#" className="dropdown-item text-center">
                                    See all message
                                </a>
                            </div>
                        </div>
                        <div className="nav-item dropdown">
                            <a
                                href="#"
                                className="nav-link dropdown-toggle"
                                data-bs-toggle="dropdown"
                            >
                                <i className="fa fa-bell me-lg-2"/>
                                <span className="d-none d-lg-inline-flex">Notificatin</span>
                            </a>
                            <div
                                className="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <a href="#" className="dropdown-item">
                                    <h6 className="fw-normal mb-0">Profile updated</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr className="dropdown-divider"/>
                                <a href="#" className="dropdown-item">
                                    <h6 className="fw-normal mb-0">New user added</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr className="dropdown-divider"/>
                                <a href="#" className="dropdown-item">
                                    <h6 className="fw-normal mb-0">Password changed</h6>
                                    <small>15 minutes ago</small>
                                </a>
                                <hr className="dropdown-divider"/>
                                <a href="#" className="dropdown-item text-center">
                                    See all notifications
                                </a>
                            </div>
                        </div>
                        <div className="nav-item dropdown">
                            <a
                                href="#"
                                className="nav-link dropdown-toggle"
                                data-bs-toggle="dropdown"
                            >
                                <img
                                    className="rounded-circle me-lg-2"
                                    src={avatar}
                                    alt=""
                                    style={{width: 40, height: 40}}
                                />
                                <span className="d-none d-lg-inline-flex">
                                        {user && user.account && (user.account.username)}
                                    </span>
                            </a>
                            <div
                                className="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
                                <Link to="/admin/profile" className="dropdown-item">
                                    Profile
                                </Link>
                                <a href="#" className="dropdown-item">
                                    Settings
                                </a>
                                <Logout/>
                            </div>
                        </div>
                    </div>
                </nav>

                {/* Navbar End */}
            </div>


        </div>
    );
}

export default Header;