import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {getJob} from "../../../redux/action/getJob";
import {deleteApi, notifySuccess} from "../../../api/apiFunction";
import {setMessage} from "../../../redux/action/setMessage";

const Job = () => {

    const dispatch = useDispatch();
    const jobs = useSelector(state => state.Array.jobs);
    const isLoading = useSelector(state => state.Array.isLoading);

    useEffect(() => {
        dispatch(getJob());
    }, [dispatch]);

    useEffect(() => {
        console.log("jobs: ",jobs);
    }, [jobs]);

    async function handleSubmit (event, jobId) {
        event.preventDefault();

        console.log("delete: ", jobId);
        try {
            const success =  await deleteApi("/admin/api/delete/job",
                {job_id : jobId}
            );
            if(success){
                console.log(success);
                dispatch(getJob());
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
                        <h6 className="mb-0">JOB</h6>
                        <a href="">Show All</a>
                    </div>
                    <div className="table-responsive">
                        <table className="table text-start align-middle table-bordered table-hover mb-0">
                            <thead>
                            <tr className="text-dark">
                                <th scope="col"><input className="form-check-input" type="checkbox"/></th>
                                <th scope="col">ID</th>
                                <th scope="col">TÊN CÔNG VIỆC</th>
                                <th scope="col">ẢNH</th>
                                <th scope="col">LƯƠNG</th>
                                <th colSpan="2">ACTION</th>
                            </tr>
                            </thead>
                            <tbody>
                                {jobs.map(job => (
                                    <tr key={job.id}>
                                        <td><input className="form-check-input" type="checkbox"/></td>
                                        <td>{job.id}</td>
                                        <td>{job.nameJob}</td>
                                        <td>{job.imageJob}</td>
                                        <td>{job.price}</td>
                                        <td><a className="btn btn-sm btn-primary" href="">CHI TIẾT</a></td>
                                        <td>
                                            <button onClick={(event) =>
                                                handleSubmit(event, job.id)}
                                                    className="btn btn-danger"
                                                    type="submit">
                                                XÓA
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    );
};

export default Job;