import React, {useState} from 'react';
import {postApi, notifySuccess} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import { setMessage, setMsgSuccess } from "../../../redux/action/setMessage";
import {getRole} from "../../../redux/action/getRequest";
import {useDispatch, useSelector} from "react-redux";
import 'react-toastify/dist/ReactToastify.css';

const Create = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [file, setFile] = useState();
    const msg = useSelector(state => state.String.message);
    const msgSuccess = useSelector(state => state.String.messageSuccess);


    function handleChange(event) {
        setFile(event.target.files[0])
    }
    async function handleSubmit (event) {
        event.preventDefault();

        if (!file) {
            console.log("LỖI: File không tồn tại!!!");
            dispatch(setMessage("LỖI: File không tồn tại!!!"));
            await new Promise(resolve => setTimeout(resolve, 2000));
            dispatch(setMessage(""));
            return;
        }
        try{
            const formData = new FormData();
            formData.append('file', file);
            formData.append('fileName', file.name);
            const success =  await postApi("admin/api/role/save", formData);
            if(success){
                console.log(success);
                dispatch(getRole());
                await notifySuccess("TẠO THÀNH CÔNG", 3000);
            }
        }catch (e) {
            console.log("LỖI: ",e);
            dispatch(setMessage(e));
            await new Promise(resolve => setTimeout(resolve, 2000));
            dispatch(setMessage(""));
        }

    }
    return (
        <div>
            <div className="container-fluid pt-4 px-4">
                <div className="bg-light text-center rounded p-4">
                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <h6 className="mb-0">CREATE ROLE</h6>
                    </div>
                    <div>
                        <form onSubmit={handleSubmit}>
                            <div className="form-floating mb-3">
                                <input
                                    type="file"
                                    className="form-control"
                                    id="file"
                                    name="file"
                                    onChange={handleChange}
                                />
                                <label htmlFor="file">ROLE FILE</label>
                            </div>
                            <button className="btn btn-primary" type="submit">Upload</button>
                        </form>
                    </div>
                    <div className="pt-3">
                        <span className="text-danger">{msg}</span>
                        <span className="text-success">{msgSuccess}</span>
                    </div>

                </div>
            </div>

        </div>
    );
};

export default Create;