
import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react';
import axios from '../../utility/axios';
import authorize from '../../utility/Authorizer';
import userIdExtract from '../../utility/userIdExtractor';
import roleExtract from '../../utility/roleExtractor'
import  { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
//import TestDriveBooking from '../../test-drive-book/TestDriveBooking';

import './TesDriveBookingsList.css';

function TestDriveBookingsList() {
  const [testDrives, setTestDrives] = useState([]);
  const [filter, setFilter] = useState("booked");
  const jwt = localStorage.getItem("jwt");
  const navigate = useNavigate();

  useEffect(() => {
    if (!authorize(roleExtract())) {
      navigate("/unAuthorized");
    }
    testDriveBookingListFetcher();
  }, []);

  const testDriveBookingListFetcher = async () => {
    const userId = userIdExtract();
    const api = `/user/${userId}/test-drive-bookings`;

    try {
      const resp = await axios.get(api, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      setTestDrives(resp.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleCancel = async (id) => {
    const userId = userIdExtract();
    const api = `/user/${userId}/test-drive-booking/${id}`;

    try {
      const response = await axios.delete(api, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      setTestDrives((prevTestDrives) =>
        prevTestDrives.map((drive) =>
          drive.testDriveBookId === id ? { ...drive, isVisible: false } : drive
        )
      );

      alert("Successfully cancelled the test drive booking!");
    } catch (error) {
      console.error("Error cancelling booking:", error);
      alert("Failed to cancel the booking. Please try again.");
    }
  };

  const filteredTestDrives = testDrives.filter((drive) =>
    filter === "booked" ? drive.isVisible : !drive.isVisible
  );

  return (
    <div className="container">
      <div className="filter-box">
        <label>
          <input
            type="radio"
            value="booked"
            checked={filter === "booked"}
            onChange={() => setFilter("booked")}
          />{" "}
          Test Drive Booked
        </label>
        <label>
          <input
            type="radio"
            value="cancelled"
            checked={filter === "cancelled"}
            onChange={() => setFilter("cancelled")}
          />{" "}
          Cancelled
        </label>
      </div>

      <table className="table">
        <thead className="table-head">
          <tr>
            <th className="table-header">Test Drive Book ID</th>
            <th className="table-header">Model Name</th>
            <th className="table-header">Booked Date</th>
            <th className="table-header">Test Drive Date</th>
            <th className="table-header">Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredTestDrives.map((drive) => (
            <tr key={drive.testDriveBookId} className="table-row">
              <td className="table-data">{drive.testDriveBookId}</td>
              <td className="table-data">{drive.model.modelName}</td>
              <td className="table-data">
                {new Date(drive.bookedDate).toLocaleString()}
              </td>
              <td className="table-data">
                {new Date(drive.testDriveDate).toLocaleString()}
              </td>
              <td className="table-data">
                <button
                  className="cancel-button"
                  onClick={() => handleCancel(drive.testDriveBookId)}
                  disabled={!drive.isVisible}
                >
                  Cancel
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TestDriveBookingsList;


// function TestDriveBookingsList() {
//   const [testDrives, setTestDrives] = useState([]);
//   const [filter, setFilter] = useState('booked');
//   const jwt=localStorage.getItem('jwt');

//   const testDriveBookings = [
//     { testDriveBookId: 1, modelName: "Mahindra", bookedDate: "2025-02-11T12:34:56.789", testDriveDate: "2025-02-13T12:34:56.789", isVisible: true },
//     { testDriveBookId: 2, modelName: "Tata", bookedDate: "2025-03-12T09:15:00.000", testDriveDate: "2025-03-14T09:15:00.000", isVisible: true },
//     { testDriveBookId: 3, modelName: "Hyundai", bookedDate: "2025-04-05T14:30:30.500", testDriveDate: "2025-04-07T14:30:30.500", isVisible: false },
//     { testDriveBookId: 4, modelName: "Toyota", bookedDate: "2025-05-10T08:45:10.000", testDriveDate: "2025-05-12T08:45:10.000", isVisible: true },
//     { testDriveBookId: 5, modelName: "Honda", bookedDate: "2025-06-20T17:20:30.200", testDriveDate: "2025-06-22T17:20:30.200", isVisible: false },
//     { testDriveBookId: 6, modelName: "Ford", bookedDate: "2025-07-01T10:05:40.300", testDriveDate: "2025-07-03T10:05:40.300", isVisible: true },
//     { testDriveBookId: 7, modelName: "BMW", bookedDate: "2025-08-15T16:55:55.600", testDriveDate: "2025-08-17T16:55:55.600", isVisible: true }
//   ];

//   const testDriveBookingListFetcher = async () => {
//     const userId=userIdExtract(); 
//     const api=`/user/${userId}/test-drive-bookings`
//     try {
//       const resp = await axios.get(api,{
//         headers: {
//             'Authorization': `Bearer ${jwt}`
//         }});
//       console.log("Test List:",resp.data);
//       setTestDrives(resp.data);
//     } catch (error) {
//       console.log(error);
//     }
//   };

//   useEffect(() => {

//     testDriveBookingListFetcher();

//   }, []);

//   const handleCancel = (id) => {
//     setTestDrives((prevTestDrives) =>
//       prevTestDrives.map((drive) =>
//         drive.testDriveBookId === id ? { ...drive, isVisible: false } : drive
//       )
//     );
//   };

//   const filteredTestDrives = testDrives.filter(drive => 
//     filter === 'booked' ? drive.isVisible : !drive.isVisible
//   );

//   return (
//     <div className="container">
//       <div className="filter-box">
//         <label>
//           <input
//             type="radio"
//             value="booked"
//             checked={filter === 'booked'}
//             onChange={() => setFilter('booked')}
//           /> Test Drive Booked
//         </label>
//         <label>
//           <input
//             type="radio"
//             value="cancelled"
//             checked={filter === 'cancelled'}
//             onChange={() => setFilter('cancelled')}
//           /> Cancelled
//         </label>
//       </div>

//       <table className="table">
//         <thead className="table-head">
//           <tr>
//             <th className="table-header">Test Drive Book ID</th>
//             <th className="table-header">Model Name</th>
//             <th className="table-header">Booked Date</th>
//             <th className="table-header">Test Drive Date</th>
//             <th className="table-header">Actions</th>
//           </tr>
//         </thead>
//         <tbody>
//           {filteredTestDrives.map((drive) => (
//             <tr key={drive.testDriveBookId} className="table-row">
//               <td className="table-data">{drive.testDriveBookId}</td>
//               <td className="table-data">{drive.model.modelName}</td>
//               <td className="table-data">{new Date(drive.bookedDate).toLocaleString()}</td>
//               <td className="table-data">{new Date(drive.testDriveDate).toLocaleString()}</td>
//               <td className="table-data">
//                 <button
//                   className="cancel-button"
//                   onClick={() => handleCancel(drive.testDriveBookId)}
//                   disabled={!drive.isVisible}
//                 >
//                   Cancel
//                 </button>
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//     </div>
//   );
// }

// export default TestDriveBookingsList;


// function TestDriveBookingsList() {
//   const [testDrives, setTestDrives] = useState([]);

//   const testDriveBookings = [
//     { testDriveBookId: 1, modelName: "Mahindra", bookedDate: "2025-02-11T12:34:56.789", testDriveDate: "2025-02-13T12:34:56.789" },
//     { testDriveBookId: 2, modelName: "Tata", bookedDate: "2025-03-12T09:15:00.000", testDriveDate: "2025-03-14T09:15:00.000" },
//     { testDriveBookId: 3, modelName: "Hyundai", bookedDate: "2025-04-05T14:30:30.500", testDriveDate: "2025-04-07T14:30:30.500" },
//     { testDriveBookId: 4, modelName: "Toyota", bookedDate: "2025-05-10T08:45:10.000", testDriveDate: "2025-05-12T08:45:10.000" },
//     { testDriveBookId: 5, modelName: "Honda", bookedDate: "2025-06-20T17:20:30.200", testDriveDate: "2025-06-22T17:20:30.200" },
//     { testDriveBookId: 6, modelName: "Ford", bookedDate: "2025-07-01T10:05:40.300", testDriveDate: "2025-07-03T10:05:40.300" },
//     { testDriveBookId: 7, modelName: "BMW", bookedDate: "2025-08-15T16:55:55.600", testDriveDate: "2025-08-17T16:55:55.600" }
//   ];

//   useEffect(() => {
//     setTestDrives(testDriveBookings);
//   }, []);

//   const handleDelete = async (id) => {
//     try {
//       setTestDrives((prevTestDrives) =>
//         prevTestDrives.filter((drive) => drive.testDriveBookId !== id)
//       );
//     } catch (error) {
//       console.error(`Error deleting test drive with id ${id}:`, error);
//     }
//   };

//   return (
//     <div className="container">
//       <table className="table">
//         <thead className="table-head">
//           <tr>
//             <th className="table-header">Test Drive Book ID</th>
//             <th className="table-header">Model Name</th>
//             <th className="table-header">Booked Date</th>
//             <th className="table-header">Test Drive Date</th>
//             <th className="table-header">Actions</th>
//           </tr>
//         </thead>
//         <tbody>
//           {testDrives.map((drive) => (
//             <tr key={drive.testDriveBookId} className="table-row">
//               <td className="table-data">{drive.testDriveBookId}</td>
//               <td className="table-data">{drive.modelName}</td>
//               <td className="table-data">{new Date(drive.bookedDate).toLocaleString()}</td>
//               <td className="table-data">{new Date(drive.testDriveDate).toLocaleString()}</td>
//               <td className="table-data">
//                 <button className="delete-button" onClick={() => handleDelete(drive.testDriveBookId)}>
//                   Delete
//                 </button>
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//     </div>
//   );
// }

// export default TestDriveBookingsList;
