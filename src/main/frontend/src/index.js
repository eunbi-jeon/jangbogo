import React from 'react';
<<<<<<< HEAD
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
=======
import ReactDOM from 'react-dom';
import { BrowserRouter as Router  } from 'react-router-dom';
import './index.css';
import App from './app/App';

ReactDOM.render(
  <Router>
      <App />
  </Router>, 
  document.getElementById('root')
);
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
