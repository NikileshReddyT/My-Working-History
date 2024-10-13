import React, { useState } from 'react';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import './App.css';

function App() {
  const [studentId, setStudentId] = useState(null);

  const handleLogin = (id) => {
    setStudentId(id);
  };

  return (
    <div className="App">
      {!studentId ? (
        <Login onLogin={handleLogin} />
      ) : (
        <Dashboard studentId={studentId} />
      )}
    </div>
  );
}

export default App;
