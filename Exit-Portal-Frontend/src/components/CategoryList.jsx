import React from "react";
import "./Styles/categorylist.css";
const CategoryList = ({ categories, onShowPopup }) => {
  console.log(categories);

  if (categories == null) {
    return <h1>No Data Found</h1>;
  }

  return (
    <div className='categories'>
      
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
            {categories.map((category, index) => (
              <tr key={index} onClick={() => onShowPopup(category)}>
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
                  {Math.max(0, category.minRequiredCourses - category.completedCourses)}
                </td>
                <td className={`status-cell ${
                  category.completedCourses >= category.minRequiredCourses &&
                  category.completedCredits >= category.minRequiredCredits
                    ? "completed"
                    : "incomplete"
                }`}>
                  {category.completedCourses >= category.minRequiredCourses &&
                   category.completedCredits >= category.minRequiredCredits
                    ? "Completed"
                    : <abbr title="Click to view details">Incomplete</abbr>}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CategoryList;
