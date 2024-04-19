import React, {useState} from 'react';
import {postApi, notify} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import { setMessage } from "../../../redux/action/setMessage";
import {useDispatch, useSelector} from "react-redux";
import 'react-toastify/dist/ReactToastify.css';

const Create = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [file, setFile] = useState();
    const msg = useSelector(state => state.String.message);

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
            const success =  await postApi("admin/api/job/save", formData);
            if(success){
                console.log(success);
                await notify("TẠO THÀNH CÔNG", 3000);
                await new Promise(resolve => setTimeout(resolve, 2000));
                navigate("/jobs");
                window.location.reload();
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
                        <h6 className="mb-0">CREATE JOB</h6>
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
                                <label htmlFor="file">JOB FILE</label>
                            </div>
                            <button className="btn btn-primary" type="submit">Upload</button>
                        </form>
                    </div>
                    <div className="pt-3">
                        <span className="text-danger">{msg}</span>
                    </div>
                </div>
            </div>

        </div>
    );
};

export default Create;