import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import Logout from "../admin/auth/Logout";
import React from "react";


const Footer = () => {
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);


    return (

        <footer>
            <div>
                {/* Footer Start */}
                <div className="bg-light rounded-top p-4">
                    <div className="row">
                        <div className="col-12 col-sm-6 text-center text-sm-start">
                            © <a href="#">Your Site Name</a>, All Right Reserved.
                        </div>
                        <div className="col-12 col-sm-6 text-center text-sm-end">
                            {/*/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. *** /*/}
                            Designed By <a href="https://htmlcodex.com">HTML Codex</a>
                            <br/>
                            Distributed By{" "}
                            <a
                                className="border-bottom"
                                href="https://themewagon.com"
                                target="_blank"
                            >
                                ThemeWagon
                            </a>
                        </div>
                    </div>
                </div>

            </div>
            {/* Content End */}
            {/* Back to Top */}
            <a href="#" className="btn btn-lg btn-primary btn-lg-square back-to-top">
                <i className="bi bi-arrow-up"/>
            </a>
        </footer>
    );
}

export default Footer;