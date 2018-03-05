const api = process.env.REACT_APP_CONTACTS_API_URL || 'http://localhost:9090';


const headers = {
    'Accept': 'application/json'
};

export const searchOneWay = (payload) =>
axios.get('/user?ID=12345')
.then(function (response) {
  console.log(response);
})
.catch(function (error) {
  console.log(error);
});

