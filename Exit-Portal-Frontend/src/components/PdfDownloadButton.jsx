import React, { useState, useEffect } from "react";
import jsPDF from "jspdf";
import "jspdf-autotable"; // For creating tables in the PDF
import axios from "axios";

const PdfDownloadButton = ({ studentId }) => {
  const [data, setData] = useState([]);

  useEffect(() => {
    if (studentId) {
      axios
        .post("http://localhost:8080/api/v1/frontend/getdata", {
          universityid: studentId,
        })
        .then((res) => {
          setData(res.data);
        })
        .catch((err) => {
          console.error(err);
        });
    }
  }, [studentId]);

  const generatePDF = () => {
    const doc = new jsPDF();

    doc.text("Student Data Report", 14, 28); // Title

    // Create a table with the data
    doc.autoTable({
      startY: 30,
      head: [
        [
          "Category Name",
          "Required Courses",
          "Completed Courses",
          "Remaining Courses",
          "Required Credits",
          "Completed Credits",
        ],
      ],
      body: data.map((course) => [
        course.categoryName,
        course.minRequiredCourses,
        course.completedCourses,
        course.minRequiredCourses - course.completedCourses,
        course.minRequiredCredits,
        course.completedCredits,
      ]),
    });

    // Add additional student details
    doc.text(`Student ID: ${studentId}`, 14, 10);
    doc.text(`Student Name: ${data[0].studentName}`, 14, 17);

    doc.save(`student_exit_report_${studentId}.pdf`); // Save the PDF
  };

  return (
    <h3>
      <a style={{cursor:"pointer"}} onClick={generatePDF}>Download PDF</a>
    </h3>
  );
};

export default PdfDownloadButton;
