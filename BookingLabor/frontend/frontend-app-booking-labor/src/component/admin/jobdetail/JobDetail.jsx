import {useDispatch, useSelector} from "react-redux";
import React, {useEffect} from "react";
import {getApi} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {getUser} from "../../../redux/action/getUser";

const JobDetail = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const users = useSelector(state => state.Array.users);
    const isLoading = useSelector(state => state.Array.isLoading);

    return (
        <div>
            <div className="container-fluid pt-4 px-4">
                <div className="bg-light text-center rounded p-4">
                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <h6 className="mb-0">DETAIL LABOR AND JOB</h6>
                        <a href="">Show All</a>
                    </div>
                    <div className="table-responsive">
                        <table className="table text-start align-middle table-bordered table-hover mb-0">
                            <thead>
                            <tr className="text-dark">
                                <th scope="col"><input className="form-check-input" type="checkbox"/></th>
                                <th scope="col">ID</th>
                                <th scope="col">EMAIL</th>
                                <th scope="col">HỌ TÊN NGƯỜI LAO ĐỘNG</th>
                                <th scope="col">TRÌNH ĐỘ - CHI TIẾT CÔNG VIỆC</th>
                                <th scope="col">KINH NGHIỆM - CHI TIẾT CÔNG VIỆC</th>
                                <th scope="col">NƠI LÀM VIỆC CHÍNH THỨC</th>
                                <th scope="col">TRÌNH ĐỘ - ỨNG TUYỂN</th>
                                <th scope="col">TRÌNH ĐỘ - KINH NGHIỆM</th>
                                <th colSpan="2">ACTION</th>
                            </tr>
                            </thead>
                            <tbody>
                                {users.map(user =>
                                    user.labors.map(labor=>
                                                labor.jobDetails.map(jobDetail=> (
                                                    user.applies.map(apply=> (
                                                            <tr key={jobDetail.id}>
                                                                <td><input className="form-check-input" type="checkbox"/></td>
                                                                <td>{jobDetail.id}</td>
                                                                <td>{user.email}</td>
                                                                <td>{labor.full_name}</td>
                                                                <td>{jobDetail.education}</td>
                                                                <td>{jobDetail.experience}</td>
                                                                <td>{jobDetail.official_work_address}</td>
                                                                <td>{apply.education}</td>
                                                                <td>{apply.experience}</td>
                                                                <td><a className="btn btn-sm btn-primary" href="">Detail</a></td>
                                                            </tr>
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    )
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default JobDetail;