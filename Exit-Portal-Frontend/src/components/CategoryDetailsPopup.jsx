import axios from "axios";
import React, { useEffect, useState } from "react";
import './Styles/popupdetails.css';

const CategoryDetailsPopup = ({ categoryName, studentId, onClose }) => {
  const [category, setCategory] = useState(null);

  useEffect(() => {
    const encodedCategoryName = encodeURIComponent(categoryName);
    axios
      .get(
        `http://localhost:8080/api/v1/frontend/getcategorydetails/${encodedCategoryName}/${studentId}`
      )
      .then((response) => {
        setCategory(response.data);
        console.log(response.data);
      });
  }, [categoryName, studentId]);

  return (
    <div className="popup-overlay" onClick={onClose}>
      <div className="popup" onClick={(e) => e.stopPropagation()}>
        <div className="popup-content">
          <button className="close-button" onClick={onClose}>X</button>
          <h3>{categoryName} - Course Details</h3>
          {category ? (
            <table className="course-table">
              <thead>
                <tr>
                  <th>Course Name</th>
                  <th>Grade</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {category.map((course, index) => (
                  <tr key={index}>
                    <td>{course.courseName}</td>
                    <td>{course.grade}</td>
                    <td style={{ color: course.promotion.toLowerCase() === 'p' ? 'green' : 'red' }}>
                      {course.promotion.toLowerCase() === 'p' ? 'Completed' : 'Not Completed'}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p>Loading...</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default CategoryDetailsPopup;
