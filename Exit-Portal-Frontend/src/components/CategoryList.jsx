import React from "react";

const CategoryList = ({ categories, onShowPopup }) => {
  console.log(categories);

  if (categories == null) {
    return <h1>No Data Found</h1>;
  }

  return (
    <div className='categories'>
      <h4>Categories</h4>
      <ul>
        {categories.map((category, index) => (
          <li key={index} onClick={() => onShowPopup(category)}>
            <span className='category-name'>{category.categoryName}</span>
            <div className='category-courses'>
              <span className='category-total-courses'>
                Total Courses : {category.minRequiredCourses}{" "}
              </span>
              <span className='category-completed-courses'>
                Completed Courses : {category.completedCourses}{" "}
              </span>
            </div>
            <div className='category-credits'>
              <span className='category-total-credits'>
                Required Credits : {category.minRequiredCredits}{" "}
              </span>
              <span className='category-completed-courses'>
                Completed Credits : {category.completedCredits}{" "}
              </span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CategoryList;
