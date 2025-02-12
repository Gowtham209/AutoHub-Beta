import React from 'react'
import { useNavigate } from 'react-router-dom';
import './AdminDashBoard.css'

import { useState } from 'react';

import AddCategory from '../admin/add-category/AddCategory.jsx'
import AddCarModel from '../admin/add-car/AddCarModel.jsx';
import ListCustomers from '../admin/list-customers/ListCustomers.jsx';
import ListOrders from '../admin/list-orders/ListOrders.jsx';
import ListTestDrives from '../admin/list-test-drives-booking/ListTestDrives.jsx';
import DashBoard from '../admin/list-cars/DashBoard.jsx';

function AdminDashboard() {
  const [activeSection, setActiveSection] = useState('dashboard');

  const renderContent = () => {
    switch (activeSection) {
      case 'addCategory':
        return <AddCategory />;
      case 'addCarModel':
        return <AddCarModel />;
      case 'listCustomers':
        return <ListCustomers />;
      case 'listOrders':
        return <ListOrders />;
      case 'listTestDrives':
        return <ListTestDrives />;
      default:
        return <DashBoard/>;
    }
  };

  return (
    <div className="admin-dashboard">
      <nav className="sidebar">
        <h2>Admin Panel</h2>
        <ul>
          <li onClick={() => setActiveSection('dashboard')}>Dashboard</li>
          <li onClick={() => setActiveSection('addCategory')}>Add Category</li>
          <li onClick={() => setActiveSection('addCarModel')}>Add Car Model</li>
          <li onClick={() => setActiveSection('listCustomers')}>List Customers</li>
          <li onClick={() => setActiveSection('listOrders')}>View Orders</li>
          <li onClick={() => setActiveSection('listTestDrives')}>Test Drive Bookings</li>
        </ul>
      </nav>

      <main className="content">
        {renderContent()}
      </main>
    </div>
  );
}

export default AdminDashboard;