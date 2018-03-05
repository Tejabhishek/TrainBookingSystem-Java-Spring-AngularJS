import React from 'react';
import ReactDOM from 'react-dom';
import Modal from 'react-modal';
import Counter from './Counter'
import Nav from './Nav';
import ls from 'local-storage';
import axios from 'axios';
import Ticket from './Ticket';
const customStyles = {
  content : {
    top                   : '50%',
    left                  : '50%',
    right                 : 'auto',
    bottom                : 'auto',
    marginRight           : '-50%',
    transform             : 'translate(-50%, -50%)',
    height: '600px',
    width:'600px'
  }
};

class AddToCart extends React.Component {

  constructor(){
    super();
    this.state = {
        counter:1,
        countArr:[1],
        passengers:[],
        ticketProcessed:false
    }
    
  }
  

    handleCount(count){

    this.setState({counter:this.state.count});

  }
  handleCounter(eh){
  this.setState({countArr:eh});
  }
  

  handleSubmit(e){
    var passengerDetails = [];
  //  var email= ls.get('loginData').email;
  var email = ls.get('emailId');
    var val = 1;
    

      if(this.refs.key1 != null){
        passengerDetails.push(this.refs.key1.value);
        val++;
       }
       if(this.refs.key2 != null){
        passengerDetails.push(this.refs.key2.value);
        val++;
       }
       if(this.refs.key3 != null){
        passengerDetails.push(this.refs.key3.value);
        val++;
       }
       if(this.refs.key4 != null){
        passengerDetails.push(this.refs.key4.value);
        val++;
       }
       if(this.refs.key5 != null){
        passengerDetails.push(this.refs.key5.value);
        val++;
       }
       var passengerNames="";
       passengerDetails.forEach(element => {
         passengerNames+=element+","
       });

       var selectedData = ls.get('selected');
       axios({
        method: 'post',
        url: 'http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/ticket',
        data: {
          userName:email,
          noOfPassengers:this.state.countArr.length,
          passengerNames:passengerNames,
          oneWayTripTime:selectedData.totalTripTime,
          returnTripTime:null,
          totalTripPrice:(selectedData.totalTripPrice)*this.state.countArr.length,
          depDate:selectedData.tripDate,
          arrDate:null,
          oneWayTrips:selectedData.trips,
          returnTrips:null     
        }
      }).then((res)=>{
        console.log("The response for ticket submission")
       
        if(res.status===201){
        
          this.setState({ticketProcessed:true});
        }
        ls.remove("Booking_Data");
        ls.set("Booking_Data",res.data);
      });
    

    e.preventDefault()
  }

  handleRoundSubmit(e){
    var passengerDetails = [];
    //var email= ls.get('loginData').email;
    var email = ls.get('emailId');
    console.log('progress round '+email)
    var val = 1;
    

      if(this.refs.key1 != null){
        passengerDetails.push(this.refs.key1.value);
        val++;
       }
       if(this.refs.key2 != null){
        passengerDetails.push(this.refs.key2.value);
        val++;
       }
       if(this.refs.key3 != null){
        passengerDetails.push(this.refs.key3.value);
        val++;
       }
       if(this.refs.key4 != null){
        passengerDetails.push(this.refs.key4.value);
        val++;
       }
       if(this.refs.key5 != null){
        passengerDetails.push(this.refs.key5.value);
        val++;
       }
       var passengerNames="";
       passengerDetails.forEach(element => {
         passengerNames+=element+","
       });

       var round_one = ls.get('round_one');
       var round_two = ls.get('round_two');
       console.log('na');
       
       console.log("total price"+ round_one.totalTripPrice+round_two.totalTripPrice);
       axios({
        method: 'post',
        url: 'http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/ticket',
        data: {
          userName:email,
          noOfPassengers:this.state.countArr.length,
          passengerNames:passengerNames,
          oneWayTripTime:round_one.totalTripTime,
          returnTripTime:round_two.totalTripTime,
          totalTripPrice:(round_one.totalTripPrice+round_two.totalTripPrice)*this.state.countArr.length,
          depDate:round_one.tripDate,
          arrDate:round_two.tripDate,
          oneWayTrips:round_one.trips,
          returnTrips: round_two.trips    
        }
      }).then((res)=>{
        console.log("The response for ticket submission")
       
        if(res.status===201){
        
          this.setState({ticketProcessed:true});
        }
        ls.remove("Booking_Data");
        ls.set("Booking_Data",res.data);
      });
    

    e.preventDefault()
  }


  render() {

   
    var i = 1;
    var elem = this.state.countArr.map((ele)=>{
     return(
       <div key={"div"+(i-1)}>
        
            <label key={"label"+(i-1)} style={{"fontSize":"25px"}}>Passenger Name {i++}: </label>
            <input typ="text" name = {"key"+(i-1)} key={"key"+(i-1)} ref={"key"+(i-1)} style={{"height":"30px","width":"190px"}} />
         
     </div>
    );
    
   })

  
   
 
   console.log('triptype:');
   console.log(this.props.match.params.triptype);
   if(this.props.match.params.triptype==='One-way'){
    var selectedData = ls.get('selected');
    return (
      <div>
          <Nav />
       
     <div style={{"display":"flex", "flexDirection":"row"}}>
     <div style={{"height":"600px","width":"50%"}}>
        <div  style={{"display":"flex", "flexDirection":"row"}}>
          <h2 ref={subtitle => this.subtitle = subtitle} style={{"color":"#002940"}}> </h2>
        

          </div>

          <div style={{"height": "30px","width":"100%","display":"flex", "flexDirection":"row"}}>
                <h3>Add to cart</h3> 
          </div>
          <hr/>    
          
          <form>
            <div style={{"display":"flex", "flexDirection":"row"}}>
          <h2>Number of Passengers :</h2><Counter countVal={this.handleCount.bind(this)} countArray={this.handleCounter.bind(this)} />
           </div>
          </form>
          <h2 className="col-md-4">Departure station :{selectedData.trips[0].depStation}</h2>
          <h2 className="col-md-5">Arrival station :{selectedData.trips[selectedData.trips.length-1].arrStation}</h2>
          <h2 className="col-md-8">Trip time :{selectedData.totalTripTime} mins</h2>
          <h2 className="col-md-12">Number of connections :{selectedData.trips.length-1} stops </h2>
          <h2 className="col-md-8">Total Price :$ {selectedData.totalTripPrice  * this.state.countArr.length} </h2>

          </div>
         
          <div style={{"height":"600px","width":"50%"}} >
          
          <h3>Passenger Details</h3><hr/>
          <form onSubmit= {this.handleSubmit.bind(this)}>
               {elem} 
               <input style={{"display":(this.state.ticketProcessed==false)?"block":"none"}}  type="submit" className="btn btn-success"/>
                <div style={{"display":(this.state.ticketProcessed==true)?"block":"none"}}><Ticket/></div>
            </form>
          </div>

          </div>
        
      </div>
    );
  }














  else
  if(this.props.match.params.triptype==='Roundtrip'){
    var round_one = ls.get('round_one');
    var round_two = ls.get('round_two');
    console.log('round one');
    console.log(round_one);
    console.log('round two');
    console.log(round_two);
    return (
      <div>
          <Nav/>
       
     <div style={{"display":"flex", "flexDirection":"row"}}>
     <div style={{"height":"600px","width":"50%"}}>
        <div  style={{"display":"flex", "flexDirection":"row"}}>
          <h2 ref={subtitle => this.subtitle = subtitle} style={{"color":"#002940"}}> </h2>
        

          </div>

          <div style={{"height": "30px","width":"100%","display":"flex", "flexDirection":"row"}}>
                <h3>Add to cart</h3> 
          </div>
          <hr/>    
          
          <form>
            <div style={{"display":"flex", "flexDirection":"row"}}>
          <h2>Number of Passengers :</h2><Counter countVal={this.handleCount.bind(this)} countArray={this.handleCounter.bind(this)} />
           </div>
          </form>
          <div style={{"height":"100%"}}>
              <h2>Trip:</h2>
              <h3 >Departure station :{round_one.trips[0].depStation} </h3>
              <h3 >Arrival station :{round_one.trips[round_one.trips.length-1].arrStation}</h3>
              <h3 >Trip time :{round_one.totalTripTime} mins</h3>
              <h3 >Number of connections : {round_one.trips.length}stops </h3>
              <h3 >Total Price :$ {round_one.totalTripPrice * this.state.countArr.length}</h3>

              <h2>Return Trip:</h2>
              <h3 >Departure station :{round_two.trips[0].depStation} </h3>
              <h3 >Arrival station :{round_two.trips[round_two.trips.length-1].arrStation}</h3>
              <h3 >Trip time :{round_two.totalTripTime} mins</h3>
              <h3 >Number of connections : {round_two.trips.length}stops </h3>
              <h3 >Total Price :$ {round_two.totalTripPrice * this.state.countArr.length}</h3>
          </div>

          </div>
         
          <div style={{"height":"600px","width":"50%"}} >
          
          <h3>Passenger Details</h3><hr/>
          <form onSubmit= {this.handleRoundSubmit.bind(this)}>
               {elem} 
               <input style={{"display":(this.state.ticketProcessed==false)?"block":"none"}} type="submit"  className="btn btn-success"/>
               <div style={{"display":(this.state.ticketProcessed==true)?"block":"none"}}><Ticket/></div>
            </form>
          </div>

          </div>
        
      </div>
    );
  }
}
}



export default AddToCart;