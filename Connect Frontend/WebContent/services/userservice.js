/**
 * UserService to make server side calls
 */
app.factory('UserService',function($http){
	
	var BASE_URL="http://localhost:8087/Connect"
	
	var userService={}
	
	userService.getAllUsers=function(){
		return $http.get(BASE_URL + "/getallusers")
	}
	
	userService.registerUser=function(user){
		console.log(user)
		return $http.post(BASE_URL + "/registeruser",user)
	}
	
	userService.login=function(user){
		console.log(user)
		return $http.post(BASE_URL + "/login",user)
	}
	
	userService.logout=function(){
		return $http.get(BASE_URL + "/logout")
	}
	
	userService.getUser=function(){
		return $http.get(BASE_URL + "/getuser")
	}
	
	userService.deleteUser=function(id){
		return $http['delete'](BASE_URL + "/deleteuser/"+id);
	}
	
	userService.editUserProfile=function(user){
		return $http.put(BASE_URL + "/edituserprofile",user)
	}
	
	return userService;
})