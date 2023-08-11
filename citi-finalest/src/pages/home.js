import React, { useState } from 'react';
import "./home.css";

const HomePage = ({onLogin}) => {
    // const [password, setPassword] = useState('');
    // const handleLogin = (event) => {
    //     event.preventDefault();
    //     // You can replace these hard-coded values with your actual authentication logic
    //     if (password === '1234') {
    //       onLogin(true);
    //     } else {
    //       alert('Incorrect password.');
    //     }
    //   };

  return (
    <div>
    <div className='about-us-box'>
      <h1>About the Application</h1>
      <p>
        This is a brief description of the application. It does something.
        {/* awesome! <Link to="/more">Find More</Link>. */}
      </p>
    </div>
    {/* <div className="login-container">
    <h2>Login</h2>
      <form onSubmit={handleLogin}>
       
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>
    </div> */}
    </div>
  );
};

export default HomePage;
