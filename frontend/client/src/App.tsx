import React from 'react';
import logo from 'logo.png';
import './App.css';
import RouteLink from 'routes/Route';

function App() {
  return (
    <div className="App">
      <img src={logo} alt="" />
      <header className="App-header">
        <RouteLink></RouteLink>
      </header>
    </div>
  );
}

export default App;
