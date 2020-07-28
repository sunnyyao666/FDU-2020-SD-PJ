var register = {
    username: document.getElementById("username2"),
    password: document.getElementById("password2"),
    repassword: document.getElementById("repassword"),
    email: document.getElementById("email"),
};

var f2 = [false, false, false, false];

function reset() {
    document.getElementById("registerButton").disabled = true;
    if (register.username.value !== "") validateUsername();
    else {
        f2[0] = false;
        document.getElementById("username2Wrong").style.display = "none";
        document.getElementById("username2Null").style.display = "none";
        document.getElementById("username2").style.border = "1px solid #ccc";
        document.getElementById("username2Checked").style.display = "none";
    }
    document.getElementById("password-check").style.display = "none";
    document.getElementById("password2Wrong").style.display = "none";
    document.getElementById("password2Null").style.display = "none";
    document.getElementById("password2").style.border = "1px solid #ccc";
    document.getElementById("password2Checked").style.display = "none";
    document.getElementById("lv1").style.borderTopColor = "darkgrey";
    document.getElementById("lv2").style.borderTopColor = "darkgrey";
    document.getElementById("lv3").style.borderTopColor = "darkgrey";
    document.getElementById("repasswordNull").style.display = "none";
    document.getElementById("repassword").style.border = "1px solid #ccc";
    document.getElementById("repasswordChecked").style.display = "none";
    document.getElementById("repasswordWrong").style.display = "none";
    if (register.email.value !== "") validateEmail();
    else {
        f2[3] = false;
        document.getElementById("emailWrong").style.display = "none";
        document.getElementById("emailNull").style.display = "none";
        document.getElementById("email").style.border = "1px solid #ccc";
        document.getElementById("emailChecked").style.display = "none";
    }
    resetPIN2();
}

register.username.onblur = function () {
    if (register.username.value !== "") validateUsername();
    else {
        f2[0] = false;
        document.getElementById("registerButton").disabled = true;
        document.getElementById("username2Wrong").style.display = "none";
        document.getElementById("username2Null").style.display = "inline-block";
        document.getElementById("username2").style.border = "1px solid red";
        document.getElementById("username2Checked").style.display = "none";
    }
}

function validateUsername() {
    document.getElementById("username2Null").style.display = "none";
    document.getElementById("username2").style.border = "1px solid #ccc";
    document.getElementById("username2Checked").style.display = "none";
    if (/^[A-Za-z0-9_]{4,15}$/.test(register.username.value)) {
        f2[0] = true;
        var f3 = true;
        for (var i = 0; i <= 3; i++) if (!f2[i]) {
            f3 = false;
            break;
        }
        if (f3) document.getElementById("registerButton").disabled = false;
        document.getElementById("username2Wrong").style.display = "none";
        document.getElementById("username2").style.border = "1px solid #ccc";
        document.getElementById("username2Checked").style.display = "inline-block";
    } else {
        f2[0] = false;
        document.getElementById("registerButton").disabled = true;
        document.getElementById("username2Wrong").style.display = "inline-block";
        document.getElementById("username2").style.border = "1px solid red";
        document.getElementById("username2Checked").style.display = "none";
    }
}


function validatePassword() {
    document.getElementById("password-check").style.display = "inline-block";
    document.getElementById("password2Null").style.display = "none";
    document.getElementById("password2").style.border = "1px solid #ccc";
    document.getElementById("password2Checked").style.display = "none";
    if (/^[A-Za-z0-9_]{6,12}$/.test(register.password.value)) {
        f2[1] = true;
        var f3 = true;
        for (var i = 0; i <= 3; i++) if (!f2[i]) {
            f3 = false;
            break;
        }
        if (f3) document.getElementById("registerButton").disabled = false;
        document.getElementById("password2Wrong").style.display = "none";
        document.getElementById("password2").style.border = "1px solid #ccc";
        document.getElementById("password2Checked").style.display = "inline-block";
    } else {
        f2[1] = false;
        document.getElementById("registerButton").disabled = true;
        document.getElementById("password2Wrong").style.display = "inline-block";
        document.getElementById("password2").style.border = "1px solid red";
        document.getElementById("password2Checked").style.display = "none";
    }
}

register.password.onblur = register.password.onkeyup = function () {
    judgePassword();
    if (register.password.value !== "") validatePassword();
    else {
        f2[1] = false;
        document.getElementById("registerButton").disabled = true;
        document.getElementById("password2Null").style.display = "inline-block";
        document.getElementById("password2").style.border = "1px solid red";
        document.getElementById("password2Wrong").style.display = "none";
        document.getElementById("password2Checked").style.display = "none";
    }
};

function judgePassword() {
    if (register.password.value !== "") {
        document.getElementById("password-check").style.display = "inline-block";
        document.getElementById("lv1").style.borderTopColor = "darkgrey";
        document.getElementById("lv2").style.borderTopColor = "darkgrey";
        document.getElementById("lv3").style.borderTopColor = "darkgrey";
        if ((/^[A-Za-z]{6,12}$/.test(register.password.value)) || (/^[0-9]{6,12}$/.test(register.password.value))) {
            document.getElementById("lv1").style.borderTopColor = "red";
        } else if (/^[A-Za-z0-9]{6,12}$/.test(register.password.value)) {
            document.getElementById("lv1").style.borderTopColor = "red";
            document.getElementById("lv2").style.borderTopColor = "orange";
        } else if (/^[A-Za-z0-9_]{6,12}$/.test(register.password.value)) {
            document.getElementById("lv1").style.borderTopColor = "red";
            document.getElementById("lv2").style.borderTopColor = "orange";
            document.getElementById("lv3").style.borderTopColor = "green";
        } else {
            document.getElementById("lv1").style.borderTopColor = "darkgrey";
            document.getElementById("lv2").style.borderTopColor = "darkgrey";
            document.getElementById("lv3").style.borderTopColor = "darkgrey";
        }
    } else {
        document.getElementById("password-check").style.display = "none";
    }
}

register.repassword.onblur = function () {
    if (register.repassword.value !== "") validateRepassword();
    else {
        f2[2] = false;
        document.getElementById("registerButton").disabled = true;
        document.getElementById("repasswordNull").style.display = "inline-block";
        document.getElementById("repassword").style.border = "1px solid red";
        document.getElementById("repasswordWrong").style.display = "none";
        document.getElementById("repasswordChecked").style.display = "none";
    }
}

function validateRepassword() {
    document.getElementById("repasswordNull").style.display = "none";
    document.getElementById("repassword").style.border = "1px solid #ccc";
    document.getElementById("repasswordChecked").style.display = "none";
    if (register.repassword.value === register.password.value) {
        f2[2] = true;
        var f3 = true;
        for (var i = 0; i <= 3; i++) if (!f2[i]) {
            f3 = false;
            break;
        }
        if (f3) document.getElementById("registerButton").disabled = false;
        document.getElementById("repasswordWrong").style.display = "none";
        document.getElementById("repassword").style.border = "1px solid #ccc";
        document.getElementById("repasswordChecked").style.display = "inline-block";
    } else {
        f2[2] = false;
        document.getElementById("registerButton").disabled = true;
        document.getElementById("repasswordWrong").style.display = "inline-block";
        document.getElementById("repassword").style.border = "1px solid red";
        document.getElementById("repasswordChecked").style.display = "none";
    }
}

register.email.onblur = function () {
    if (register.email.value !== "") validateEmail();
    else {
        f2[3] = false;
        document.getElementById("registerButton").disabled = true;
        document.getElementById("emailWrong").style.display = "none";
        document.getElementById("emailNull").style.display = "inline-block";
        document.getElementById("email").style.border = "1px solid red";
        document.getElementById("emailChecked").style.display = "none";
    }
}

function validateEmail() {
    document.getElementById("emailNull").style.display = "none";
    document.getElementById("email").style.border = "1px solid #ccc";
    document.getElementById("emailChecked").style.display = "none";
    if (/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/.test(register.email.value)) {
        f2[3] = true;
        var f3 = true;
        for (var i = 0; i <= 3; i++) if (!f2[i]) {
            f3 = false;
            break;
        }
        if (f3) document.getElementById("registerButton").disabled = false;
        document.getElementById("emailWrong").style.display = "none";
        document.getElementById("email").style.border = "1px solid #ccc";
        document.getElementById("emailChecked").style.display = "inline-block";
    } else {
        f2[3] = false;
        document.getElementById("registerButton").disabled = true;
        document.getElementById("emailWrong").style.display = "inline-block";
        document.getElementById("email").style.border = "1px solid red";
        document.getElementById("emailChecked").style.display = "none";
    }

}

document.getElementById("registerButton").onclick = function () {
    document.getElementById("btn2").click();
};

var login = {
    id: document.getElementById("username1"),
    password: document.getElementById("password1")
};

document.getElementById("loginButton").onclick = function () {
    if (login.id.value === "") {
        alert("请输入用户名/邮箱！");
        login.id.focus();
    } else if (login.password.value === "") {
        alert("请输入密码！");
        login.password.focus();
    } else document.getElementById("btn1").click();
};

function resetPIN1() {
    var str = document.getElementById("PINImage1").src;
    var length = str.indexOf("?");
    if (length > 0) str = str.substring(0, length);
    document.getElementById("PINImage1").src = str + "?abc=" + Math.random();
}

function resetPIN2() {
    var str = document.getElementById("PINImage2").src;
    var length = str.indexOf("?");
    if (length > 0) str = str.substring(0, length);
    document.getElementById("PINImage2").src = str + "?abc=" + Math.random();
}