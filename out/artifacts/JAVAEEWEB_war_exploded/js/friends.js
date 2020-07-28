var xmlhttp;

function stateChange(p) {
    xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "changeState?state=" + p, true);
    xmlhttp.send();
}