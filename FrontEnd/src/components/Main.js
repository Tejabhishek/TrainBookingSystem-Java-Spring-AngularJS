import React, { Component } from 'react';
import { render } from 'react-dom';
// Import routing components
import {Router, Route} from 'react-router';
import ls from 'local-storage';
import Nav from './Nav';
import FacebookLogin from 'react-facebook-login';
import { GoogleLogin } from 'react-google-login';
import BookingDashboard from './BookingDashboard';
import axios from 'axios';


var Link = require('react-router-dom').Link;
class Main extends Component {

 
  constructor(){
   
    super();
    this.state = {
       trainType:[],
       tripType :[],
       connectionType:[],
        searchResults:[ ],
        selectedResult:[],
        exactTimeFlag:false,
        round:0,
        originalSearchResult:[],
        loggedIn:(ls.get('loginData')!==null)?true:false
    }
   
  
  }
  componentWillMount(){
    this.setState({
      tripType:[
        {
            one_way: true,
            round_trip:false

        }]
    })

    this.setState({
      trainType:[
        {
            Regular: false,
            Express:false,
            Any:true
        }]
    })

    this.setState({
      connectionType:[
        {
          Any:true,
          None:false,
          One: false
        }
      ]
    })
   /* if(ls.get("loginData")!=null){
      var accessToken = (ls.get("loginData").accessToken!==null)?ls.get("loginData").accessToken:null;
      this.setState({loggedIn:true});
      console.log("running the constructor");
    }*/
    
}
  handleTripType(val){
  
    let curState = this.state.tripType;
    switch(val){
      case 'One-way':
      curState[0].one_way=true;
      curState[0].round_trip=false;
      break;
      case 'Roundtrip':
      curState[0].one_way = false;
      curState[0].round_trip = true;
      break;
      default:
      curState[0].one_way = true;
      curState[0].round_trip = false;
      break;
    }
    this.setState({tripType:curState});
   // console.log(this.statetripType);
  }
  handleTrainType(val){
      let trainTypeState = this.state.trainType;
      switch(val){
        case 'Regular':
          trainTypeState[0].Regular = true;
          trainTypeState[0].Express = false;
          trainTypeState[0].Any = false;
          
        break;

        case 'Express':
        trainTypeState[0].Regular = false;
        trainTypeState[0].Express = true;
        trainTypeState[0].Any = false;
        

        break;

        case 'Any':
        trainTypeState[0].Regular = false;
        trainTypeState[0].Express = false;
        trainTypeState[0].Any = true;
        break;
      }
      this.setState({trainType:trainTypeState});
  }
  handleConnectionsType(conn){
    let connection = this.state.connectionType;
    switch(conn){
      case 'Any':
      connection[0].Any = true;
      connection[0].None = false;
      connection[0].One = false;
      break;
      case 'None':
      connection[0].Any = false;
      connection[0].None = true;
      connection[0].One = false;
      break;
      case 'One':
      connection[0].Any = false;
      connection[0].None = false;
      connection[0].One = true;
      break;
    }
    this.setState({connectionType:connection});
  }

  responseFacebook(response) {
  ls.set("loginData",response)
  ls.set('emailId',response.email)
  console.log('giant id'+ls.get('emailId'));
   var curState = this.state.loggedIn;
    console.log(curState)
    console.log("showing the state of ")
    console.log(ls.get("loginData"));
    if(ls.get("loginData")!==null){
        if(ls.get("loginData").status!=="unknown"){
          curState = true;
          this.setState({loggedIn:curState})
        }
    console.log("we are here");

    }
}

handleFacebook(){
  
  var curState = this.state.loggedIn;
    console.log(curState)
    console.log("showing the state of ")
    console.log(ls.get("loginData"));
    if(ls.get("loginData")!==null){
        if(ls.get("loginData").status!=="unknown"){
          curState = true;
          this.setState({loggedIn:curState})
        }
    console.log("we are here");
}
}

responseGoogle(response){
  console.log('responses Google')
  console.log(response.profileObj.email);
  var curState = this.state.loggedIn;
  ls.remove('loginData');
  ls.set('loginData',response);
  ls.set('emailId',response.profileObj.email)
  console.log('little id '+ls.get('emailId'));
  var curState = this.state.loggedIn;
  if(ls.get("loginData")!==null){
    if(ls.get("loginData").status!=="unknown"){
      curState = true;
      this.setState({loggedIn:curState})
    }
}
}































  handleExactFlag(flag){
    console.log("flag "+ flag);
    this.setState({exactTimeFlag:flag});
  }


  handleSearch(val){
    console.log("i wass called now...");
    ls.remove("Booking_Data");
    var rounds = this.state.round;
    
   if(rounds===0){
     ls.set("data",val);
     
   }
   console.log("ls");
   console.log(ls.get("data"));
   console.log("round "+ rounds);

   
    if(this.state.tripType[0].one_way===true){
      console.log('processing one way trips');
                    var from = val.from;
                    var to = val.to;
                   
                  //  console.log(days);
                    var time = val.time;
                    var traintype;
                    var trip_type;
                    var exactTime = this.state.exactTimeFlag;
                    //var No_of_conn ="One";
                  console.log('exact time '+this.state.exactTimeFlag);
                  //console.log(this.state.exactTime);
                    if(this.state.trainType[0].Regular === true){
                      traintype = "Regular"
                    }else
                    if(this.state.trainType[0].Express===true){
                      traintype = "Express"
                    }else{
                      traintype = "Any"
                    }

                    if(this.state.tripType[0].one_way === true){
                      trip_type = "One-way"
                    }else
                    if(this.state.tripType[0].round_trip===true){
                      trip_type = "Roundtrip"
                    }

                    var connType;
                    if(this.state.connectionType[0].Any === true){
                      connType = "Any"
                    }else
                    if(this.state.connectionType[0].None===true){
                      connType = "None"
                    }else{
                      connType = "One"
                    }




                    var triptype = this.state.tripType;
                    var tripDate = val.tripDate;
                    console.log(tripDate)
                    axios.get('http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/train', {
                      params: {
                        depStation: from,
                        arrStation:to,
                        type:traintype,
                        depTime:time,
                        exactTimeFalg:exactTime,
                        NumofConnections:connType,
                        tripDate:tripDate
                      }
                    })
                    .then( (response)=> {
                      //console.log("response");
                      console.log(response.data);
                      this.setState({searchResults:response.data})
                    })
                    .catch(function (error) {
                      console.log(error);
                    });

} else
if(this.state.tripType[0].round_trip===true){
    console.log('processing round trips');
    var from = val.from;
    var to = val.to;
    var startTime = val.st_time;
    var endTime = val.e_time;
    var exactTime = this.state.exactTimeFlag;
    //var No_of_conn ="One";
    var traintype;
    var startDate =new Date(val.st_date) ;
    var endDate = new Date(val.e_date) ;

    
    var from_ms = startDate.getTime();
    var to_ms = endDate.getTime();
    //var diff = to_ms -from_ms  ;
    var one_day=1000*60*60*24;
    var difference_ms = to_ms -from_ms ;
    var days =  Math.round(difference_ms/one_day);
   
    console.log(days)


    var connType;
    if(this.state.connectionType[0].Any === true){
      connType = "Any"
    }else
    if(this.state.connectionType[0].None===true){
      connType = "None"
    }else{
      connType = "One"
    }
 
     console.log("start time"+ startTime);
     if(this.state.trainType[0].Regular === true){
       traintype = "Regular"
     }else
     if(this.state.trainType[0].Express===true){
       traintype = "Express"
     }else{
       traintype = "Any"
     }


    if(days <= 7){
      if(rounds===0){
        console.log("axios working for round 0")
                  axios.get('http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/train', {
                    params: {
                      depStation: from,
                      arrStation:to,
                      type:traintype,
                      depTime:startTime,
                      exactTimeFalg:exactTime,
                      NumofConnections:connType,
                      tripDate:startDate
                    }
                  })
                  .then( (response)=> {
                    console.log(response.data);
                    this.setState({searchResults:response.data})
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
               
       }

    }
    else{
      alert("The return date cannot be greater than 7 days from the departure date ")
    }


 

}



  }

  handleSelect(sel){
  //if regular
    var currentRound = this.state.round;
    
  if(this.state.tripType[0].one_way===true){
    console.log('localstorage for one way')
    ls.remove("selected");
    ls.set("selected",sel);
  }
  else
  if(this.state.tripType[0].round_trip===true){
    console.log('localstorage for roundtrip')
//if it round 0 increment to 1 and send the thing back to ser
    var currentRound = this.state.round;
    if(currentRound === 0){
      console.log("handling round one");
      
      ls.remove("round_one");
      ls.set("round_one",sel)
      currentRound++;
      this.setState({round:1})
      this.handleSecondSearch(ls.get("data"));
    }
    else
    if(currentRound==1){
      console.log("handling round two");
      ls.remove("round_two");
      ls.set("round_two",sel)
    }


  }
   
  
   
  }

  handleLogout(val){
    var logState = this.state.loggedIn;
    console.log(logState)
    console.log('speek')
      
        logState=false;
        console.log(logState)
        this.setState({loggedIn:logState});
    
  }

  handleSecondSearch(val){

    console.log('inside the second search methods');
    console.log('rounds '+this.state.round);
    var from = val.from;
    var to = val.to;
    var startTime = val.st_time;
    var endTime = val.e_time;
    var exactTime = this.state.exactTimeFlag;
    //var No_of_conn ="One";
    var traintype;
    var startDate = val.st_date;
    var endDate = val.e_date;
    console.log("start time"+ startTime);
    console.log("from"+ from);
    if(this.state.trainType[0].Regular === true){
      traintype = "Regular"
    }else
    if(this.state.trainType[0].Express===true){
      traintype = "Express"
    }else{
      traintype = "Any"
    }

    var connType;
    if(this.state.connectionType[0].Any === true){
      connType = "Any"
    }else
    if(this.state.connectionType[0].None===true){
      connType = "None"
    }else{
      connType = "One"
    }
 
    
    console.log("axios working for round 1")
    axios.get('http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/train', {
      params: {
        depStation: to,
        arrStation:from,
        type:traintype,
        depTime:endTime,
        exactTimeFalg:exactTime,
        NumofConnections:connType,
        tripDate:endDate
      }
    })
    .then( (response)=> {
      console.log(response.data);
      this.setState({searchResults:response.data})
    })
    .catch(function (error) {
      console.log(error);
    });
  }
  render() {
   
   

    let searchResultsDisplay;
    var trip_type;
    if(this.state.tripType[0].one_way === true){
      trip_type = "One-way"
    }else
    if(this.state.tripType[0].round_trip===true){
      trip_type = "Roundtrip"
    }
    searchResultsDisplay=this.state.searchResults.map((train)=>{
   
      let totalPrice =  train.totalTripPrice;
      let totalHours = train.totalTripTime;
      let tripDate = train.tripDate;
      var len = train.trips.length;
      var fromStation = train.trips[0].depStation;
      var tostation = train.trips[len-1].arrStation;
      
    
     
        return(
            <div className="searchResult" key={train.train_id} >
              <div className="layer1" style={{"display":"flex", "flexDirection":"row"}}>
                 <div className="res_tab"> 
                 
                 Total Price : {totalPrice}

                 
                   </div> 
                   <div className="res_tab"> Total Time: {totalHours} Mins   </div> 
                   <div className="res_tab"> 
                  <div style={{"display":(this.state.tripType[0].one_way === true)?"block":"none","color":"black"}} onClick={this.handleSelect.bind(this,train)} >
                    <Link to={`/cart/${trip_type}`} >
                    <button className="btn btn-secondary" style={{"display":(this.state.tripType[0].one_way === true)?"block":"none","color":"black"}} onClick={this.handleSelect.bind(this,train)} >
                      Select
                      </button>
                    </Link>
                   </div>
                   <div style={{"display":(this.state.tripType[0].round_trip === true)?"block":"none"}} onClick={this.handleSelect.bind(this,train)} >
                   <button className="btn btn-secondary" style={{"display":(this.state.round===0)?"block":"none","color":"black"}}>Select</button>
                   <div  style={{"display":(this.state.round===1)?"block":"none","color":"black"}}>
                   <Link to={`/cart/${trip_type}`} >
                   <button className="btn btn-secondary" style={{"display":(this.state.round===1)?"block":"none","color":"black"}}>
                      Select
                      </button>
                    </Link>
                    </div>
                   </div>
                    </div>
              </div>
             
           
              {train.trips.map((a)=>(
               
               <div className="lap" key={a.name+a.arrStation} style={{"height":"150px","width":"100%","backgroundColor":"black","color":"white"}}>
               <div style={{"float":"left","fontSize":"30px","marginLeft":"20px","fontFamily":"Helvetica Neue,Helvetica,Arial,sans-serif","fontWeight":"lighter"}}>{a.departureTime} - {a.arrivalTime}
               <div style={{"fontSize":"20px"}}>{a.name}</div>
               
               </div>
                  <div className="layer2" style={{"float":"right","marginRight":"20px","fontSize":"15px","fontFamily":"Helvetica Neue,Helvetica,Arial,sans-serif","fontWeight":"lighter"}}>
                   
                    <div> From Station: {a.depStation}</div>
                    <div>To Station:  {a.arrStation}</div>
                    <div>Seats Available: {a.availableSeats}</div>
                  </div>
              
               </div>
              
                ))}
                
            </div>
          );
      });
  
  
if(this.state.loggedIn !== false){
    return (
      <div className="container" style={{"minWidth":"1000px"}}>
        <div className="Main">
          <Nav loggedOut={this.handleLogout.bind(this)}/>
            <BookingDashboard exactFlag={this.handleExactFlag.bind(this)} formData = {this.handleSearch.bind(this)} connections={this.handleConnectionsType.bind(this)}   train_type={this.handleTrainType.bind(this)} triptype={this.handleTripType.bind(this)} tripData={this.state.tripType}/>
            {searchResultsDisplay}
        </div>
      </div>
    );}
   else{
      return (
        <div>You are not logged in
              <h1 style={{"textAlign":"center"}}>California Ultra Speed Rail</h1>
          <div style={{"marginTop":"150px","marginLeft":"350px"}}>
 <FacebookLogin
          appId="728870350654624"
          autoLoad={true}
          fields="name,email,picture"
          callback={this.responseFacebook.bind(this)}
          onClick = {this.handleFacebook.bind(this)}
        />

<GoogleLogin
    clientId="2687967248-f1r18s0bk3igtd5dno8jfifsnpcl8vke.apps.googleusercontent.com"
    buttonText="Login"
    onSuccess={this.responseGoogle.bind(this)}
    onFailure={this.responseGoogle.bind(this)}
style={{"height":"63px","width":"250px","backgroundColor":"#d1404f","color":"white"}}

  />
          </div>

        </div>
        


      );

    }
  }
}

export default Main;
