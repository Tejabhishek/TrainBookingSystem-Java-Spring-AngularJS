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
    height: '200px',
    width:'500px'
  }
};

class Ticket extends React.Component {
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
    console.log(bookingData);
   
    return (
      <div>
        <button  className="btn btn-success"  onClick={this.openModal}>View Ticket</button>
        <Modal
          isOpen={this.state.modalIsOpen}
          onAfterOpen={this.afterOpenModal}
          onRequestClose={this.closeModal}
          style={customStyles}
          contentLabel="Example Modal"
        >

          <h2 ref={subtitle => this.subtitle = subtitle}></h2>
         <div style={{"float":"right"}}> <button onClick={this.closeModal}>X</button></div>
         <div>
            <label>Ticket ID: </label> {ticketId}<br/>
            <label>Booking Date: </label>{bookingDate}<br/>
             Thanks for Booking the ticket!! The ticket has been emailed to your email account.
        </div> 
         
        </Modal>
      </div>
    );
  

  }
}
export default Ticket;