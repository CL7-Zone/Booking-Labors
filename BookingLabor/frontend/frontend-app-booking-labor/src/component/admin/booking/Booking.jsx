import React, {useEffect} from "react";
import {getApi, getUserProfile} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getBooking} from "../../../redux/action/getBooking";
import {getUser} from "../../../redux/action/getUser";

const Booking = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const users = useSelector(state => state.Array.users);
    const isLoading = useSelector(state => state.Array.isLoading);


    return (
        <div>
            <div className="container-fluid pt-4 px-4">
                <div className="bg-light text-center rounded p-4">
                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <h6 className="mb-0">BOOKING</h6>
                        <a href="">Show All</a>
                    </div>
                    <div className="table-responsive">
                        <table className="table text-start align-middle table-bordered table-hover mb-0">
                            <thead>
                            <tr className="text-dark">
                                <th scope="col"><input className="form-check-input" type="checkbox"/></th>
                                <th scope="col">ID</th>
                                <th scope="col">EMAIL</th>
                                <th scope="col">FULLNAME</th>
                                <th scope="col">CHECKIN</th>
                                <th scope="col">CHECKOUT</th>
                                <th scope="col">CANCEL TIME</th>
                                <th scope="col">TOTAL SALARY</th>
                                <th scope="col">MESSAGE</th>
                                <th scope="col">ACCEPT</th>
                                <th scope="col">STATUS</th>
                                <th scope="col">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                                {users.map(user => (
                                        user.customers.map(customer=>(
                                            customer.bookings.map(booking=> (
                                                    <tr key={booking.id}>
                                                        <td><input className="form-check-input" type="checkbox"/></td>
                                                        <td>{booking.id}</td>
                                                        <td>{user.email}</td>
                                                        <td>{customer.full_name}</td>
                                                        <td>{booking.checkin}</td>
                                                        <td>{booking.checkout}</td>
                                                        <td>{booking.cancel_time}</td>
                                                        <td>{booking.total_price}</td>
                                                        <td>{booking.message}</td>
                                                        <td>{booking.accept}</td>
                                                        <td>{booking.status}</td>
                                                        <td><a className="btn btn-sm btn-primary" href="">Detail</a></td>
                                                    </tr>
                                                )
                                                )
                                            )
                                        )
                                    )
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default Booking