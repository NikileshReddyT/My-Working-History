import React from "react";
import "./Styles/categorylist.css";
const CategoryList = ({ categories, onShowPopup }) => {

  if (categories == null) {
    return <h1>No Data Found</h1>;
  }

  return (
    <div className='categories'>
      <h3 className="click-to-view-details">Click on the category to view details</h3>
      <div className="table-container">
        <table className='categories-table'>
          <thead>
            <tr>
              <th>Course Category</th>
              <th>Required Courses</th>
              <th>Completed Courses</th>
              <th>Required Credits</th>
              <th>Completed Credits</th>
              <th>Remaining Courses</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {categories.map((category, index) => {
              const pendingCourses = Math.max(0, category.minRequiredCourses - category.completedCourses);
              return (
                <tr key={index} onClick={() => onShowPopup(category, pendingCourses)}>
                  <td className="category-name">{category.categoryName}</td>
                  <td className='category-total-courses'>
                    {category.minRequiredCourses}
                  </td>
                  <td
                    className={`category-completed-courses ${
                      category.completedCourses < category.minRequiredCourses
                        ? "incomplete"
                        : ""
                    }`}
                  >
                    {category.completedCourses}
                  </td>
                  <td className='category-total-credits'>
                    {category.minRequiredCredits}
                  </td>
                  <td
                    className={`category-completed-credits ${
                      category.completedCredits < category.minRequiredCredits
                        ? "incomplete"
                        : ""
                    }`}
                  >
                    {category.completedCredits}
                  </td>
                  <td className='category-remaining-courses'>
                    {pendingCourses}
                  </td>
                  <td className={`status-cell ${
                    category.completedCourses >= category.minRequiredCourses &&
                    category.completedCredits >= category.minRequiredCredits
                      ? "completed"
                      : "incomplete-status"
                  }`}>
                    {category.completedCourses >= category.minRequiredCourses &&
                     category.completedCredits >= category.minRequiredCredits
                      ? "Completed"
                      : <abbr title="Click to view details">Incomplete</abbr>}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CategoryList;
