import {useDispatch, useSelector} from "react-redux";
import React, {useEffect} from "react";
import {getApi} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {getUser} from "../../../redux/action/getUser";

const User = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const users = useSelector(state => state.Array.users);
    const isLoading = useSelector(state => state.Array.isLoading);

    useEffect(() => {
        dispatch(getUser());
    }, [dispatch]);

    useEffect(() => {
        console.log("user", users);
    }, [users]);

    return (
        <div>
            <div className="container-fluid pt-4 px-4">
                <div className="bg-light text-center rounded p-4">
                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <h6 className="mb-0">USER</h6>
                        <a href="">Show All</a>
                    </div>
                    <div className="table-responsive">
                        <table className="table text-start align-middle table-bordered table-hover mb-0">
                            <thead>
                            <tr className="text-dark">
                                <th scope="col"><input className="form-check-input" type="checkbox"/></th>
                                <th scope="col">ID</th>
                                <th scope="col">EMAIL</th>
                                <th scope="col">PROVIDER</th>
                                <th scope="col">ROLES</th>
                            </tr>
                            </thead>
                            <tbody>
                                {!isLoading && users.map(user => (
                                    <tr key={user.id}>
                                        <td><input className="form-check-input" type="checkbox"/></td>
                                        <td>{user.id}</td>
                                        <td>{user.email}</td>
                                        <td>{user.provider}</td>
                                        <td>
                                            {user.roles.map(role =>
                                                <input key={role.id} id={role.id} defaultValue={role.name}/>
                                            )}
                                        </td>
                                        <td><a className="btn btn-sm btn-primary" href="">Detail</a></td>
                                    </tr>
                                ))}
                            </tbody>

                        </table>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default User;