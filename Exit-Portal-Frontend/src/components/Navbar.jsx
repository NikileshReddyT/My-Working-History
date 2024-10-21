import React from "react";
import "./CustomNavbar.css"; // Import the CSS file

const CustomNavbar = ({ data }) => {
  if (!data) {
    console.error("Student data is not defined!");
    return null;
  }

  
  const { universityId, studentName } =
    data[0];
  // Use a dummy photo if none is provided
  const photo =  "https://randomuser.me/api/portraits/men/1.jpg"; // Placeholder image

  return (
    <nav className='custom-navbar'>
      <div className="student-details">
      <div className='photo-container'>
        <img src={photo} alt='Student' />
      </div>
      <div className='details-container'>
        <p>ID: {universityId || "Unknown ID"}</p>
        <p>{studentName || "Unknown Name"}</p>
      </div>
      </div>

      <div className='eligibility-container'>
        <p>
          <strong>Certificate Eligibility</strong>{" "}
          Not Eligible
        </p>
        <p>
          <strong>Specialization Eligibility:</strong>{" "}
          Not Completed
        </p>
      </div>
    </nav>
  );
};

export default CustomNavbar;
