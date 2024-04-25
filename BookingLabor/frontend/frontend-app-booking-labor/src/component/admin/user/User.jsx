import {useDispatch, useSelector} from "react-redux";
import React, {useEffect, useRef, useState} from "react";
import {getApi, addApi, notifySuccess, deleteApi} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {getUser} from "../../../redux/action/getUser";
import {getRole} from "../../../redux/action/getRequest";

const User = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const users = useSelector(state => state.Array.users);
    const isLoading = useSelector(state => state.Array.isLoading);
    const roles = useSelector(state => state.Array.roles);
    const [role_name, setSelectedRole] = useState('');

    useEffect(() => {
        dispatch(getUser());
    }, []);

    useEffect(() => {
        dispatch(getRole());
    }, []);

    useEffect(() => {
        console.log("users", users);
    }, []);

    const handleRoleChange = (e) => {
        setSelectedRole(e.target.value);
    };

    const handleDeleteUserRole = async (e) => {
        e.preventDefault();

        const form = e.target;
        const email = form.elements.email.value;
        const role_name = form.elements.name.value;

        try {
            const success = await deleteApi("/admin/api/delete/user-role", {
                email : email,
                name: role_name,
            });
            if(success){
                console.log(success);
                dispatch(getUser());
                await notifySuccess("XÓA QUYỀN THÀNH CÔNG", 3000);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleAddRole = async (e) => {
        e.preventDefault();

        const form = e.target;
        const email = form.elements.email.value;

        try {
            const success = await addApi("/admin/api/role-user/save", {
                email : email,
                name: role_name,
            });
            if(success){
                console.log(success);
                dispatch(getUser());
                await notifySuccess("THÊM QUYỀN THÀNH CÔNG", 3000);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

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
                                <th colSpan="3">ACTION</th>
                            </tr>
                            </thead>
                            <tbody>
                                {!isLoading && users.map((user, index) => (
                                    <tr key={user.id}>
                                        <td><input className="form-check-input" type="checkbox"/></td>
                                        <td>{user.id}</td>
                                        <td>{user.email}</td>
                                        <td>{user.provider}</td>
                                        <td>
                                            {user.roles.map(role => (
                                                <form className="pt-3" onSubmit={handleDeleteUserRole} key={`${user.id}-${role.id}`}>
                                                    <input
                                                       className="form-control"
                                                       type="text"
                                                       name="email"
                                                       defaultValue={user.email}
                                                       readOnly={true}
                                                    />
                                                    <div className="pb-3 pt-3">
                                                        <input
                                                           type="radio"
                                                           name="name"
                                                           value={role.name}
                                                           aria-label="Default radio example"
                                                           id={role.id}
                                                           checked={role_name === role.name}
                                                           onChange={() => setSelectedRole(role.name)}
                                                        />
                                                        <label htmlFor={role.id}>{role.name}</label>
                                                    </div>


                                                    <button type="submit" className="btn btn-danger">XÓA QUYỀN</button>
                                                </form>
                                                )
                                            )}
                                        </td>
                                        <td>
                                            <form id={user.id} onSubmit={handleAddRole}>
                                                <input
                                                   type="text"
                                                   name="email"
                                                   className="form-control"
                                                   defaultValue={user.email}
                                                   readOnly={true}
                                                />
                                                <select className="form-select"
                                                        name="name"
                                                        aria-label="Default select example"
                                                        onChange={handleRoleChange}>
                                                    {roles.map((role, index) =>
                                                        <option key={`${role.id}`}  value={role.name}>
                                                            {role.name}
                                                        </option>
                                                    )}
                                                </select>
                                                <button type="submit" className="btn btn-success">Thêm quyền</button>
                                            </form>
                                        </td>
                                        <td><a className="btn btn-sm btn-primary" href="">CHI TIẾT</a></td>
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