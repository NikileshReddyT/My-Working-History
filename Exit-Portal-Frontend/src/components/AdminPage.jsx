import React, { useState } from 'react';
import axios from 'axios';
import { saveAs } from 'file-saver';
import './Styles/admin.css'

const AdminPage = () => {
  const [activeSection, setActiveSection] = useState('categories');
  const [studentData, setStudentData] = useState([]);
  const [loading, setLoading] = useState(false);

  // Upload CSV file to the backend
  const handleFileUpload = async (event, endpoint) => {
    const file = event.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    try {
      await axios.post(`http://localhost:8080/api/${endpoint}`, formData, {
        headers: { "Content-Type": "multipart/form-data" }
      });
      alert("File uploaded successfully");
    } catch (error) {
      alert("File upload failed");
    }
  };

  // Fetch student data from the backend
  const fetchStudentData = async () => {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/admin/students");
      setStudentData(response.data.adminConsolidation || []);
    } catch (error) {
      alert("Failed to fetch student data");
    } finally {
      setLoading(false);
    }
  };

  // Download student data as an Excel file
  const downloadExcel = () => {
    const data = studentData.map((item) => ({
      ...item,
    }));

    const blob = new Blob([JSON.stringify(data, null, 2)], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8",
    });
    saveAs(blob, "student_data.xlsx");
  };

  return (
    <div className="admin-page">
      <h1>Admin Dashboard</h1>

      {/* Navigation Bar */}
      <nav className="nav-bar">
        <button onClick={() => setActiveSection('categories')} className={activeSection === 'categories' ? 'active' : ''}>Upload Categories CSV</button>
        <button onClick={() => setActiveSection('courses')} className={activeSection === 'courses' ? 'active' : ''}>Upload Courses CSV</button>
        <button onClick={() => setActiveSection('students')} className={activeSection === 'students' ? 'active' : ''}>Upload Students CSV</button>
        <button onClick={() => setActiveSection('studentData')} className={activeSection === 'studentData' ? 'active' : ''}>View Student Data</button>
      </nav>

      {/* Section Content */}
      <div className="section-content">
        {activeSection === 'categories' && (
          <section className="file-upload-section">
            <h2>Upload Categories CSV</h2>
            <input
              type="file"
              accept=".csv"
              onChange={(event) => handleFileUpload(event, "uploadCategories")}
            />
          </section>
        )}
        
        {activeSection === 'courses' && (
          <section className="file-upload-section">
            <h2>Upload Courses CSV</h2>
            <input
              type="file"
              accept=".csv"
              onChange={(event) => handleFileUpload(event, "uploadCourses")}
            />
          </section>
        )}

        {activeSection === 'students' && (
          <section className="file-upload-section">
            <h2>Upload Students CSV</h2>
            <input
              type="file"
              accept=".csv"
              onChange={(event) => handleFileUpload(event, "uploadStudents")}
            />
          </section>
        )}

        {activeSection === 'studentData' && (
          <section className="student-data-section">
            <h2>Student Data</h2>
            <button onClick={fetchStudentData} disabled={loading}>
              {loading ? "Loading..." : "Fetch Student Data"}
            </button>

            {studentData.length > 0 && (
              <div>
                <table>
                  <thead>
                    <tr>
                      <th>Category Name</th>
                      <th>Completed Courses</th>
                    </tr>
                  </thead>
                  <tbody>
                    {studentData.map((item, index) => (
                      <tr key={index}>
                        <td>{Object.keys(item)[0]}</td>
                        <td>{Object.values(item)[0]}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
                <button onClick={downloadExcel}>Download Excel</button>
              </div>
            )}
          </section>
        )}
      </div>
    </div>
  );
};

export default AdminPage;
