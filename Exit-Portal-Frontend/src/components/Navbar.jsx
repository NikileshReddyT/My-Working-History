import React from "react";
import "./Styles/CustomNavbar.css"; // Import the CSS file
import { useNavigate } from "react-router-dom";

const CustomNavbar = ({ data }) => {
  const navigate = useNavigate();

  if (!data) {
    console.error("Student data is not defined!");
    return null;
  }
  console.log(data);

  const { universityId, studentName } = data[0];
  // Use a dummy photo if none is provided
  const photo = "https://randomuser.me/api/portraits/men/1.jpg"; // Placeholder image

  const handleLogout = () => {
    // Clear all data from local storage
    localStorage.clear();
    // Navigate to the root path
    navigate('/');
  };

  // Determine Certificate Eligibility
  const isCertificateEligible = data.every(category => 
    category.completedCourses >= category.minRequiredCourses
  );

  // Determine Specialization Eligibility
  const isSpecializationCompleted = data.slice(5, 10).every(category => 
    category.completedCourses >= category.minRequiredCourses
  );

  return (
    <nav className='custom-navbar'>
      <div className='student-details'>
        <div className='photo-container'>
          <img src={photo} alt='Student' />
        </div>
        <div className='details-container'>
          <p>ID: {universityId || "Unknown ID"}</p>
          <p>{studentName || "Unknown Name"}</p>
        </div>
      </div>
{/* <h2 className="portal-title">Exit Requirement Portal</h2> */}
      <div className='eligibility-container'>
        <p>
          <strong>Certificate Eligibility:</strong> {isCertificateEligible ? "Eligible" : "Not Eligible"}
        </p>
        <p>
          <strong>Specialization Status:</strong> {isSpecializationCompleted ? "Completed" : "Not Completed"}
        </p>
      </div>
          <button className="logout-button" onClick={handleLogout}>Logout</button>
    </nav>
  );
};

export default CustomNavbar;
