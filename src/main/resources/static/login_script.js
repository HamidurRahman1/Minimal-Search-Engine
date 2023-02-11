
const HOST = window.location.origin
const LOGIN_API = HOST + "/api/admin/login"
const ERR_MSG_KEY = 'errorMessage'

$("#login-form").submit(function (e) {

    e.preventDefault()

    let username = document.getElementById("username").value
        .toLowerCase()
        .trim()
        .replace(/[!"#$%&'()*+,-.\/:;<=>?@[\]^_`{|}~]/g, "")
        .replace(/\\/g, "")
        .replace(/\s{2,}/g, "");

    let password = document.getElementById("userpassword").value.trim();

    if (username.length === 0 || username.length < 5 || username.length > 30
        || password.length === 0 || password.length < 5 || password.length > 30) {
        alert("Empty strings or length requirement did not meet. Please use a valid username and a password");
    }
    else {

        const xhrAdminLogin = new XMLHttpRequest();

        xhrAdminLogin.open('POST', LOGIN_API);
        xhrAdminLogin.responseType = 'json';
        xhrAdminLogin.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        xhrAdminLogin.onload = function (e) {

            if (this.readyState === 4 && this.status === 200) {
                localStorage.setItem("adminFirstName", this.response['firstname']);
                localStorage.setItem("adminLastName", this.response['lastname']);
                localStorage.setItem("adminUsername", this.response['username']);

                window.setTimeout(() => window.location = "admin.html")
            }
            else if (this.status === 404 || this.status === 400 || this.status === 409) {
                alert(this.response[ERR_MSG_KEY])
            }
            else {
                console.log(this.status + " : " + this.response)
                alert("Encountered an unexpected issue. Please try again. Server status: " + this.status)
            }
        };

        const adminJson = {
            username: username,
            password: password
        }

        xhrAdminLogin.send(JSON.stringify(adminJson));
    }
});