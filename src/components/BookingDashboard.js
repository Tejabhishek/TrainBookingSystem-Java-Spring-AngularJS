import React, { Component } from 'react';
import FormFields from './FormFields'
import '../App.css';

class BookingDashboard extends Component {

  constructor(){
    super();
    this.state = {
        exactTime:false
    }
}

  handleForm(dash){
this.props.formData(dash);
   
  }
  handleChange(e){
    var value = this.refs.trip.value;
    this.props.triptype(value);
    e.preventDefault();
  }
  handleTripType(e){
    var train_type = this.refs.train_type.value;
    this.props.train_type(train_type);
    e.preventDefault();
  }
  handleConnections(e){
    var connections = this.refs.connections.value;
    this.props.connections(connections);
    e.preventDefault();
  }
  handleCheck(e){
    let checkState = this.state.exactTime;
    checkState = !this.state.exactTime;
    console.log('checkState'+ checkState);
    this.setState({exactTime:checkState});
    
   this.props.exactFlag(checkState);
    
  }
  render() {
    return (
      <div className="Dashboard"> 
      <div style={{"display":"flex", "flexDirection":"row"}}>
      <div className="col-md-6">
      <span className="fieldLabel">Trip Type:</span>
          <select ref="trip"  className="selectField" onChange={this.handleChange.bind(this)} style={{"marginRight":"5px"}}>
            <option  disabled>Trip Type</option>
              <option> One-way</option>
              <option> Roundtrip</option>
          </select>
          </div>
          <div className="col-md-6">
          <span className="fieldLabel">Train Type:</span>
          <select ref="train_type"  className="selectField" onChange={this.handleTripType.bind(this)} style={{"marginRight":"5px"}}>
          <option  disabled>Train Type</option>
              <option> Any</option>
              <option> Regular</option>
              <option> Express</option>
          </select>
          </div>
        <div className="col-md-6">
        <span className="fieldLabel" >Connections:</span>
         <select ref="connections" className="selectField" onChange={this.handleConnections.bind(this)} style={{"marginRight":"5px"}} >
         <option  disabled>Connections</option>
              <option> Any</option>
              <option> One</option>
              <option> None</option>
          </select>
        </div>
          <div className="checkBox" style={{"display":"flex", "flexDirection":"row","marginTop":"25px","marginLeft":"35px","fontSize":"25px"}}><div className="checkBoxName">Exact Time</div><input type="checkbox"  onClick={this.handleCheck.bind(this,"Exact_Time")} style={{"height":"30px","width":"70px"}}className="checks" value="Exact_Time"/></div>
       </div>
        <FormFields formData={this.handleForm.bind(this)} tripPass={this.props.tripData}/>
       
      </div>
    );
  }
}

export default BookingDashboard;
