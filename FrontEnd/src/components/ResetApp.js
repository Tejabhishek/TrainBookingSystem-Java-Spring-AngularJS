import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Nav from './Nav';
import axios from 'axios'

class ResetApp extends Component{
  
  constructor(){
      super();
      this.state = {
       reset :false
      }
  }

    handleSubmit(e){
        var capacity = this.refs.capacity.value;
       
        axios.get('http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/ticket/reset',{
            params: {
                capacity:capacity
               
            }}).then((res)=>{
            this.state.reset=true;
            console.log("capacity has changed")
           
           
        })
e.preventDefault();
    }
  
    render(){


      return (
        <div className="ResetApp">
        <Nav />
        <form onSubmit={this.handleSubmit.bind(this)} style={{"marginTop":"80px","marginLeft":"400px"}}>
            <input type="text" ref="capacity" placeholder="Capacity"/>
            <button type="submit" className="btn btn-danger " >Reset Data</button>
        </form>

        
        </div>
      );
    }
  };
  
  export default ResetApp;