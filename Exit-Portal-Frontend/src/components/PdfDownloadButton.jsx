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
      const storedData = localStorage.getItem(`reportData_${studentId}`);
      if (storedData) {
        setReportData(JSON.parse(storedData));
      } else {
        const response = await axios.post("https://exit-portal-requirement-klu-production.up.railway.app/api/v1/frontend/generatereport", {
          universityId: studentId
        });
        if (response.data) {
          setReportData(response.data);
          localStorage.setItem(`reportData_${studentId}`, JSON.stringify(response.data));
        }
      }
    } finally {
      setLoading(false);
    }
  };

  const generatePDF = () => {
    if (!reportData) return;

    const doc = new jsPDF("p", "pt", "a4");
    const pageWidth = doc.internal.pageSize.width;
    const pageHeight = doc.internal.pageSize.height;
    let currentY = 40;

    const addPage = () => {
      doc.addPage();
      currentY = 40;
    };

    const checkPageSpace = (requiredSpace) => {
      if (currentY + requiredSpace > pageHeight - 40) addPage();
    };

    // Header
    doc.setFillColor(0, 51, 102);
    doc.rect(0, 0, pageWidth, 60, 'F');
    doc.setTextColor(255, 255, 255);
    doc.setFontSize(20);
    doc.setFont("helvetica", "bold");
    doc.text("KONERU LAKSHMAIAH EDUCATION FOUNDATION", pageWidth / 2, 30, { align: "center" });
    doc.setFontSize(16);
    doc.text("DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING", pageWidth / 2, 50, { align: "center" });

    currentY = 80;

    // Student Info
    doc.setTextColor(0, 0, 0);
    doc.setFontSize(12);
    doc.setFont("helvetica", "normal");
    doc.text(`Student Name: ${reportData.studentName}`, 40, currentY);
    doc.text(`Student ID: ${reportData.studentId}`, 40, currentY + 20);
    currentY += 100;

    // Categories
    reportData.categories.forEach((category) => {
      checkPageSpace(60);

      doc.setFillColor(230, 230, 230);
      doc.rect(30, currentY - 20, pageWidth - 60, 30, 'F');
      doc.setFont("helvetica", "bold");
      doc.setFontSize(14);
      doc.text(category.categoryName, 40, currentY);

      doc.setFont("helvetica", "normal");
      doc.setFontSize(10);
      doc.text(`Required courses: ${category.minRequiredCourses}`, 40, currentY + 20);
      doc.text(`Completed courses: ${category.registeredCourses}`, 450, currentY + 20);
      currentY += 30;

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
          styles: { cellPadding: 2, fontSize: 10, overflow: 'linebreak', halign: 'center', valign: 'middle' },
          headStyles: { fillColor: [150, 150, 150], fontStyle: 'bold' },
          didDrawPage: () => (currentY = 80) // reset for new page
        });

        currentY = doc.lastAutoTable.finalY + 50;
      } else {
        currentY -= 10;
        doc.setFont("helvetica", "normal");
        doc.setFontSize(10);
        doc.setTextColor(100, 100, 100);
        doc.text("No courses registered", pageWidth / 2, currentY + 20, { align: "center" });
        doc.setTextColor(0, 0, 0);
        currentY += 60;
      }
    });

    // Footer
    doc.setFillColor(0, 51, 102);
    doc.rect(0, pageHeight - 30, pageWidth, 30, 'F');
    doc.setTextColor(255, 255, 255);
    doc.setFontSize(10);
    doc.text(`Total Registered Courses: ${reportData.totalRegisteredCourses}`, 40, pageHeight - 15);
    doc.text(`Total Registered Credits: ${reportData.totalRegisteredCredits}`, pageWidth - 40, pageHeight - 15, { align: "right" });

    doc.save(`${reportData.studentId}_Exit_Requirement_Report.pdf`);
  };

  return (
    <div className="flex justify-center mt-4">
      <button
        onClick={generatePDF}
        disabled={loading || !reportData}
        className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded transition-colors duration-200 disabled:bg-gray-400"
      >
        {loading ? "Loading data..." : "Download"}
      </button>
    </div>
  );
};

export default PdfDownloadButton;
