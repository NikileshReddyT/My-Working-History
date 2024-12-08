import axios from "axios";
import React, { useEffect, useState } from "react";
import './Styles/popupdetails.css';

const CategoryDetailsPopup = ({ categoryName, studentId, pendingCourses, onClose }) => {
  const [category, setCategory] = useState([]);
  const [allCourses, setAllCourses] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    const encodedCategoryName = encodeURIComponent(categoryName);
    
    Promise.all([
      axios.get(
        `https://keen-radiance-production.up.railway.app/api/v1/frontend/getcategorydetails/${encodedCategoryName}/${studentId}`
      ),
      axios.get(
        `https://keen-radiance-production.up.railway.app/api/v1/frontend/getallcourses/${encodedCategoryName}`
      )
    ]).then(([categoryResponse, coursesResponse]) => {
      const sortedData = (categoryResponse.data || []).sort((a, b) => {
        if (a.year !== b.year) {
          return a.year.localeCompare(b.year);
        }
        return a.semester === "Odd Sem" ? -1 : 1;
      });
      setCategory(sortedData);
      setAllCourses(coursesResponse.data || []);
    }).finally(() => {
      setLoading(false);
    });
  }, [categoryName, studentId]);

  const filterAvailableCourses = () => {
    const completedCourseCodes = category.map(course => course.courseCode);
    const completedCourseTitles = category.map(course => course.courseName);
    return allCourses.filter(course => !completedCourseCodes.includes(course.courseCode) && !completedCourseTitles.includes(course.courseTitle));
  };

  const availableCourses = filterAvailableCourses();

  return (
    <div className="popup-overlay" onClick={onClose}>
      <div className="popup" onClick={(e) => e.stopPropagation()}>
        <div className="popup-content">
          <button className="close-button" onClick={onClose}>X</button>
          <h3>{categoryName} - Course Details</h3>
          {loading ? (
            <p>Loading...</p>
          ) : (
            <>
              {pendingCourses > 0 && (
                <p>You need to complete {pendingCourses} more courses in this category.</p>
              )}
              {category.length > 0 ? (
                <table className="course-table">
                  <thead>
                    <tr>
                      <th>Year</th>
                      <th>Semester</th>
                      <th>Course Code</th>
                      <th>Course Name</th>
                      <th>Grade</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {category.map((course, index) => (
                      <tr key={index}>
                        <td>{course.year}</td>
                        <td>{course.semester}</td>
                        <td>{course.courseCode}</td>
                        <td>{course.courseName}</td>
                        <td>{course.grade}</td>
                        <td style={{ color: course.promotion.toLowerCase() === 'p' ? 'green' : (course.promotion.toLowerCase() === 'registered' ? 'orange' : (course.promotion.toLowerCase() === 'dt' ? 'red': 'red')) }}>
                          {course.promotion.toLowerCase() === 'p' ? 'Completed' : (course.promotion.toLowerCase() === 'registered' ? 'Result To be declared' : (course.promotion.toLowerCase() === 'dt' ? 'Re-Register': 'Not Completed'))}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              ) : (
                <div className="empty-state-message">
                  <h4>No Courses Completed</h4>
                  <p>You haven't completed any courses in this category yet.</p>
                  <p>Required: {pendingCourses} course{pendingCourses > 1 ? 's' : ''} to complete</p>
                </div>
              )}
              {pendingCourses > 0 && availableCourses.length > 0 && (
                <>
                  <h4>Other Courses Available for Registration</h4>
                  <p>You have {pendingCourses} pending courses. Select from the list below to complete them:</p>
                  <table className="course-table">
                    <thead>
                      <tr>
                        <th>Course Code</th>
                        <th>Course Name</th>
                        <th>Course Credits</th>
                      </tr>
                    </thead>
                    <tbody>
                      {availableCourses.map((course, index) => (
                        <tr key={index}>
                          <td>{course.courseCode}</td>
                          <td>{course.courseTitle}</td>
                          <td>{course.courseCredits}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </>
              )}
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default CategoryDetailsPopup;
