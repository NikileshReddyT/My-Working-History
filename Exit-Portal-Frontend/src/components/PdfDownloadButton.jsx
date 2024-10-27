import React, { useState, useEffect } from "react";
import axios from "axios";
import jsPDF from "jspdf";
import "jspdf-autotable";

const PdfDownloadButton = ({ studentId }) => {
  const [loading, setLoading] = useState(false);
  const [reportData, setReportData] = useState(null);

  useEffect(() => {
    if (studentId) {
      fetchReportData();
    }
  }, [studentId]);

  const fetchReportData = async () => {
    setLoading(true);
    try {
      // Check if data is already in local storage
      const storedData = localStorage.getItem(`reportData_${studentId}`);
      if (storedData) {
        setReportData(JSON.parse(storedData));
        console.log("Data loaded from local storage:", JSON.parse(storedData));
      } else {
        // Fetch data from the server if not in local storage
        const response = await axios.post("http://localhost:8080/api/v1/frontend/generatereport", {
          universityId: studentId
        });
        if (response.data) {
          setReportData(response.data);
          // Store the fetched data in local storage
          localStorage.setItem(`reportData_${studentId}`, JSON.stringify(response.data));
          console.log("Data fetched successfully:", response.data);
        } else {
          console.log("No data received from backend");
        }
      }
    } finally {
      setLoading(false);
    }
  };

  const generatePDF = () => {
    if (!reportData) {
      console.log("No report data available to generate PDF.");
      return;
    }

    const doc = new jsPDF("p", "pt", "a4");
    const pageWidth = doc.internal.pageSize.width;
    const pageHeight = doc.internal.pageSize.height;
    let currentY = 40;

    // Header
    doc.setFillColor(0, 51, 102);
    doc.rect(0, 0, pageWidth, 60, 'F');
    doc.setTextColor(255, 255, 255);
    doc.setFontSize(20);
    doc.setFont("helvetica", "bold");
    doc.text("KONERU LAKSHMAIAH EDUCATION FOUNDATION", pageWidth / 2, 30, { align: "center" });
    doc.setFontSize(16);
    doc.text("DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING", pageWidth / 2, 50, { align: "center" });

    // Student Info
    currentY = 80;
    doc.setTextColor(0, 0, 0);
    doc.setFontSize(12);
    doc.setFont("helvetica", "normal");
    doc.text(`Student Name: ${reportData.studentName}`, 40, currentY);
    doc.text(`Student ID: ${reportData.studentId}`, 40, currentY + 20);
    currentY += 100;

    // Categories
    reportData.categories.forEach((category, index) => {
      // Category Header
      doc.setFillColor(230, 230, 230);
      doc.rect(30, currentY - 20, pageWidth - 60, 30, 'F');
      doc.setFont("helvetica", "bold");
      doc.setFontSize(14);
      doc.text(category.categoryName, 40, currentY);

      // Category Info
      doc.setFont("helvetica", "normal");
      doc.setFontSize(10);
      doc.text(`Required courses: ${category.minRequiredCourses}`, 40, currentY + 20);
      doc.text(`Completed courses: ${category.registeredCourses}`, 300, currentY + 20);
      currentY += 40;

      // Course Data
      if (Array.isArray(category.courses) && category.courses.length > 0) {
        const tableData = category.courses.map(course => [
          course.courseCode,
          course.year?.toString() || "-",
          course.semester || "-",
          course.courseName,
          course.credits?.toString() || "-",
          course.grade || "Not Released"
        ]);

        doc.autoTable({
          head: [["Course Code", "Year", "Semester", "Name", "Credits", "Result"]],
          body: tableData,
          startY: currentY,
          margin: { horizontal: 30 },
          styles: {
            cellPadding: 2,
            fontSize: 10,
            overflow: 'linebreak',
            halign: 'center',
            valign: 'middle',
            fillColor: [200, 200, 200],
            textColor: [0, 0, 0],
            fontStyle: 'normal'
          },
          headStyles: {
            fillColor: [150, 150, 150],
            textColor: [0, 0, 0],
            fontStyle: 'bold'
          },
          didDrawCell: (data) => {
            if (data.column.index === 5) {
              const grade = data.cell.text;
              if (grade === "O") {
                doc.setTextColor(0, 128, 0); // Green for O grade
              } else if (grade === "A+" || grade === "A") {
                doc.setTextColor(0, 102, 204); // Blue for A grades
              } else {
                doc.setTextColor(0, 0, 0); // Black for other grades
              }
            }
          },
          didDrawPage: (data) => {
            doc.setTextColor(0, 0, 0); // Reset text color
          }
        });

        currentY = doc.lastAutoTable.finalY + 80; // Add space here
      } else {
        doc.text("No courses registered", pageWidth / 2, currentY, { align: "center" });
        currentY += 20;
      }
    });

    // Footer
    doc.setFillColor(0, 51, 102);
    doc.rect(0, pageHeight - 30, pageWidth, 30, 'F');
    doc.setTextColor(255, 255, 255);
    doc.setFontSize(10);
    doc.text(`Total Registered Courses: ${reportData.totalRegisteredCourses}`, 40, pageHeight - 15);
    doc.text(`Total Registered Credits: ${reportData.totalRegisteredCredits}`, pageWidth - 40, pageHeight - 15, { align: "right" });

    doc.save(`${reportData.studentId}_Academic_Transcript.pdf`);
  };

  return (
    <div className="flex justify-center mt-4">
      <button
        onClick={generatePDF}
        disabled={loading || !reportData}
        className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded transition-colors duration-200 disabled:bg-gray-400"
      >
        {loading ? "Loading data..." : "Download PDF"}
      </button>
    </div>
  );
};

export default PdfDownloadButton;
