import React from 'react';

const CategoryList = ({ categories, onShowPopup }) => {
  const calculateCompletedCredits = (category) => {
    return category.courses.reduce((total, course) => {
      return course.completed ? total + (category.totalCredits / category.minCourses) : total;
    }, 0).toFixed(2);
  };

  return (
    <div className="categories">
      <h4>Categories</h4>
      <ul>
        {categories.map((category, index) => (
          <li key={index} onClick={() => onShowPopup(category)}>
            <span className='category-name'>{category.name}</span>
            <div className="category-credits">
              <span className='category-total-credits'>{category.totalCredits} Credits</span>
              <span className='category-completed-courses'>{calculateCompletedCredits(category)} Completed Credits</span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CategoryList;
