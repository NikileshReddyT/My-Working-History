import React from "react";
import "./Styles/dashboard.css";
import PdfDownloadButton from "./PdfDownloadButton";
const Summary = ({ StudentId }) => {
  // console.log(data);
  return (

    <div className='summary'>
      <h3>Download Report</h3>
        <PdfDownloadButton studentId={StudentId} />
    </div>
  );
};

export default Summary;
