import {useDispatch, useSelector} from "react-redux";
import React, {useEffect} from "react";
import {getApi} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {getUser} from "../../../redux/action/getUser";

const Post = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const users = useSelector(state => state.Array.users);
    const isLoading = useSelector(state => state.Array.isLoading);


    return (
        <div>
            <div className="container-fluid pt-4 px-4">
                <div className="bg-light text-center rounded p-4">
                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <h6 className="mb-0">POST</h6>
                        <a href="">Show All</a>
                    </div>
                    <div className="table-responsive">
                        <table className="table text-start align-middle table-bordered table-hover mb-0">
                            <thead>
                            <tr className="text-dark">
                                <th scope="col"><input className="form-check-input" type="checkbox"/></th>
                                <th scope="col">ID</th>
                                <th scope="col">EMAIL</th>
                                <th scope="col">Tên doanh nghiệp</th>
                                <th scope="col">Hình thức doanh nghiệp</th>
                                <th scope="col">Tiêu đề</th>
                                <th scope="col">Số điện thoại</th>
                                <th scope="col">Số lượng tuyển</th>
                                <th scope="col">Lương</th>
                                <th scope="col">Tuổi</th>
                                <th scope="col">Hình thức trả lương</th>
                                <th scope="col">Địa chỉ làm việc</th>
                                <th colSpan="2">ACTION</th>
                            </tr>
                            </thead>
                            <tbody>
                                {users.map(user =>
                                        user.posts.map(post=> (
                                                user.applies.map(apply=> (
                                                        <tr key={post.id}>
                                                            <td><input className="form-check-input" type="checkbox"/></td>
                                                            <td>{post.id}</td>
                                                            <td>{user.email}</td>
                                                            <td>{post.business_name}</td>
                                                            <td>{post.you_are}</td>
                                                            <td>{post.title}</td>
                                                            <td>{post.phone_number}</td>
                                                            <td>{post.quantity}</td>
                                                            <td>{`${post.min_payroll} - ${post.max_payroll}`}</td>
                                                            <td>{`Từ ${post.min_age} - ${post.max_age}`}</td>
                                                            <td>{`Theo ${post.pay_form}`}</td>
                                                            <td>{post.official_address}</td>
                                                            <td><a className="btn btn-sm btn-primary" href="">Detail</a></td>
                                                        </tr>
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

export default Post;