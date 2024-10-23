import React from "react";
import "./Styles/categorylist.css";
const CategoryList = ({ categories, onShowPopup }) => {
  console.log(categories);

  if (categories == null) {
    return <h1>No Data Found</h1>;
  }

  return (
    <div className='categories'>
      <table className='categories-table'>
        <thead>
          <tr>
            <th>Course Category</th>
            <th>Required Courses</th>
            <th>Completed Courses</th>
            <th>Required Credits</th>
            <th>Completed Credits</th>
          </tr>
        </thead>
        <tbody>
          {categories.map((category, index) => (
            <tr key={index} onClick={() => onShowPopup(category)}>
              <td
 
              >
                {category.categoryName}
                {category.completedCourses < category.minRequiredCourses ||
                 category.completedCredits < category.minRequiredCredits ? (
                  <span className="category-name incomplete">(Incomplete)</span>
                ) : null}
              </td>
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
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CategoryList;
