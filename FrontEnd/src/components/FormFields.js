import React, { Component } from 'react';

import '../App.css';

class FormFields extends Component {
    handleSubmit(e){
        let data;
        data={
            from: this.refs.from.value,
            to:this.refs.to.value,
            time: this.refs.time.value,
            tripDate:this.refs.date.value
        } 
        console.log('data');
        console.log(data)
        this.props.formData(data);
        e.preventDefault();
    }
    handleRoundSubmit(e){
      let round_data;
      round_data={
          from: this.refs.Rfrom.value,
          to:this.refs.Rto.value,
          st_time: this.refs.st_time.value,
          e_time:this.refs.e_time.value,
          st_date:this.refs.st_date.value,
          e_date:this.refs.e_date.value

      } 
      console.log(round_data.st_date);
      this.props.formData(round_data);
      e.preventDefault();
      
    }
  render() {
      //console.log("tripPass");
      //console.log(this.props.tripPass[0].one_way);
      
    return (
      <div style={{"display":"flex", "flexDirection":"row"}}> 
        <form onSubmit={this.handleSubmit.bind(this)} style={{"display":this.props.tripPass[0].one_way? "block":"none"}}>
          
            <input  className="formField" type="text" ref="from" placeholder="From" />
           <input className="formField" placeholder="To" type="text" ref="to"/>
           <input type="date" ref="date" className="dateField" style={{"marginBottom":"20px"}}/>
           <input type="time" placeholder="Departure Time"ref="time" className="dateField"/>
  
            <input className="col-md-6" type="submit" value="Find Trains" className="btn_train"/>
        </form>

        <form onSubmit={this.handleRoundSubmit.bind(this)} style={{"display":this.props.tripPass[0].round_trip? "block":"none","marginLeft":"120px"}}>
          
            <input  className="formField" type="text" ref="Rfrom" placeholder="From" />
            <input className="formField" placeholder="To" type="text" ref="Rto"/>
            <input type="date" placeholder="Departure Date" ref="st_date" className="dateField" />
            <input type="time" placeholder="Departure Time"ref="st_time" className="dateField" style={{"width":"180px"}}/>
            <div style={{"display":"flex", "flexDirection":"row","marginLeft":"30px"}}>
            <div ><input type="date"  placeholder="Arrival Date"ref="e_date" className="dateField" /></div>
            <div ><input type="time" placeholder="Arrival Time" ref="e_time" className="dateField" style={{"width":"180px"}}/></div>
            <input type="submit" value="Find Trains" className="btn_train"/>
            </div>
        </form>
      </div>
    );
  }
}

export default FormFields;
