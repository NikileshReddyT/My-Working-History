import React from "react";
import "./CustomNavbar.css"; // Import the CSS file

const CustomNavbar = ({ data }) => {
  if (!data) {
    console.error("Student data is not defined!");
    return null;
  }

  const { id, photoUrl, certificateEligible, specializationCompleted, name } =
    data;
  // Use a dummy photo if none is provided
  const photo = photoUrl || "https://randomuser.me/api/portraits/men/1.jpg"; // Placeholder image

  return (
    <nav className='custom-navbar'>
      <div className="student-details">
      <div className='photo-container'>
        <img src={photo} alt='Student' />
      </div>
      <div className='details-container'>
        <p>ID: {id || "Unknown ID"}</p>
        <p>{name || "Unknown Name"}</p>
      </div>
      </div>

      <div className='eligibility-container'>
        <p>
          <strong>Certificate Eligibility</strong>{" "}
          {certificateEligible ? "Eligible" : "Not Eligible"}
        </p>
        <p>
          <strong>Specialization Eligibility:</strong>{" "}
          {specializationCompleted ? "Completed" : "Not Completed"}
        </p>
      </div>
    </nav>
  );
};

export default CustomNavbar;
