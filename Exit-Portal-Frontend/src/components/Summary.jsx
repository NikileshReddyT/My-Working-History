import React from 'react';

const Summary = ({ data , StudentId}) => {
  return (
    <div className="summary">
      <h3>Summary</h3>
      <p>Name: {data.name}</p>
      <p>Student ID: {StudentId}</p>
      <p>
        Certificate Eligible: {data.certificateEligible ? 'Yes' : 'No'}
      </p>
      <p>
        Specialization Completed: {data.specializationCompleted ? 'Yes' : 'No'}
      </p>
      <p>Total Categories: {data.categories.length}</p>
      <p>
        Total Earned Credits: {data.categories.reduce((acc, cat) => acc + cat.earnedCredits, 0)} Credits
      </p>
    </div>
  );
};

export default Summary;
