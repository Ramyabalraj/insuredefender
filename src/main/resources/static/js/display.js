




//variables
var myList;
var bId;
var uId;
var uName;
var uEmail;



//get all users        

function getUsers() {
    $.ajax({
        url: "http://localhost:8080/api/Users/",
        // dataType: 'json',
        success: function (results) {
            myList = results;
            console.log("data" + results);
            viewUsers();
        }
    })

}



//save or update user
function saveOrUpdateUser() {

    if (document.getElementById("submit").innerHTML == "Update") {
        var UserId = document.getElementById("userId").value;
        var UserName = document.getElementById("userName").value;
        var UserEmail = document.getElementById("userEmail").value;
        console.log("UserName" + UserName);
        var jsondata = {
            "userEmail": UserEmail,
            "userId": UserId,
            "userName": UserName
        };
        $.ajax({

            url: "http://localhost:8080/api/Users/" + bId,

            method: "PUT",
            data: JSON.stringify(jsondata),
            contentType: "application/json",
            // contentType: "json",
            success: function (data) {
                if(!data){
                alert("data invalid");
                }
                else{
                    console.log("data"+ data);
                    getUsers();
                }
            },
           error: function (data){
            alert("data is error" ); 
           }

        });
        btnName();

    }
    else {

        var UserId = document.getElementById("userId").value;
        var UserName = document.getElementById("userName").value;
        var UserEmail = document.getElementById("userEmail").value;
        console.log("UserName" + UserName);
        var jsondata = {
            "userEmail": UserEmail,
            "userId": UserId,
            "userName": UserName
        };
        $.ajax({
            url: "http://localhost:8080/api/Users/",

            method: "POST",
            data: JSON.stringify(jsondata),
            contentType: "application/json",
            // contentType: "json",
            success: function (data) {
                if(!data){
                alert("data not valid");
                }
                else{
                    console.log("data"+ data);
                    getUsers();
                }
            },
           error: function (data){
            alert("data error" ); 
           }
            


        });
    }
    clearUser();

}


//clear all data in input boxes
function clearUser() {
    document.getElementById("userId").value = "";

    document.getElementById("userName").value = "";
    document.getElementById("userEmail").value = "";
}


//Button innerHtml save 
function btnName() {
    document.getElementById("submit").innerHTML = "Save";
}




//view all users - tables      
function viewUsers() {
    var html = "<table border='1|1'>";
    html += "<th>ID</th>";
    html += "<th>User_Name</th>";
    html += "<th>User_Email</th>";
    html += "<th>Actions</th>";
    for (var i = 0; i < myList.length; i++) {
        html += "<tr>";
        html += "<td>" + myList[i].userId + "</td>";
        html += "<td>" + myList[i].userName + "</td>";
        html += "<td>" + myList[i].userEmail + "</td>";
        html += '<td><button onclick="editUser(' + myList[i].userId + ')">Edit</button></td>';
        html += '<td><button onclick="deleteUser(' + myList[i].userId + ')">Delete</button></td>';
        html += "</tr>";
    }
    html += "</table>";
    document.getElementById("box").innerHTML = html;
}




//edit user 
function editUser(id) {

    $.ajax({
        url: "http://localhost:8080/api/Users/" + id,
        // dataType: 'json',
        success: function (user) {
            console.log("data" + user.userName);
            uId = user.userId;
            uName = user.userName;
            uEmail = user.userEmail;
            console.log("user" + uEmail);

            document.getElementById("submit").innerHTML = "Update";
            document.getElementById("userId").value = uId;
            bId = uId;
            document.getElementById("userName").value = uName;
            document.getElementById("userEmail").value = uEmail;

        }
    })
}




//update user
function updateUser() {

    var jsondata = {
        "userEmail": UserEmail,
        "userId": 0,
        "userName": UserName
    };
    $.ajax({
        url: "http://localhost:8080/api/Users/",

        method: "POST",
        data: JSON.stringify(jsondata),
        contentType: "application/json",
        // contentType: "json",
        success: function (data) {
            console.log("data" + data);
            getUsers();

        },

    });

}



//delete user       
function deleteUser(id) {
    $.ajax({
        url: "http://localhost:8080/api/Users/" + id,
        method: "DELETE",
        success: function (data) {
            console.log("data" + data);
            getUsers();

        },

    });
}

