import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {getCity} from "../../../redux/action/getCity";
import {useNavigate} from "react-router-dom";

const City = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const cities = useSelector(state => state.Array.cities);
    const isLoading = useSelector(state => state.Array.isLoading);

    useEffect(() => {
        dispatch(getCity());
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
                                <th scope="col">Tên thành phố</th>
                            </tr>
                            </thead>
                            <tbody>
                                {cities.map(city => (
                                    <tr key={city.id}>
                                        <td><input className="form-check-input" type="checkbox"/></td>
                                        <td>{city.id}</td>
                                        <td>{city.city_name}</td>
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
};

export default City;