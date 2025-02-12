import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react';
import Tile from '../../test-tile/Tile';
import axios from '../../utility/axios';
import authorize from '../../utility/Authorizer';
import userIdExtract from '../../utility/userIdExtractor';
import roleExtract from '../../utility/roleExtractor'
import  { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

function AddCategory() {
  const [categoryList, setCategoryList] = useState([]);
  const [carType, setCarType] = useState('');
  const [editingCategory, setEditingCategory] = useState(null);
  const [viewMode, setViewMode] = useState('current'); // 'current' or 'discontinued'

  const categoryListFetcher = async () => {
    try {
      const resp = await axios.get('/categories');
      setCategoryList(resp.data);
    } catch (error) {
      console.log(error);
    }
  };

  const saveCategory = async () => {
    const jwt = localStorage.getItem('jwt');
    const api = `/admin/category`;
    const payload = { "carType": carType, "isVisible": true };

    try {
      const response = await axios.post(api, payload, {
        headers: { Authorization: `Bearer ${jwt}` },
      });
      console.log('Successfully:', response.data);
      setCategoryList((prev) => [...prev, response.data]);
      setCarType('');
    } catch (error) {
      console.log('Error:', error);
    }
  };

  const deleteCategory = async (categoryId) => {
    const jwt = localStorage.getItem('jwt');
    try {
      await axios.delete(`/admin/category/${categoryId}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      });
      setCategoryList((prev) =>
        prev.map((cat) =>
          cat.categoryId === categoryId ? { ...cat, isVisible: false } : cat
        )
      );
    } catch (error) {
      console.log('Error deleting category:', error);
    }
  };

  const updateCategory = async () => {
    const jwt = localStorage.getItem('jwt');
    try {
      const response = await axios.put(
        `/admin/category/${editingCategory.categoryId}`,
        { carType: editingCategory.carType },
        {
          headers: { Authorization: `Bearer ${jwt}` },
        }
      );
      console.log('Successfully updated:', response.data);
      setCategoryList((prev) =>
        prev.map((cat) => (cat.categoryId === editingCategory.categoryId ? response.data : cat))
      );
      setEditingCategory(null);
    } catch (error) {
      console.log('Error updating category:', error);
    }
  };

  useEffect(() => {
    categoryListFetcher();
  }, []);

  const filteredCategories = categoryList.filter(
    (category) => (viewMode === 'current' ? category.isVisible : !category.isVisible)
  );

  return (
    <div className="add-category-container">
      <h1>Add New Category</h1>
      <div className="adding-new-category">
        <label htmlFor="category">New Category:</label>
        <input
          id="category"
          type="text"
          value={carType}
          onChange={(e) => setCarType(e.target.value)}
        />
        <button onClick={saveCategory}>Save</button>
      </div>

      <div className="view-mode-controls">
        <label>
          <input
            type="radio"
            name="viewMode"
            checked={viewMode === 'current'}
            onChange={() => setViewMode('current')}
          />{' '}
          Current Live Categories
        </label>
        <label>
          <input
            type="radio"
            name="viewMode"
            checked={viewMode === 'discontinued'}
            onChange={() => setViewMode('discontinued')}
          />{' '}
          Discontinued Categories
        </label>
      </div>

      <h2>Categories</h2>
      <table className="category-table">
        <thead>
          <tr>
            <th>Category ID</th>
            <th>Car Type</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredCategories.map((category) => (
            <tr key={category.categoryId}>
              <td>{category.categoryId}</td>
              <td>{category.carType}</td>
              <td>
                <button onClick={() => setEditingCategory(category)}>Update</button>
                {viewMode === 'current' && (
                  <button onClick={() => deleteCategory(category.categoryId)}>Delete</button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {editingCategory && (
        <div className="popup">
          <h2>Update Category</h2>
          <label>Car Type:</label>
          <input
            type="text"
            value={editingCategory.carType}
            onChange={(e) => setEditingCategory({ ...editingCategory, carType: e.target.value })}
          />
          <div className="popup-buttons">
            <button onClick={updateCategory}>Save</button>
            <button onClick={() => setEditingCategory(null)}>Cancel</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default AddCategory;


// function AddCategory() {
//   const [categoryList, setCategoryList] = useState([]);
//   const [carType, setCarType] = useState('');
//   const [editingCategory, setEditingCategory] = useState(null);

//   const categoryListFetcher = async () => {
//     try {
//       const resp = await axios.get('/categories');
//       setCategoryList(resp.data);
//     } catch (error) {
//       console.log(error);
//     }
//   };

//   const saveCategory = async () => {
//     const jwt = localStorage.getItem('jwt');
//     const api = `/admin/category`;
//     const payload = { "carType":carType,"isVisible":true };

//     try {
//       const response = await axios.post(api, payload, {
//         headers: { Authorization: `Bearer ${jwt}` },
//       });
//       console.log('Successfully:', response.data);
//       setCategoryList((prev) => [...prev, response.data]);
//       setCarType('');
//     } catch (error) {
//       console.log('Error:', error);
//     }
//   };

//   const deleteCategory = async (categoryId) => {
//     const jwt = localStorage.getItem('jwt');
//     try {
//       await axios.delete(`/admin/category/${categoryId}`, {
//         headers: { Authorization: `Bearer ${jwt}` },
//       });
//       setCategoryList((prev) => prev.filter((cat) => cat.categoryId !== categoryId));
//     } catch (error) {
//       console.log('Error deleting category:', error);
//     }
//   };

//   const updateCategory = async () => {
//     const jwt = localStorage.getItem('jwt');
//     try {
//       const response = await axios.put(
//         `/admin/category/${editingCategory.categoryId}`,
//         { carType: editingCategory.carType },
//         {
//           headers: { Authorization: `Bearer ${jwt}` },
//         }
//       );
//       console.log('Successfully updated:', response.data);
//       setCategoryList((prev) =>
//         prev.map((cat) => (cat.categoryId === editingCategory.categoryId ? response.data : cat))
//       );
//       setEditingCategory(null);
//     } catch (error) {
//       console.log('Error updating category:', error);
//     }
//   };

//   useEffect(() => {
//     categoryListFetcher();
//   }, []);

//   return (
//     <div className="add-category-container">
//       <h1>Add New Category</h1>
//       <div className="adding-new-category">
//         <label htmlFor="category">New Category:</label>
//         <input
//           id="category"
//           type="text"
//           value={carType}
//           onChange={(e) => setCarType(e.target.value)}
//         />
//         <button onClick={saveCategory}>Save</button>
//       </div>

//       <h2>Existing Categories</h2>
//       <table className="category-table">
//         <thead>
//           <tr>
//             <th>Category ID</th>
//             <th>Car Type</th>
//             <th>Actions</th>
//           </tr>
//         </thead>
//         <tbody>
//           {categoryList.map((category) => (
//             <tr key={category.categoryId}>
//               <td>{category.categoryId}</td>
//               <td>{category.carType}</td>
//               <td>
//                 <button onClick={() => setEditingCategory(category)}>Update</button>
//                 <button onClick={() => deleteCategory(category.categoryId)}>Delete</button>
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </table>

//       {editingCategory && (
//         <div className="popup">
//           <h2>Update Category</h2>
//           <label>Car Type:</label>
//           <input
//             type="text"
//             value={editingCategory.carType}
//             onChange={(e) => setEditingCategory({ ...editingCategory, carType: e.target.value })}
//           />
//           <div className="popup-buttons">
//             <button onClick={updateCategory}>Save</button>
//             <button onClick={() => setEditingCategory(null)}>Cancel</button>
//           </div>
//         </div>
//       )}
//     </div>
//   );
// }

// export default AddCategory;


// function AddCategory() {
//   const [categoryList, setCategoryList] = useState([]);
//   const [carType, setCarType] = useState('');

//   const categoryListFetcher = async () => {
//     try {
//       const resp = await axios.get("/categories");
//       setCategoryList(resp.data);
//     } catch (error) {
//       console.log(error);
//     }
//   };

//   async function saveCategory() {
//     try {
//       const jwt = localStorage.getItem('jwt');
//       const api = `/admin/category`;
//       const payload = { carType };
//       const response = await axios.post(api, payload, {
//         headers: { 'Authorization': `Bearer ${jwt}` }
//       });
//       console.log("Successfully added:", response.data);
//       setCategoryList(prev => [...prev, response.data]); // Add the new category to the list
//       setCarType(''); // Clear the input field
//     } catch (error) {
//       console.log("Error:", error.response?.data || error);
//     }
//   }

//   useEffect(() => {
//     categoryListFetcher();
//   }, []);

//   return (
//     <div className="add-category-container">
//       <h1>Add New Category</h1>
//       <div className="adding-new-category">
//         <label htmlFor="category">New Category:</label>
//         <input
//           id='category'
//           type="text"
//           value={carType}
//           onChange={(e) => setCarType(e.target.value)}
//           placeholder="Enter category name"
//         />
//         <button onClick={saveCategory}>Save</button>
//       </div>

//       <h2>Existing Categories</h2>
//       <table className="category-table">
//         <thead>
//           <tr>
//             <th>Category ID</th>
//             <th>Car Type</th>
//           </tr>
//         </thead>
//         <tbody>
//           {categoryList.map((obj) => (
//             <tr key={obj.categoryId}>
//               <td>{obj.categoryId}</td>
//               <td>{obj.carType}</td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//     </div>
//   );
// }

// export default AddCategory;

// function AddCategory() {
//   const [categoryList, setCategoryList] = useState([]);

//   const [carType,setCarType]=useState('');
//   const categoryListFetcher = async () => {
//     try {
//       const resp = await axios.get("/categories");
//       setCategoryList(resp.data);
//     } catch (error) {
//       console.log(error);
//     }
//   };

//   async function saveCateogry()
//   {
//       const jwt = localStorage.getItem('jwt');
//       const api=`/admin/category`;
//       const payload={"carType":carType};
//       const response=await axios.post(api, payload,{
//         headers: { 'Authorization': `Bearer ${jwt}` }
//       }).then((response)=>{

//         console.log("Successfully,",response.data)
//       })
//       .catch((error)=>{console.log("Error:",error.data)});
//   }

//     useEffect(() => {
    
//       categoryListFetcher();
//     }, []);
//   return (
//     <>
//     <div>AddCategory</div>
//     <div className="adding-new-category">
//      <label htmlFor="category">New Category:</label>
//       <input 
//       id='category'
//       type="text" 
//       value={carType}
//       onChange={(e)=>setCarType(e.target.value)}
//       />
//       <div className="button-to-save">
//         <button onClick={saveCateogry}>Save</button>
//       </div>
//     </div>
//     <h1>Already Present Categories</h1>
//     {categoryList.map((obj)=>(<td>obj.carType</td>))}

//     </>
//   )
// }

// export default AddCategory