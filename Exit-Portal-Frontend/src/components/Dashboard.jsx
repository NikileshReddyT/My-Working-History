import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import CategoryList from "./CategoryList";
import Summary from "./Summary";
import CategoryDetailsPopup from "./CategoryDetailsPopup";
import CustomNavbar from "./Navbar";
import axios from "axios";
import "./Styles/Dashboard.css"; // New CSS file for animations and styling

const Dashboard = () => {
  const location = useLocation();
  const { studentId } = location.state || {};

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true); // New loading state
  const [popupVisible, setPopupVisible] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);

  useEffect(() => {
    if (studentId) {
      axios
        .post("http://localhost:8080/api/v1/frontend/getdata", {
          universityid: studentId,
        })
        .then((res) => {
          setData(res.data);
          setLoading(false); // Set loading to false once data is fetched
        })
        .catch((err) => {
          console.error(err);
          setLoading(false); // Stop loading on error
        });
    }
  }, [studentId]);

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
      {" "}
      {/* Adding animation class */}
      <h2>Student Dashboard</h2>
      <CustomNavbar data={data} />
      <Summary data={data} StudentId={studentId} />
      <CategoryList categories={data} onShowPopup={handleShowPopup} />
      {popupVisible && (
        <CategoryDetailsPopup
          categoryName={selectedCategory.categoryName}
          studentId={studentId}
          onClose={handleClosePopup}
        />
      )}
    </div>
  );
};

export default Dashboard;
