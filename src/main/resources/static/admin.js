
$("#crawl-form").submit(function (e) {

    e.preventDefault()

    let enteredURL = $("#url-search").val().trim();

    if (enteredURL.length === 0) {
        alert("Empty string detected. Please use enter a valid URL.");
        return;
    }

    console.log("URL: " + enteredURL)

    document.getElementById("crawl-response").innerHTML = "";

    let adminFirstName = localStorage.getItem("adminFirstName")
    let adminLastName = localStorage.getItem("adminLastName")
    let adminUsername = localStorage.getItem("adminUsername")

    if (adminFirstName == null || adminLastName == null || adminUsername == null) {
        alert("Please login before trying to perform an administrative task. Redirecting to login ... ")
        window.location.href = window.location.origin + "/login.html";
        return;
    }

    const crawlURL = window.location.origin + "/api/admin/" + adminUsername + "/crawl?url=" + enteredURL

    const xhrCrawl = new XMLHttpRequest();

    xhrCrawl.open('GET', crawlURL);
    xhrCrawl.responseType = 'text';

    xhrCrawl.onload = function(e) {

        if (this.status === 200) {
            $("#crawl-response").append(`<p>${this.response}</p>`)
        }
        else if (this.status === 404 || this.status === 409 || this.status === 400) {
            alert(JSON.parse(this.response)[ERR_MSG_KEY])
        }
        else {
            console.log(this.status + " : " + this.response)
            alert("Encountered an unexpected issue. Please try again. Server status: " + this.status)
        }
    };

    xhrCrawl.send();
});

function adminLogout() {

    localStorage.removeItem("adminFirstName")
    localStorage.removeItem("adminLastName")
    localStorage.removeItem("adminUsername")

    window.location.href = window.location.origin
}
