import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react';
import axios from '../../utility/axios';
import  { jwtDecode } from 'jwt-decode';

function Orders() {
    const [purchases, setPurchases] = useState([]);
    const [filter, setFilter] = useState('bought');
  
    useEffect(() => {
      async function fetchPurchases() {
        const jwt = localStorage.getItem('jwt');
        const decodedToken = jwtDecode(jwt);
        const userId = decodedToken.userId;
        const api = `/user/${userId}/car-purchase-bookings`;
  
        try {
          const response = await axios.get(api, {
            headers: { 'Authorization': `Bearer ${jwt}` }
          });
          setPurchases(response.data);
        } catch (error) {
          console.error('Error fetching purchases:', error);
          alert('Error fetching purchases.');
        }
      }
  
      fetchPurchases();
    }, []);
  
    const handleCancel = async (purchaseId) => {
      const jwt = localStorage.getItem('jwt');
      const decodedToken = jwtDecode(jwt);
      const userId = decodedToken.userId;
      const api = `/user/${userId}/car-purchase-booking/${purchaseId}`;
  
      try {
        await axios.delete(api, {
          headers: { 'Authorization': `Bearer ${jwt}` }
        });
  
        // Update the specific purchase visibility
        setPurchases(prevPurchases =>
          prevPurchases.map(purchase =>
            purchase.purchaseId === purchaseId
              ? { ...purchase, isVisible: false }
              : purchase
          )
        );
  
        alert('Purchase cancelled successfully!');
      } catch (error) {
        console.error('Error cancelling purchase:', error);
        alert('Error cancelling the purchase.');
      }
    };
  
    const filteredPurchases = purchases.filter(purchase =>
      filter === 'bought' ? purchase.isVisible : !purchase.isVisible
    );
  
    return (
      <div className="container">
        <div className="filter-options">
          <label>
            <input
              type="radio"
              value="bought"
              checked={filter === 'bought'}
              onChange={() => setFilter('bought')}
            /> Bought
          </label>
          <label>
            <input
              type="radio"
              value="cancelled"
              checked={filter === 'cancelled'}
              onChange={() => setFilter('cancelled')}
            /> Cancelled
          </label>
        </div>
  
        <table className="table">
          <thead className="table-head">
            <tr>
              <th>Purchase ID</th>
              <th>Model Name</th>
              <th>Bought Date</th>
              <th>Delivery Date</th>
              <th>Quantity</th>
              <th>Final Purchase Amount</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredPurchases.map((purchase) => (
              <tr key={purchase.purchaseId} className="table-row">
                <td>{purchase.purchaseId}</td>
                <td>{purchase.model.modelName}</td>
                <td>{new Date(purchase.bookedDate).toLocaleString()}</td>
                <td>{new Date(purchase.deliveryDate).toLocaleString()}</td>
                <td>{purchase.quantity}</td>
                <td>${purchase.finalPurchaseAmount.toLocaleString()}</td>
                <td>
                  <button
                    className="cancel-button"
                    onClick={() => handleCancel(purchase.purchaseId)}
                    disabled={!purchase.isVisible}
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
  
  export default Orders;

// function Orders() {
//     const [purchases, setPurchases] = useState([]);
//     const [filter, setFilter] = useState('bought');
  
//     const purchasesPopul = [
//       { purchase_id: 1, model_name: "Mahindra", bought_date: "2025-02-11T12:34:56.789", delivery_date: "2025-02-13T12:34:56.789", quantity: 3, final_purchase_amount: 798899, isVisible: true },
//       { purchase_id: 2, model_name: "Toyota", bought_date: "2025-03-01T10:20:30.123", delivery_date: "2025-03-05T11:22:33.456", quantity: 2, final_purchase_amount: 650000, isVisible: true },
//       { purchase_id: 3, model_name: "Honda", bought_date: "2025-04-10T08:15:45.789", delivery_date: "2025-04-15T09:18:22.345", quantity: 4, final_purchase_amount: 1200000, isVisible: false },
//       { purchase_id: 4, model_name: "Hyundai", bought_date: "2025-05-05T07:12:22.345", delivery_date: "2025-05-08T08:30:11.567", quantity: 1, final_purchase_amount: 500000, isVisible: true },
//       { purchase_id: 5, model_name: "Ford", bought_date: "2025-06-01T12:00:00.000", delivery_date: "2025-06-04T14:15:10.000", quantity: 5, final_purchase_amount: 950000, isVisible: false },
//       { purchase_id: 6, model_name: "Chevrolet", bought_date: "2025-07-20T09:45:12.345", delivery_date: "2025-07-25T10:55:30.789", quantity: 2, final_purchase_amount: 700000, isVisible: true },
//       { purchase_id: 7, model_name: "Nissan", bought_date: "2025-08-15T11:20:33.456", delivery_date: "2025-08-20T12:30:44.567", quantity: 3, final_purchase_amount: 850000, isVisible: true }
//     ];
  
//     useEffect(() => {
//         async function fetchPurchases() {
            
//             const jwt=localStorage.getItem('jwt');
//             const decodedToken = jwtDecode(jwt);
//             let userId=decodedToken.userId;
//             const api=`/user/${userId}/car-purchase-bookings`;
//         try {
//            const response = await axios.get(api,{headers: {
//             'Authorization': `Bearer ${jwt}`
//         }}); // Replace with your API endpoint
//         console.log("Purchase List:",response.data)
//           setPurchases(response.data);
//         } catch (error) {
//           console.error('Error fetching purchases:', error);
//         }
//       }
  
//       fetchPurchases();
//     }, []);
  
//     const handleCancel = (purchaseId) => {
//       setPurchases(prevPurchases =>
//         prevPurchases.map(purchase =>
//           purchase.purchaseId === purchaseId
//             ? { ...purchase, isVisible: false }
//             : purchase
//         )
//       );
//     };
  
//     const filteredPurchases = purchases.filter(purchase =>
//       filter === 'bought' ? purchase.isVisible : !purchase.isVisible
//     );
  
//     return (
//       <div className="container">
//         <div className="filter-options">
//           <label>
//             <input
//               type="radio"
//               value="bought"
//               checked={filter === 'bought'}
//               onChange={() => setFilter('bought')}
//             /> Bought
//           </label>
//           <label>
//             <input
//               type="radio"
//               value="cancelled"
//               checked={filter === 'cancelled'}
//               onChange={() => setFilter('cancelled')}
//             /> Cancelled
//           </label>
//         </div>
  
//         <table className="table">
//           <thead className="table-head">
//             <tr>
//               <th>Purchase ID</th>
//               <th>Model Name</th>
//               <th>Bought Date</th>
//               <th>Delivery Date</th>
//               <th>Quantity</th>
//               <th>Final Purchase Amount</th>
//               <th>Actions</th>
//             </tr>
//           </thead>
//           <tbody>
//             {filteredPurchases.map((purchase) => (
//               <tr key={purchase.purchaseId} className="table-row">
//                 <td>{purchase.purchaseId}</td>
//                 <td>{purchase.model.modelName}</td>
//                 <td>{new Date(purchase.bookedDate).toLocaleString()}</td>
//                 <td>{new Date(purchase.deliveryDate).toLocaleString()}</td>
//                 <td>{purchase.quantity}</td>
//                 <td>${purchase.finalPurchaseAmount.toLocaleString()}</td>
//                 <td>
//                   <button
//                     className="cancel-button"
//                     onClick={() => handleCancel(purchase.purchaseId)}
//                     disabled={filter === 'cancelled'}
//                   >
//                     Cancel
//                   </button>
//                 </td>
//               </tr>
//             ))}
//           </tbody>
//         </table>
//       </div>
//     );
//   }
  
//   export default Orders;
  
// function Orders() {
//     const [purchases, setPurchases] = useState([]);

//     const purchasesPopul = [
//         {
//           purchase_id: 1,
//           model_name: "Mahindra",
//           bought_date: "2025-02-11T12:34:56.789",
//           delivery_date: "2025-02-13T12:34:56.789",
//           quantity: 3,
//           final_purchase_amount: 798899,
//         },
//         {
//           purchase_id: 2,
//           model_name: "Toyota",
//           bought_date: "2025-03-01T10:20:30.123",
//           delivery_date: "2025-03-05T11:22:33.456",
//           quantity: 2,
//           final_purchase_amount: 650000,
//         },
//         {
//           purchase_id: 3,
//           model_name: "Honda",
//           bought_date: "2025-04-10T08:15:45.789",
//           delivery_date: "2025-04-15T09:18:22.345",
//           quantity: 4,
//           final_purchase_amount: 1200000,
//         },
//         {
//           purchase_id: 4,
//           model_name: "Hyundai",
//           bought_date: "2025-05-05T07:12:22.345",
//           delivery_date: "2025-05-08T08:30:11.567",
//           quantity: 1,
//           final_purchase_amount: 500000,
//         },
//         {
//           purchase_id: 5,
//           model_name: "Ford",
//           bought_date: "2025-06-01T12:00:00.000",
//           delivery_date: "2025-06-04T14:15:10.000",
//           quantity: 5,
//           final_purchase_amount: 950000,
//         },
//         {
//           purchase_id: 6,
//           model_name: "Chevrolet",
//           bought_date: "2025-07-20T09:45:12.345",
//           delivery_date: "2025-07-25T10:55:30.789",
//           quantity: 2,
//           final_purchase_amount: 700000,
//         },
//         {
//           purchase_id: 7,
//           model_name: "Nissan",
//           bought_date: "2025-08-15T11:20:33.456",
//           delivery_date: "2025-08-20T12:30:44.567",
//           quantity: 3,
//           final_purchase_amount: 850000,
//         }
//       ];
      
//       console.log(purchases);
      

//     useEffect(() => {
//       async function fetchPurchases() {
//         try {
//         //   const response = await axios.get('/api/purchases'); // Replace with your API endpoint
//         //   setPurchases(response.data);
//         setPurchases(purchasesPopul);
//         } catch (error) {
//           console.error('Error fetching purchases:', error);
//         }
//       }
  
//       fetchPurchases();
//     }, []);
  
//     const handleDelete = async (purchaseId) => {
//       try {
//         const response = await axios.delete(`/api/purchases/${purchaseId}`); // Replace with your DELETE endpoint
//         if (response.status === 200) {
//           setPurchases((prevPurchases) =>
//             prevPurchases.filter((purchase) => purchase.purchase_id !== purchaseId)
//           );
//         } else {
//           alert('Unsuccessful deletion.');
//         }
//       } catch (error) {
//         console.error(`Error deleting purchase with id ${purchaseId}:`, error);
//         alert('Unsuccessful deletion.');
//       }
//     };
  
//     return (
//       <div className="container">
//         <table className="table">
//           <thead className="table-head">
//             <tr>
//               <th className="table-header">Purchase ID</th>
//               <th className="table-header">Model Name</th>
//               <th className="table-header">Bought Date</th>
//               <th className="table-header">Delivery Date</th>
//               <th className="table-header">Quantity</th>
//               <th className="table-header">Final Purchase Amount</th>
//               <th className="table-header">Actions</th>
//             </tr>
//           </thead>
//           <tbody>
//             {purchases.map((purchase) => (
//               <tr key={purchase.purchase_id} className="table-row">
//                 <td className="table-data">{purchase.purchase_id}</td>
//                 <td className="table-data">{purchase.model_name}</td>
//                 <td className="table-data">{new Date(purchase.bought_date).toLocaleString()}</td>
//                 <td className="table-data">{new Date(purchase.delivery_date).toLocaleString()}</td>
//                 <td className="table-data">{purchase.quantity}</td>
//                 <td className="table-data">${purchase.final_purchase_amount.toLocaleString()}</td>
//                 <td className="table-data">
//                   <button className="delete-button" onClick={() => handleDelete(purchase.purchase_id)}>
//                     Delete
//                   </button>
//                 </td>
//               </tr>
//             ))}
//           </tbody>
//         </table>
//       </div>
//     );
//   }
  
// export default Orders