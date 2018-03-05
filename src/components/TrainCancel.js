import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Nav from './Nav';
import axios from 'axios'
class TrainCancel extends Component{
    constructor(){
   
        super();
        this.state = {
        cancelled:null}
        
    }
   
    componentWillMount(){
      
    }

    handleSubmit(e){
        var t_name = this.refs.train_id.value;
        var t_date = this.refs.trainDate.value;
        axios.get('http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/train/cancel',{
            params: {
                trainName:t_name,
                tripDate:t_date
            }}).then((res)=>{
            
            console.log("train data")
            console.log(res);
            if(res.status===200){
                this.setState({cancelled:true});
            }
            else{
                this.setState({cancelled:false});
            }
        })
e.preventDefault();
    }
  
    render(){


      return (
        <div className="TrainCancel">
        <Nav />
        <form onSubmit={this.handleSubmit.bind(this)}>
        <input  className="formField"type="text" placeholder="Train Id" ref="train_id"/>
        <input  className="dateField"type="date" ref="trainDate"/>
        <input type="submit" value="Cancel Train" className="btn btn-danger" style={{"marginLeft":"10px"}}/>
        </form>
        <div class={(this.state.cancelled===true)?"alert alert-success":(this.state.cancelled !== null)?"alert alert-warning":""}>{(this.state.cancelled===true)?"Train has been cancelled and existing passengers have been rebooked to another train":(this.state.cancelled !== null)?"Train has already been cancelled":""}</div>
        </div>
      );
    }
  };
  
  export default TrainCancel;