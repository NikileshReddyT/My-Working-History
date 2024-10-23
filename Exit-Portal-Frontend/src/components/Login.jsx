import React, { useState } from 'react';
import Header from './Header';
import Footer from './Footer';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Styles/Login.css';
import logo from '../images/Frame 1 NR.png'; // Update with the path to your logo

const Login = () => {
  const [id, setId] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent default form submission
    setError(''); // Clear any previous errors
    setLoading(true); // Show loading spinner

    try {
      const response = await axios.post('http://localhost:8080/api/v1/frontend/login', {
        universityid: id, // Send student ID in the request
      });

      if (response.data && response.data.universityid) {
        // Navigate to dashboard if login is successful
        navigate('/dashboard', { state: { studentId: response.data.universityid } });
      } else {
        // Handle cases where server doesn't return the expected data
        setError('Invalid response from server.');
      }
    } catch (err) {
      // Handle network or server errors
      setError('Invalid Student ID.');
    } finally {
      // Hide loading spinner once the request is complete
      setLoading(false);
    }
  };

  return (
    <div>
      <Header />
      <div className="login animate-fade-in">
        <div className="login-container">
          <img src={logo} alt="University Logo" className="logo" />
          <h2>Student Login</h2>
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              placeholder="Enter Student ID" // Add placeholder for clarity
              value={id}
              onChange={(e) => setId(e.target.value)}
              required
            />
            <button type="submit" disabled={loading}>
              {loading ? <div className="button-spinner" /> : 'Login'}
            </button>
          </form>
          {error && <p className="error-message">{error}</p>}
          <p className="forgot-password" onClick={() => alert('Reset password functionality coming soon!')}>
            Forgot Password?
          </p>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
