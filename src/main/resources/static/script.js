
const HOST = window.location.origin

const SEARCH_API = HOST + "/api/search"
const ERR_MSG_KEY = 'errorMessage'

const TITLE = 'title'
const URL = 'url'

$("#form").submit(function (e) {

    e.preventDefault()

    let query = $("#search").val()
        .trim()
        .replace(/[!"#$%&'()*+,-.\/:;<=>?@[\]^_`{|}~]/g, "")
        .replace(/\\/g, "")
        .replace(/\s{2,}/g, "");

    console.log("Query: " + query)

    if (query.length === 0) {
        alert("Empty query or query with only special characters detected. Please use words as query.");
        return;
    }

    let searchURL = SEARCH_API + "?query=" + query;

    const xhrQuery = new XMLHttpRequest();

    xhrQuery.open('GET', searchURL);
    xhrQuery.responseType = 'json';

    xhrQuery.onload = function(e) {

        if (this.status === 200) {

            document.getElementById("result").innerHTML = "";

            for(let pageIndex in this.response) {

                $("#result").append(`<a href="${this.response[pageIndex][URL]}" target="_blank">${this.response[pageIndex][TITLE]}</a> <p/>`)
            }
        }
        else if (this.status === 404 || this.status === 400) {
            alert(this.response[ERR_MSG_KEY])
        }
        else {
            console.log(this.status + " : " + this.response)
            alert("Encountered an unexpected issue. Please try again. Server status: " + this.status)
        }
    };
    xhrQuery.send();
});