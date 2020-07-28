var change = {
    name: document.getElementById("name"),
    content: document.getElementById("content")
};

var src = document.getElementById("Img").src;

if (change.name.value !== "") {
    var f4 = [true, true, true];
    document.getElementById("nameChecked").style.display = "inline-block";
    document.getElementById("contentChecked").style.display = "inline-block";
    document.getElementById("changeButton").disabled = false;
} else var f4 = [false, false, false];

change.name.onblur = function () {
    if (change.name.value !== "") {
        document.getElementById("nameNull").style.display = "none";
        document.getElementById("name").style.border = "1px solid #ccc";
        document.getElementById("nameChecked").style.display = "inline-block";
        f4[0] = true;
        var f3 = true;
        for (var i = 0; i <= 2; i++) if (!f4[i]) {
            f3 = false;
            break;
        }
        if (f3) document.getElementById("changeButton").disabled = false;
    } else {
        f4[0] = false;
        document.getElementById("changeButton").disabled = true;
        document.getElementById("nameNull").style.display = "inline-block";
        document.getElementById("name").style.border = "1px solid red";
        document.getElementById("nameChecked").style.display = "none";
    }
};

change.content.onblur = function () {
    if (change.content.value !== "") {
        document.getElementById("contentNull").style.display = "none";
        document.getElementById("content").style.border = "1px solid #ccc";
        document.getElementById("contentChecked").style.display = "inline-block";
        f4[1] = true;
        var f3 = true;
        for (var i = 0; i <= 2; i++) if (!f4[i]) {
            f3 = false;
            break;
        }
        if (f3) document.getElementById("changeButton").disabled = false;
    } else {
        f4[1] = false;
        document.getElementById("changeButton").disabled = true;
        document.getElementById("contentNull").style.display = "inline-block";
        document.getElementById("content").style.border = "1px solid red";
        document.getElementById("contentChecked").style.display = "none";
    }
};

function UploadImg(obj) {
    var file = obj.files[0];
    if (file !== undefined) {
        f4[2] = true;
        var f3 = true;
        for (var i = 0; i <= 2; i++) if (!f4[i]) {
            f3 = false;
            break;
        }
        if (f3) document.getElementById("changeButton").disabled = false;
        var reader = new FileReader();

        reader.onload = function (e) {
            var img = document.getElementById("Img");
            img.src = e.target.result;
            img.style.display = "block";
        };
        reader.readAsDataURL(file)
    } else {
        document.getElementById("Img").style.display = "none";
        f4[2] = false;
        document.getElementById("changeButton").disabled = true;
    }
}

document.getElementById("reset").onclick = function () {
    f4 = [false, false, false];
    document.getElementById("changeButton").disabled = true;
    document.getElementById("nameChecked").style.display = "none";
    document.getElementById("nameNull").style.display = "none";
    document.getElementById("contentNull").style.display = "none";
    document.getElementById("contentChecked").style.display = "none";
    document.getElementById("name").style.border = "1px solid #ccc";
    document.getElementById("content").style.border = "1px solid #ccc";
    if (document.getElementById("imageID").value !== "-1") {
        var f4 = [true, true, true];
        document.getElementById("nameChecked").style.display = "inline-block";
        document.getElementById("contentChecked").style.display = "inline-block";
        document.getElementById("changeButton").disabled = false;
    }
    if (src !== "") {
        document.getElementById("Img").src = src;
        document.getElementById("Img").style.display = "block";
    } else document.getElementById("Img").style.display = "none";
};

var xmlhttp;

document.getElementById("country").onchange = function () {
    var country = this.value;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = stateChanged;
    xmlhttp.open("GET", "cityOptions?country=" + country, true);
    xmlhttp.send();
}

function stateChanged() {
    if (xmlhttp.readyState == 4 || xmlhttp.readyState == "complete")
        document.getElementById("city").innerHTML = xmlhttp.responseText;
}

document.getElementById("changeButton").onclick = function () {
    if (window.confirm("请确认信息无误。")) document.getElementById("btn3").click();
};