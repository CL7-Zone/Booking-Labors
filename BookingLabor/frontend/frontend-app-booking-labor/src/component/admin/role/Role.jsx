import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {getUser} from "../../../redux/action/getUser";
import {getRole} from "../../../redux/action/getRequest";
import {postApi, notifySuccess, deleteApi} from "../../../api/apiFunction";
import {setMessage, setMsgSuccess} from "../../../redux/action/setMessage";


const Role = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const users = useSelector(state => state.Array.users);
    const roles = useSelector(state => state.Array.roles);
    const isLoading = useSelector(state => state.Array.isLoading);
    const msg = useSelector(state => state.String.message);
    const msgSuccess = useSelector(state => state.String.messageSuccess);
    const [isFetch, setIsFetch] = useState(false);

    const fetchData = async () => {

        setIsFetch(true);
        await new Promise(resolve => setTimeout(resolve, 2000));
        dispatch(getRole());
        setIsFetch(false);
    }

    async function handleSubmit (event, roleId) {
        event.preventDefault();

        console.log("delete: ", roleId);
        try {
            const success =  await deleteApi("/admin/api/delete/role",
                {role_id : roleId}
            );
            if(success){
                console.log(success);
                dispatch(getRole());
                await notifySuccess("XÓA THÀNH CÔNG", 3000);
            }
        }catch (e) {
            console.log("ERROR: ",e);
            dispatch(setMessage(e.message));
            await new Promise(resolve => setTimeout(resolve, 2000));
            dispatch(setMessage(""));
        }

    }
    return (
        <div>
            <div className="container-fluid pt-4 px-4">
                <div className="bg-light text-center rounded p-4">
                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <button className="btn btn-success"
                                onClick={fetchData}>
                            Fetch Role data
                        </button>
                        <a href="">Show All</a>
                    </div>
                    <div className="table-responsive">
                        {isFetch && (
                            <div className="mb-5">
                                <h2>Fetch data...</h2>
                            </div>
                        )}
                        <table className="table text-start align-middle table-bordered table-hover mb-0">
                        <thead>
                            <tr className="text-dark">
                                <th scope="col"><input className="form-check-input" type="checkbox"/></th>
                                <th scope="col">ID</th>
                                <th scope="col">NAME</th>
                            </tr>
                            </thead>
                            <tbody>
                                {roles.map(role=> (
                                        <tr key={role.id}>
                                            <td><input className="form-check-input" type="checkbox"/></td>
                                            <td>{role.id}</td>
                                            <td>{role.name}</td>
                                            <td><a className="btn btn-sm btn-primary" href="">CHI TIẾT</a></td>
                                            <td>
                                                <button onClick={(event) =>
                                                        handleSubmit(event, role.id)}
                                                        className="btn btn-danger"
                                                        type="submit">
                                                    XÓA
                                                </button>
                                            </td>
                                        </tr>
                                    )
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    );
};

export default Role;