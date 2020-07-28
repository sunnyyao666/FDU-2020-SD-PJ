var xmlhttp;

function page(p) {
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = stateChanged;
    xmlhttp.open("GET", "myPicturesPages?page=" + p, true);
    xmlhttp.send();
}

function stateChanged() {
    if (xmlhttp.readyState == 4 || xmlhttp.readyState == "complete")
        document.getElementById("content").innerHTML = xmlhttp.responseText
}