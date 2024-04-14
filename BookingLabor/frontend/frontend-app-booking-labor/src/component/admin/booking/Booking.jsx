import React, {useEffect} from "react";
import {getApi, getUserProfile} from "../../../api/apiFunction";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import { SetBooking } from '../../../redux/action/SetBooking';


const Booking = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const show = useSelector(state => state.showElement);
    const bookings = useSelector(state => state.bookings);
    const user = useSelector(state => state.userProfile);

    useEffect(() => {
        const fetch = async ()=>{
            try{
                const books = await getApi(`/admin/api/booking`);
                dispatch(SetBooking(books));
            }catch (e) {
                console.log(e);
            }
        }
        fetch().then(r => r);

    }, [dispatch]);

    useEffect(() => {
        console.log("Booking: ", bookings);

    }, [bookings]);

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
                            {bookings.map(booking => (
                                <tr key={booking.id}>
                                    <td><input className="form-check-input" type="checkbox"/></td>
                                    <td>{booking.id}</td>
                                    <td>{booking.checkin}</td>
                                    <td>{booking.checkout}</td>
                                    <td>{booking.cancel_time}</td>
                                    <td>{booking.total_price}</td>
                                    <td>{booking.message}</td>
                                    <td>{booking.accept}</td>
                                    <td>{booking.status}</td>
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

export default Booking