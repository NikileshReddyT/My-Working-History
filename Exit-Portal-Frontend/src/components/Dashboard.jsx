import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import CategoryList from "./CategoryList";
import Summary from "./Summary";
import CategoryDetailsPopup from "./CategoryDetailsPopup";
import CustomNavbar from "./Navbar";
import axios from "axios";
import "./Styles/dashboard.css"; // New CSS file for animations and styling

const Dashboard = () => {
  const navigate = useNavigate();

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [popupVisible, setPopupVisible] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [pendingCourses, setPendingCourses] = useState(null);

  useEffect(() => {
    const storedStudentId = localStorage.getItem('studentId');
    const storedStudentData = localStorage.getItem('studentData');

    if (storedStudentId) {
      if (storedStudentData) {
        setData(JSON.parse(storedStudentData));
        console.log("Data from local storage:");
        setLoading(false);
      } else {
        axios.post('https://exit-portal-requirement-klu.vercel.app/api/v1/frontend/getdata', { universityid: storedStudentId })
          .then(response => {
            if (response.data) {
              setData(response.data);
              console.log("Data from backend:");
              localStorage.setItem('studentData', JSON.stringify(response.data));
            }
            setLoading(false);
          })
          .catch(() => {
            navigate('/');
          });
      }
    } else {
      navigate('/');
    }
  }, [navigate]);

  const handleShowPopup = (category, pendingCourses) => {
    setSelectedCategory(category);
    setPendingCourses(pendingCourses);
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
    );
  }

  // Check for no data, empty array, or null
  if (!data || (Array.isArray(data) && data.length === 0)) {
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
          pendingCourses={pendingCourses}
          onClose={handleClosePopup}
        />
      )}
    </div>
  );
};

export default Dashboard;
