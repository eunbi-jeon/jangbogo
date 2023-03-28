import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import Header from './components/Header'
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
          <BrowserRouter>
            <Header />
            <App />
          </BrowserRouter>
);