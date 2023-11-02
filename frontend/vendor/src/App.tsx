// import React from 'react';
import './App.css';
import RouteLink from './routes/Route';
import { GlobalStyles } from 'styles/GlobalStyles';
import { GlobalFonts } from 'styles/GlobalFonts';

function App() {
  return (
    <div className="App">
      <GlobalFonts />
      <GlobalStyles />
      <header className="App-header">
        <RouteLink></RouteLink>
      </header>
    </div>
  );
}

export default App;
