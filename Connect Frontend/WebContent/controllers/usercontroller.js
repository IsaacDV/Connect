/*
 * UsertController [Model to View]
 */

app.controller('UserCtrl',function($scope,UserService,$location,$rootScope,$cookieStore){
	
	if($rootScope!=undefined){
		UserService.getUser()
		.then(function(response){
			$scope.user=response.data
		},function(response){
			if(response.status==401){
				$location.path('/login')
			}
		})
	}
	
	
	$scope.registerUser=function(){
		console.log($scope.user)
		UserService.registerUser($scope.user)
		.then(function(response){
			$location.path('/login')
		},function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.error=response.data
		})
	}
	
	$scope.login=function(){
		UserService.login($scope.user)
		.then(function(response){
			$rootScope.currentUser=response.data
			$cookieStore.put('currentUser',response.data)
			$location.path('/home')
		},function(response){
			if(response.status==401){
				$scope.error=response.data
				$location.path('/login')
			}
		})
	}
	
	$scope.editUserProfile=function(){
		UserService.editUserProfile($scope.user)
		.then(function(response){
			alert('Updated Successfully')
			$location.path('/home')
		},function(response){
			if(response.status==401){
				$location.path('/login')
			}
			if(response.status==500){
				$scope.error=response.data
				$location.path('/editprofile')
			}
		})
	}
	/*
	$scope.deleteUser=function(id){
		UserService.deleteUser(id).then(function(response){
			$scope.users=response.data;
			console.log(response.status)
		},function(response){
			console.log(response.status)
			console.log(response.data)
			alert(response.data.message)
			$scope.error=response.data
		})
	}
	
	getAllUsers()*/
})