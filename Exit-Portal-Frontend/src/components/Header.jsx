import React from 'react';
import KLlogo from '../images/KLlogo.png'; // Correct path

const Header = () => {
  return (
    <div>
      <header>
        <img src={KLlogo} alt='Logo' className='logo' />
        <h1>KL DEEMED TO BE UNIVERSITY</h1>
      </header>
    </div>
  );
};

export default Header;
