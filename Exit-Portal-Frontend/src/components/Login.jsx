import React, { useState } from 'react';

const Login = ({ onLogin }) => {
  const [id, setId] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onLogin(id);
  };

  return (
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
  );
};

export default Login;
