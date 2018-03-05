import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Modal from 'react-modal';

class Counter extends Component{

    constructor(){
        super();
        this.state = {
            count:1
        }}
    incrementCount(e){
        if(this.state.count +1 < 6 ){
      this.setState({
        count: this.state.count + 1

      }
    
    
    );

    this.props.countVal(this.state.count + 1);  
    this.setArray(this.state.count + 1);
    }
    
      e.preventDefault();
    }


    setArray(val){
        var vals = [];
        for(var i = 0; i < val; i++ ){
             
           vals.push("key"+i.toString)
                
        }
this.props.countArray(vals);
       
    }
    decrementCount(e){
        if(this.state.count -1 >0 ){
            this.setState({
                count: this.state.count - 1
              }
            
            );
            this.props.countVal(this.state.count -1);  
            this.setArray(this.state.count - 1);
        }
        
      e.preventDefault();
    }
    setCountVal(val){
        
       
    }
    getInitialState(){
       return {
         count: 1
       }
    }
    componentDidMount(){
        this.props.countVal(this.state.count);
    }
    render(){
      return (
        <div className="counter">
          <h1>{this.state.count}</h1>
          <button className="btn" onClick={this.incrementCount.bind(this)}><h2>+</h2></button>
          <button className="btn" onClick={this.decrementCount.bind(this)}><h2>-</h2></button>
        </div>
      );
    }
  };
  
  export default Counter;