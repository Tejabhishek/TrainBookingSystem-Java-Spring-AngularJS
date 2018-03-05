import React from 'react';
import ReactDOM from 'react-dom';
import Modal from 'react-modal';
import ls from 'local-storage';
const customStyles = {
  content : {
    top                   : '50%',
    left                  : '50%',
    right                 : 'auto',
    bottom                : 'auto',
    marginRight           : '-50%',
    transform             : 'translate(-50%, -50%)',
    height: '500px',
    width:'900px'
  }
};

class BookingItem extends React.Component {
  constructor() {
    super();

    this.state = {
      modalIsOpen: false
    };

    this.openModal = this.openModal.bind(this);
    this.afterOpenModal = this.afterOpenModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  openModal() {
    this.setState({modalIsOpen: true});
  }

  afterOpenModal() {
    // references are now sync'd and can be accessed.
    this.subtitle.style.color = '#f00';
  }

  closeModal() {
    this.setState({modalIsOpen: false});
  }

  render() {
    var bookingData = ls.get("Booking_Data");
    var ticketId = ((bookingData)===null)? "None":bookingData.ticket_id ;
    var bookingDate = ((bookingData)===null)? "None":bookingData.booking_date ;
    console.log("hey");
   var bookingData = this.props.bookingDetail;
    console.log(bookingData);
   
    return (
      <div>
        <button  className="btn btn-secondary"  onClick={this.openModal}>View Ticket</button>
        <Modal
          isOpen={this.state.modalIsOpen}
          onAfterOpen={this.afterOpenModal}
          onRequestClose={this.closeModal}
          style={customStyles}
          contentLabel="Example Modal"
        >

          <h2 ref={subtitle => this.subtitle = subtitle}></h2>
         <div style={{"float":"right"}}> <button onClick={this.closeModal}>X</button></div>
         <table className="table" >
            <thead>
                    <tr>
                    <th>#</th>
                    <th>Train Number</th>
                    <th>Departure Station</th>
                    <th> Deparuture Time</th>
                    <th> Arrival Station</th>
                    <th>Arrival Time</th>
                    <th>Total Time</th>
                   <th> Passengers</th>
                    </tr>
            </thead>
            <tbody>
            <tr>
                  <th scope="row">{bookingData.id}</th>
                  <td>{bookingData.oneWayTrips[0].name}</td>
                  <td>{bookingData.oneWayTrips[0].depStation}</td>
                  <td>{bookingData.oneWayTrips[0].departureTime}</td>
                  <td>{bookingData.oneWayTrips[bookingData.oneWayTrips.length-1].arrStation}</td>
                  <td>{bookingData.oneWayTrips[bookingData.oneWayTrips.length-1].arrivalTime}</td>
                  <td>{bookingData.oneWayTripTime}</td>
                  <td>{bookingData.passengerNames}</td>
              </tr>
              
            </tbody>
            </table>


            <table className="table" style={{"display":(bookingData.returnTrips.length>0)?"block":"none"}} >
            <thead>
                    <tr>
                    <th>#</th>
                    <th>Train Number</th>
                    <th>Departure Station</th>
                    <th> Deparuture Time</th>
                    <th> Arrival Station</th>
                    <th>Arrival Time</th>
                    <th>Total Time</th>
                   <th> Passengers</th>
                    
                    </tr>
            </thead>
            <tbody>
              <tr>
                  <th scope="row">{bookingData.id}</th>
                  <td>{(bookingData.returnTrips.length!==0)?bookingData.returnTrips[0].name:""}</td>
                  <td>{(bookingData.returnTrips.length!==0)?bookingData.returnTrips[0].depStation:""}</td>
                  <td>{(bookingData.returnTrips.length!==0)?bookingData.returnTrips[0].departureTime:""}</td>
                  <td>{(bookingData.returnTrips.length!==0)?bookingData.returnTrips[bookingData.returnTrips.length-1].arrStation:""}</td>
                  <td>{(bookingData.returnTrips.length!==0)?bookingData.returnTrips[bookingData.returnTrips.length-1].arrivalTime:""}</td>
                  <td>{(bookingData.returnTrips.length!==0)?bookingData.returnTripTime:""}</td>
                  <td>{bookingData.passengerNames}</td>
                  </tr>
            </tbody>
            </table>
         
        </Modal>
      </div>
    );
  

  }
}
export default BookingItem;