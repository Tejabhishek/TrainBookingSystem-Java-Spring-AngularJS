import React, { Component } from 'react';
import ls from 'local-storage';
import '../App.css';
var Link = require('react-router-dom').Link;
class Nav extends Component {
  handleLogout(){
    console.log("logging the user out");
   
    ls.remove("loginData");
     ls.remove('emailId');
    ls.clear();
    console.log("now the localstoarge is")
    console.log(ls.get("loginData"));
    this.props.loggedOut("loggedOut")
    
  }
  render() {
    //console.log(ls.get("loginData"));
    //console.log(ls.get("loginData").accessToken);
    //console.log(ls.get("loginData").signedRequest);
    var show = false;
   console.log(ls.get("loginData"));
   if(ls.get("loginData")!==null){
     if(ls.get("loginData").status !=="unknown"){
       console.log('it exists')
     console.log('wassup');
    var accessToken = (ls.get("loginData").accessToken!==null)?ls.get("loginData").accessToken:null;
       show=(ls.get("loginData").accessToken!==null)?true:false;
  }
}
    console.log(accessToken);
   

    return (
      <div className="Nav"> 
      <div className="NavItems" style={{"display":"flex", "flexDirection":"row"}}>
        <div className="navItem"style={{"marginRight":"10px"}}>
        California Ultra-Speed Rail 
        </div>
      
        <div className="navItem"style={{"marginRight":"10px"}}>

        <Link to={`/main`} style={{"color":"white"}}>
            Search Trains
        </Link>
                
        </div>
        <div className="navItem"style={{"marginRight":"10px"}}>
        <Link to={`/mybookings`} style={{"color":"white"}}>
           My Bookings
        </Link>
        </div>
        <div className="navItem"style={{"marginRight":"10px"}}>

          <Link to={`/traincancel`} style={{"color":"white"}}>
              Cancel Trains
          </Link>
                  
          </div>
          <div className="navItem"style={{"marginRight":"10px"}}>

            <Link to={`/ResetApp`} style={{"color":"white"}}>
                Reset Application
            </Link>
                    
            </div>


        <div onClick={this.handleLogout.bind(this)}className="navItem"style={{"marginLeft":"30px","marginRight":"10px","display":(show != false)?"block":"none"}}>
        <Link to={`/main`} style={{"color":"white"}}>
           <div>Sign Out</div>
        </Link>
        </div>
       
      </div>     
      <div  style={{"fontSize":"12px","color":"white","display":"flex", "flexDirection":"row","marginLeft":"60px","marginTop":"1px"}}>
          {ls.get("emailId")}
        </div>
      </div>
    );
  }
}

export default Nav;
