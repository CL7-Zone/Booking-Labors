import logo from '../../logo.svg';
import styles from "./element.module.css";
import {Link, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {set} from "mdb-ui-kit/src/js/mdb/perfect-scrollbar/lib/css";

const Unauthorized = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [load, setLoad] = useState(false);
    const isLoading = useSelector(state => state.Array.isLoading);

    const redirect = async () =>{

        setLoad(true)
        await new Promise(resolve => setTimeout(resolve, 1000));
        setLoad(true);
        navigate("/login");
    }

    return (
        <div>
            {isLoading ? <div className="mt-5 pt-5"><img className="App-logo" width="30%" src={logo} alt=""/></div>
            : (
                <section className={styles.page_404}>
                    <div className="container">
                        <div className="row">
                            <div className="col-sm-12 d-flex align-items-center justify-content-center">
                                <div className="col-sm-10 col-sm-offset-1  text-center">
                                    <div className={styles.four_zero_four_bg}>
                                        <h1 className="text-center ">401 Unauthorized</h1>
                                    </div>
                                    <div className={styles.contant_box_404}>
                                        <h3 className="h2">Look like you're lost</h3>
                                        <p>the page you are looking for not avaible!</p>
                                        <Link onClick={redirect} className={styles.link_404}>
                                            Login please
                                            {load && (
                                                <img src={logo} width="10%" className="App-logo-load" alt=""/>)}
                                        </Link>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            )}
        </div>
    );
}

export default Unauthorized