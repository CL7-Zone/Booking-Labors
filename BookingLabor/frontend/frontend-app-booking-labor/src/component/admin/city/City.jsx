import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {getCity} from "../../../redux/action/getCity";
import {useNavigate} from "react-router-dom";
import {deleteApi, notifySuccess} from "../../../api/apiFunction";
import {setMessage} from "../../../redux/action/setMessage";

const City = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const cities = useSelector(state => state.Array.cities);
    const isLoading = useSelector(state => state.Array.isLoading);
    const [isFetch, setIsFetch] = useState(false);

    const fetchData = async () => {

        setIsFetch(true);
        await new Promise(resolve => setTimeout(resolve, 2000));
        dispatch(getCity());
        setIsFetch(false);
    }
    async function handleSubmit (event, cityId) {
        event.preventDefault();

        console.log("delete: ", cityId);
        try {
            const success =  await deleteApi("/admin/api/delete/city",
                {city_id : cityId}
            );
            if(success){
                console.log(success);
                dispatch(getCity());
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
                            Fetch City data
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
                                <th scope="col">Tên thành phố</th>
                                <th colSpan="2">ACTION</th>
                            </tr>
                            </thead>
                            <tbody>
                                {cities.map(city => (
                                    <tr key={city.id}>
                                        <td><input className="form-check-input" type="checkbox"/></td>
                                        <td>{city.id}</td>
                                        <td>{city.city_name}</td>
                                        <td><a className="btn btn-sm btn-primary" href="">Detail</a></td>
                                        <td>
                                            <button onClick={(event) =>
                                                handleSubmit(event, city.id)}
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

export default City;