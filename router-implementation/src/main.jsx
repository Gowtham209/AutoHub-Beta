import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { createBrowserRouter, createRoutesFromElements,Route,RouterProvider,Navigate  } from 'react-router-dom'
import Layout from './components/layout/Layout.jsx'
import Home from './components/home/Home.jsx'
import UserHome from './components/user/home/UserHome.jsx'
import About from './components/about/About.jsx'
import Login from './components/login/Login.jsx'
import SignUp from './components/signup/SignUp.jsx'
import UserLayout from './components/user/UserLayout.jsx'
import AdminLayout from './components/admin/AdminLayout.jsx'
import Profile from './components/admin/profile/Profile.jsx'
import UnAuthorized from './components/error/UnAuthorized.jsx'
import NotFound from './components/error/NotFound.jsx'
import CarDetail from './components/car-detail/CarDetail.jsx'
import TestDriveBooking from './components/test-drive-book/TestDriveBooking.jsx'
import BuyPage from './components/buy/BuyPage.jsx'
//import DashBoard from './components/admin/dash-board/DashBoard.jsx'
import AdminDashBoard from './components/admin-dash-board/AdminDashBoard.jsx'
import AddCar from './components/admin/add-car/AddCarModel.jsx'
import Address from './components/address/Address.jsx'
import Orders from './components/user/orders/Orders.jsx'
import TestDriveBookingsList from './components/user/test-drive-booking-list/TestDriveBookingsList.jsx'
const routerObj = createBrowserRouter(
  createRoutesFromElements(
  <Route>

    {/* The Below Route Design is Used to Set a Default Route to Show of the Parent Route 
    AND If U want the URL path to SHOW in Browser of the Default Route this Approach can be used 
    */}
    {/* <Route path='/' element={<Layout/>}>
    <Route index element={<Navigate to='/home' />} />
      <Route path='home' element={<Home/>}/>
      <Route path='about' element={<About/>} />
    </Route> */}

    {/* The Below Route Design is Used to Set a Default Route to Show of the Parent Route 
    AND If U DONT want the URL path to SHOW in Browser of the Default Route this Approach can be used 
    */}
    <Route path='/' element={<Layout />}>
        <Route index element={<Home />} />
        <Route path='home' element={<Home />} />
        <Route path='about' element={<About />} />
        <Route path='login' element={<Login/>}/>
        <Route path='signup' element={<SignUp/>}/>
        <Route path='test-drive/:carModelId' element={<TestDriveBooking/>}/>
        <Route path='buy/:carModelId' element={<BuyPage/>}/>
        <Route path='cardetail/:carModelId' element={<CarDetail/>} />
      </Route>

    <Route path='/admin' element={<AdminLayout/>}>
    <Route index element={<Navigate to='/dash-board' />} />
    <Route path='dash-board' element={<AdminDashBoard/>} />
    <Route path='add-car' element={<AddCar/>} />
    <Route path='profile' element={<Profile/>} />
    <Route path='address' element={<Address/>} />

    </Route>

    <Route path='/user' element={<UserLayout/>}>
    <Route path='test-drive/:carModelId' element={<TestDriveBooking/>}/>
    <Route path='buy/:carModelId' element={<BuyPage/>}/>
    <Route path='test-drive-bookings' element={<TestDriveBookingsList/>} />
    <Route path='orders' element={<Orders/>} />
    <Route path='userhome' element={<UserHome/>} />
    <Route path='profile' element={<Profile/>} />
    <Route path='address' element={<Address/>} />
    </Route>

     {/* Global 404 Route */}
     <Route path="*" element={<NotFound />} />
     {/* Not - UNAUTHORIZED */}
      <Route path="unAuthorized" element={<UnAuthorized/>}/>
</Route>
  )
)

createRoot(document.getElementById('app')).render(
  <StrictMode>
    <RouterProvider router={routerObj}/>
  </StrictMode>,
)
