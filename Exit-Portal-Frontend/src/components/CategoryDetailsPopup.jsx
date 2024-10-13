import React from 'react';


const CategoryDetailsPopup = ({ category, onClose }) => {
  return (
    <div className="popup">
      <div className="popup-content">
        <h3>{category.name} - Course Details</h3>
        <ul>
          {category.courses.map((course, index) => (
            
            <li key={index}>
              {course.name} -{' '}
              <span style={{ color: course.completed ? 'green' : 'red' }}>
                {course.completed ? 'Completed' : 'Not Completed'}
              </span>
            </li>
          ))}
        </ul>
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default CategoryDetailsPopup;
