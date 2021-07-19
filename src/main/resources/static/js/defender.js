var userId;

var app = angular.module("myApp", [ "ngRoute" ]);
app.config(function($routeProvider) {

	$routeProvider.when("/", {
		templateUrl : "Login.html",
		
	}).when("/agent", {
		templateUrl : "Agent.html",
		
	}).when("/table", {
		templateUrl : "table.html",
		
	}).when("/error", {
		templateUrl : "error.html",
		
	})
	

});
app.controller('MyCtrl2', function($scope, $http, $location) {
    $scope.fillTable = function() {
    	console.log("GGGG");
		$http({
			method : "GET",
			url : "http://localhost:8080/Defender/agent/",
		})
		.then(
				function mySuccess(response) {
					console.log("Admin got:"+JSON.stringify(response.data));
					var data = JSON.stringify(response.data);
					console.log("Admin got:"+JSON.stringify(response.data[0].userName));
					$scope.userName = response.data[0].userName;
					$scope.userEmail = response.data[0].userEmail;
					$scope.agentCode = response.data[0].agentCode;
					$scope.agentName = response.data[0].agentName;
					$scope.age = response.data[0].age;
					$scope.maritalStatus = response.data[0].maritalStatus;
					$scope.icNo = response.data[0].icNo;
					$scope.postCode = response.data[0].postCode;
					$scope.state = response.data[0].state;
					$scope.mobileNo = response.data[0].mobileNo;
					$scope.gender = response.data[0].gender;
					$scope.address1 = response.data[0].address1;
					$scope.address2 = response.data[0].address2;
					
					
				},
				function myError(response) {
					alert("error");
				});
    };
    $scope.fillTable();
});
	app.controller('myCtrl', function($scope, $http, $location) {
		$scope.Login = function(userName, email, pass) {
			$http({
						method : "POST",
						url : "http://localhost:8080/Defender/Admin/login",
						data : JSON.stringify({ "admin" : userName, "adminEmail" : email, "password" :pass })
					})
					.then(
							function mySuccess(response) {
								console.log("Admin:"+JSON.stringify(response));
								console.log(response.data.message);
								if(response.data.message == "valid login"){
									$scope.adminmessage = "valid";
									alert("Valid");
									$location.path("/table");
								}
								else{
									$scope.adminmessage = "invalid";
								}
							},
							function myError(response) {
								$scope.adminmessage = "invalid";
							});
		}
		
		$scope.createAdmin = function(userName, email, pass) {
			console.log(userName + email + pass);
			if(userName ){
				if(pass){
			if(email){
			$http({
				method : "POST",
				url : "http://localhost:8080/Defender/Admin/",
				data : ({ "admin" : userName, "adminEmail" : email, "password" :pass, "adminId" : 0 })
			})
			.then(
					function mySuccess(response) {
						console.log("Admin CREATED:"+response);
						alert("Welcome");
						$location.path("/table");
					},
					function myError(response) {
						$scope.adminmessage = "user not craeted";
					});
			}else{
				alert("PLEASE ENTER A VALID EMAIL");	
			}
			}else{
				alert("PLEASE ENTER SAME AND 8 DIGIT PASSWORD");		
			}
			}
			
			else{
				alert("PLEASE ENTER ALL DETAILS");	
			}
		}
		$scope.goToAgent = function() {
			$location.path("/agent");
		}
		
		$scope.getAgent = function() {
			console.log("GGGG");
			$http({
				method : "GET",
				url : "http://localhost:8080/Defender/agent/",
			})
			.then(
					function mySuccess(response) {
						console.log("Admin got:"+response);
						alert("Welcome");
					//	self.userName = 
					},
					function myError(response) {
						alert("error");
					});
		}
		
		$scope.error = function() {
			$http({
				method : "GET",
				url : "http://localhost:8080/Defender/Upload/error",
			})
			.then(
					function mySuccess(response) {
						console.log("Admin got:"+response);
						
			           $scope.isVisible =  true;
					},
					function myError(response) {
						//alert("error");
					});
		}
});