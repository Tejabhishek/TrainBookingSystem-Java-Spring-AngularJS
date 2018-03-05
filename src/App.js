import React, { Component } from 'react';
import { render } from 'react-dom';

import './App.css';
import Nav from './components/Nav';
import BookingDashboard from './components/BookingDashboard'
import AddToCart from './components/AddToCart'
import Main from './components/Main'
import SignedOut from './components/SignedOut'
import Bookings from './components/Bookings';
import App from './components/App';
import ResetApp from './components/ResetApp'
import TrainCancel from './components/TrainCancel';

// Import routing components
var ReactRouter = require('react-router-dom');
var IndexRoute = ReactRouter.IndexRoute;
var Router = ReactRouter.BrowserRouter;
var Route = ReactRouter.Route;



const  router = (
<Router>
  <div className="container">
    <Route path='/' component={App}/>
  
    <Route path='/Main' component={Main}/>
    <Route path="/mybookings" component={Bookings}/>
    <Route path="/signout" component={SignedOut}/>
    <Route path='/cart/:triptype' component={AddToCart}/>
    <Route path='/traincancel' component={TrainCancel}/>
    <Route path='/ResetApp' component={ResetApp}/>
  
    </div>
  </Router>
)
render(router, document.getElementById('root'));