import React from 'react';
import logo from './logo.png';
import './App.css';
import RouteLink from './routes/Route';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo}></img>
        <RouteLink></RouteLink>
      </header>
    </div>
  );
}

export default App;
