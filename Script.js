/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    
    // Here you would typically send the data to the server for validation
    console.log('Login Attempt:', { username, password });
    
    alert('Login functionality is not implemented yet.');
});

document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    const customerID = document.getElementById('customerID').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const name = document.getElementById('name').value;
    const address = document.getElementById('address').value;
    const nic = document.getElementById('nic').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const email = document.getElementById('email').value;
    
    // Here you would typically send the data to the server for registration
    console.log('Registration Attempt:', {
        customerID,
        username,
        password,
        name,
        address,
        nic,
        phoneNumber,
        email
    });
    
    alert('Registration functionality is not implemented yet.');
});


