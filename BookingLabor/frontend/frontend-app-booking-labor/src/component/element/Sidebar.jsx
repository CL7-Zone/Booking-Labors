import {useSelector} from "react-redux";
import {Link} from "react-router-dom";
import avatar from '../../image/user.jpg';


const Sidebar  = () => {

    const user = useSelector(state => state.userProfile);

    return (
        <div>
            {/* Sidebar Start */}

            <div className="sidebar pe-4 pb-3">
                <nav className="navbar bg-light navbar-light">
                    <Link to="/" className="navbar-brand mx-4 mb-3">
                        <h3 className="text-primary">
                            <i className="fa fa-user"/>
                            {user && user.account && user.account.authorities[0] &&
                            " "+user.account.authorities[0].authority}
                        </h3>
                    </Link>
                    <div className="d-flex align-items-center ms-4 mb-4">
                        <div className="position-relative">
                            <img
                                className="rounded-circle"
                                src={avatar}
                                alt=""
                                style={{width: 40, height: 40}}
                            />
                            <div
                                className="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"/>
                        </div>
                        <div className="ms-3">
                            <h6 className="mb-0">
                                {user && user.account && user.account.username}
                            </h6>
                            <span> {user && user.account && user.account.authorities[0] &&
                                " "+user.account.authorities[0].authority}</span>
                        </div>
                    </div>
                    <div className="navbar-nav w-100">
                        <Link to="/" className="nav-item nav-link active">
                            <i className="fa fa-tachometer-alt me-2"/>
                            Dashboard
                        </Link>
                        <div className="nav-item dropdown">
                            <a
                                href="#"
                                className="nav-link dropdown-toggle"
                                data-bs-toggle="dropdown"
                            >
                                <i className="fa fa-laptop me-2"/>
                                Elements
                            </a>
                            <div className="dropdown-menu bg-transparent border-0">
                                <a href="button.html" className="dropdown-item">
                                    Buttons
                                </a>
                                <a href="typography.html" className="dropdown-item">
                                    Typography
                                </a>
                                <a href="element.html" className="dropdown-item">
                                    Other Elements
                                </a>
                            </div>
                        </div>
                        <Link to="/admin/profile" className="nav-item nav-link">
                            <i className="fa fa-user me-2"/>
                            PROFILE
                        </Link>
                        <a href="form.html" className="nav-item nav-link">
                            <i className="fa fa-keyboard me-2"/>
                            Forms
                        </a>
                        <a href="table.html" className="nav-item nav-link">
                            <i className="fa fa-table me-2"/>
                            Tables
                        </a>
                        <a href="chart.html" className="nav-item nav-link">
                            <i className="fa fa-chart-bar me-2"/>
                            Charts
                        </a>
                        <div className="nav-item dropdown">
                            <a
                                href="#"
                                className="nav-link dropdown-toggle"
                                data-bs-toggle="dropdown"
                            >
                                <i className="far fa-file-alt me-2"/>
                                Pages
                            </a>
                            <div className="dropdown-menu bg-transparent border-0">
                                <a href="signin.html" className="dropdown-item">
                                    Sign In
                                </a>
                                <a href="signup.html" className="dropdown-item">
                                    Sign Up
                                </a>
                                <a href="404.html" className="dropdown-item">
                                    404 Error
                                </a>
                                <a href="blank.html" className="dropdown-item">
                                    Blank Page
                                </a>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>


            {/* Sidebar End */}
        </div>
    );

}

export default Sidebar;