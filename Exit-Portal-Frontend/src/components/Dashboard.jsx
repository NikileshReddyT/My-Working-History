import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import CategoryList from "./CategoryList";
import Summary from "./Summary";
import CategoryDetailsPopup from "./CategoryDetailsPopup";
import CustomNavbar from "./Navbar";
import axios from "axios";
import "./Styles/Dashboard.css"; // New CSS file for animations and styling

const Dashboard = () => {
  const navigate = useNavigate();

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true); // New loading state
  const [popupVisible, setPopupVisible] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);

  useEffect(() => {
    // Retrieve student ID from local storage
    const storedStudentId = localStorage.getItem('studentId');
    const storedStudentData = localStorage.getItem('studentData');

    if (storedStudentId) {
      if (storedStudentData) {
        // Use existing data if available
        setData(JSON.parse(storedStudentData));
        setLoading(false);
      } else {
        // Fetch data from the backend using the student ID
        axios.post('http://localhost:8080/api/v1/frontend/getdata', { universityid: storedStudentId })
          .then(response => {
            if (response.data) {
              setData(response.data);
              localStorage.setItem('studentData', JSON.stringify(response.data));
              setLoading(false); // Set loading to false once data is fetched
            }
          })
          .catch(() => {
            navigate('/');
          });
      }
    } else {
      navigate('/');
    }
  }, [navigate]);

  const handleShowPopup = (category) => {
    setSelectedCategory(category);
    console.log(category);
    setPopupVisible(true);
  };

  const handleClosePopup = () => {
    setPopupVisible(false);
    setSelectedCategory(null);
  };

  if (loading) {
    return (
      <>
        <div className='loading-spinner'>{}</div>
        Loading...
      </>
    ); // Show loading spinner
  }

  if (!data) {
    return <h1>No Data Found</h1>;
  }

  return (
    <div className='dashboard animate-fade-in'>
      <h2>Student Dashboard</h2>
      <CustomNavbar data={data} />
      <Summary data={data} StudentId={localStorage.getItem('studentId')} />
      <CategoryList categories={data} onShowPopup={handleShowPopup} />
      {popupVisible && (
        <CategoryDetailsPopup
          categoryName={selectedCategory.categoryName}
          studentId={localStorage.getItem('studentId')}
          onClose={handleClosePopup}
        />
      )}
    </div>
  );
};

export default Dashboard;
