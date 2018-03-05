import React, { Component } from 'react';
import Nav from './Nav';
import ls from 'local-storage';
import { Redirect } from 'react-router'
import BookingItem from './BookingItem';
import axios from 'axios';
import '../App.css';
var Link = require('react-router-dom').Link;


 
  class Bookings extends React.Component {
 
    constructor(){
   
        super();
        this.state = {
            myBookings:[]
        }
    }

    componentWillMount(){
        //var email = ls.get('loginData').email;
        var email = ls.get('emailId');
        axios.get('http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/ticket/',{
            params: {
                userName:email
            }}).then((res)=>{
                this.setState({myBookings:res.data})
            console.log("trainBookings")
            console.log(res);
        })
    }
    handleCancel(ticketId){
        console.log('inside cancel');
        console.log(ticketId);
        axios.post('http://ec2-54-193-54-75.us-west-1.compute.amazonaws.com:9090/ticket/'+ticketId+'/cancel').then((res)=>{
        var changeState = this.state.myBookings;
        let foundTicket = changeState.findIndex((k)=> {
            if(k.id === ticketId){
               k.cancelled = true;
            }
           });
           this.setState({myBookings:changeState});
              console.log(res);
        });
    }

    render() {
        var elem = this.state.myBookings.map((booking)=>{
            console.log(booking.cancelled);
           
            return(
                <tr>
                  
                    <th scope="row">{booking.id}</th>
                    <td><BookingItem bookingDetail={booking}/></td>
                    <td>{booking.bookingDate}</td>
                    <td>{booking.depDate}</td>
                    <td>{booking.arrDate}</td>
                    <td>{booking.totalTripPrice}</td>
                    <td>{(booking.cancelled!==true)?"Booked":"Cancelled"}</td>
                    <td><button onClick={this.handleCancel.bind(this,booking.id)} className={(booking.cancelled===true)?"btn btn-success":"btn btn-danger"} >{(booking.cancelled===true)?"Rebook":"Cancel"}</button></td>
                </tr>
            )
        });
      return (
        <div>
          <Nav />
          <input  className="roundFormField" type="text" ref="ticketId" placeholder="TicketId" />
            
            <table className="table" >
            <thead>
                    <tr>
                    
                    <th>#</th>
                    <th>View Bookings</th>
                    <th>Booking Date</th>
                    <th> Deparuture Details</th>
                    <th> Arrival Details</th>
                    <th>Total Price</th>
                    <th>Status</th>
                    <th>Cancel</th>
                    </tr>
            </thead>
            <tbody>
                {elem}
            </tbody>
            </table>
            
        </div>
      )
    }
  }
 

  export default Bookings;
