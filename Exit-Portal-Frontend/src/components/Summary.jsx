import React from "react";

const Summary = ({ data, StudentId }) => {
  // console.log(data);
  return (
    <div className='summary'>
      <h3>Summary</h3>
      <h3>
        <a href='#'>Download PDF</a>
      </h3>
    </div>
  );
};

export default Summary;
