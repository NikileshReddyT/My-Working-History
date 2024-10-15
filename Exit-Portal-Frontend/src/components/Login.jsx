import React, { useState } from 'react';
import Header from './Header'
import Footer from './Footer';
import './Login.css';
const Login = ({ onLogin }) => {
  const [id, setId] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onLogin(id);
  };

  return (
    <div>
      <Header />
    <div className="login">
      <h2>Student Login</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Enter Student ID"
          value={id}
          onChange={(e) => setId(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>
    </div>
    <Footer />
    </div>
  );
};

export default Login;
