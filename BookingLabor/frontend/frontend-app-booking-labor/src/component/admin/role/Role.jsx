import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {getUser} from "../../../redux/action/getUser";

const Role = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const users = useSelector(state => state.Array.users);
    const isLoading = useSelector(state => state.Array.isLoading);

    useEffect(() => {
        dispatch(getUser());
    }, [dispatch]);

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
                                <th scope="col">NAME</th>
                            </tr>
                            </thead>
                            <tbody>
                                {users.map(user => (
                                    user.roles.map(role=> (
                                            <tr key={role.id}>
                                                <td><input className="form-check-input" type="checkbox"/></td>
                                                <td>{role.id}</td>
                                                <td>{role.name}</td>
                                                <td><a className="btn btn-sm btn-primary" href="">Detail</a></td>
                                            </tr>
                                        )
                                    )
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    );
};

export default Role;