import React, { Component } from 'react';
import Nav from './Nav';
import ls from 'local-storage';
import { Redirect } from 'react-router'
import '../App.css';
var Link = require('react-router-dom').Link;


 
  class SignedOut extends React.Component {
 
    render() {
        
      return (
        <div>
          <Nav />
          <div>
           You have successfully been logged out
</div>
           
               <Link to="/login" style={{"color":"white"}}>
               <button className="btn btn-success" style={{"width":"400px","marginTop":"300px","marginLeft":"350px","color":"white !important"}} >
                    Login
                    </button>   
               </Link>
           
        </div>
      )
    }
  }
 

  export default SignedOut;
