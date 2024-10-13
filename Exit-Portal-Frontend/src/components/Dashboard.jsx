import React, { useState } from 'react';
import CategoryList from './CategoryList';
import Summary from './Summary';
import CategoryDetailsPopup from './CategoryDetailsPopup';

const dummyData = {
  name: 'John Doe',
  certificateEligible: false,
  specializationCompleted: false,
  categories: [
    {
      name: 'Humanities & Social Sciences (HSS)',
      minCourses: 6,
      totalCredits: 12,
      earnedCredits: 10,
      courses: [
        { name: 'HSS Course 1', completed: true },
        { name: 'HSS Course 2', completed: false },
        { name: 'HSS Course 3', completed: true },
        { name: 'HSS Course 4', completed: true },
        { name: 'HSS Course 5', completed: true },
        { name: 'HSS Course 6', completed: false },
      ],
    },
    {
      name: 'Basic Sciences (BS)',
      minCourses: 7,
      totalCredits: 20.5,
      earnedCredits: 20.5,
      courses: [
        { name: 'BS Course 1', completed: true },
        { name: 'BS Course 2', completed: true },
        { name: 'BS Course 3', completed: true },
        { name: 'BS Course 4', completed: true },
        { name: 'BS Course 5', completed: true },
        { name: 'BS Course 6', completed: true },
        { name: 'BS Course 7', completed: true },
      ],
    },
    {
      name: 'Engineering Sciences (ES)',
      minCourses: 7,
      totalCredits: 25.5,
      earnedCredits: 24.5,
      courses: [
        { name: 'ES Course 1', completed: true },
        { name: 'ES Course 2', completed: true },
        { name: 'ES Course 3', completed: true },
        { name: 'ES Course 4', completed: false },
        { name: 'ES Course 5', completed: true },
        { name: 'ES Course 6', completed: true },
        { name: 'ES Course 7', completed: true },
      ],
    },
    {
      name: 'Professional Core (PC)',
      minCourses: 10,
      totalCredits: 34,
      earnedCredits: 30,
      courses: [
        { name: 'PC Course 1', completed: true },
        { name: 'PC Course 2', completed: true },
        { name: 'PC Course 3', completed: true },
        { name: 'PC Course 4', completed: false },
        { name: 'PC Course 5', completed: true },
        { name: 'PC Course 6', completed: false },
        { name: 'PC Course 7', completed: true },
        { name: 'PC Course 8', completed: true },
        { name: 'PC Course 9', completed: true },
        { name: 'PC Course 10', completed: true },
      ],
    },
    {
      name: 'Skill Development Core Courses (SDC)',
      minCourses: 4,
      totalCredits: 7,
      earnedCredits: 7,
      courses: [
        { name: 'SDC Course 1', completed: true },
        { name: 'SDC Course 2', completed: true },
        { name: 'SDC Course 3', completed: true },
        { name: 'SDC Course 4', completed: true },
      ],
    },
  ],
};

const Dashboard = ({studentId}) => {

  const [popupVisible, setPopupVisible] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);

  const handleShowPopup = (category) => {
    setSelectedCategory(category);
    setPopupVisible(true);
  };

  const handleClosePopup = () => {
    setPopupVisible(false);
    setSelectedCategory(null);
  };
  console.log(studentId);

  return (
    <div className="dashboard">
      <h2>Student Dashboard</h2>
      <Summary data={dummyData} StudentId={studentId} />
      <CategoryList
        categories={dummyData.categories}
        onShowPopup={handleShowPopup}
      />
      {popupVisible && (
        <CategoryDetailsPopup
          category={selectedCategory}
          onClose={handleClosePopup}
        />
      )}
    </div>
  );
};

export default Dashboard;
