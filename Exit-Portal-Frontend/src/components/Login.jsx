import React, { useState } from 'react';
import Header from './Header';
import Footer from './Footer';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Styles/Login.css';

const Login = () => {
  const [id, setId] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false); // New loading state
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true); // Set loading to true when submitting

    try {
      const response = await axios.post('http://localhost:8080/api/v1/frontend/login', {
        universityid: id,
      });

      if (response.data && response.data.universityid) {
        navigate('/dashboard', { state: { studentId: response.data.universityid } });
      } else {
        setError('Invalid response from server.');
      }
    } catch (err) {
      setError('Student ID not found. Please try again.');
    } finally {
      setLoading(false); // Stop loading once the request is complete
    }
  };

  return (
    <div>
      <Header />
      <div className="login animate-fade-in"> {/* Fade-in animation class */}
        <h2>Student Login</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Enter Student ID"
            value={id}
            onChange={(e) => setId(e.target.value)}
            required
          />
          <button type="submit" disabled={loading}> {/* Disable button when loading */}
            {loading ? <div className="button-spinner" /> : 'Login'} {/* Spinner while loading */}
          </button>
        </form>
        {error && <p className="error-message">{error}</p>}
      </div>
      <Footer />
    </div>
  );
};

export default Login;
