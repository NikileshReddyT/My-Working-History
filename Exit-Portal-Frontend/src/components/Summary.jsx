import React from "react";
import "./Styles/dashboard.css";
const Summary = ({ data, StudentId }) => {
  // console.log(data);
  return (
    <div className='summary'>
      <h3><a href='#'>Summary</a></h3>
      <h3>
        <a href='#'>Download PDF</a>
      </h3>
    </div>
  );
};

export default Summary;
