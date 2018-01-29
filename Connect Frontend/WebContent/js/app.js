/*
 * Angular Module 
 */
var app=angular.module('app',['ngRoute','ngCookies'])
app.config(function($routeProvider){
	//alert('Entering Config')
	$routeProvider
	.when('/register',{
		templateUrl:'views/RegistrationForm.html',
		controller:'UserCtrl'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserCtrl'
	})
	.when('/editprofile',{
		templateUrl:'views/userprofile.html',
		controller:'UserCtrl'
	})
	.when('/addjob',{
		templateUrl:'views/jobform.html',
		controller:'JobController'
	})
	.when('/alljobs',{
		templateUrl:'views/jobslist.html',
		controller:'JobController'
	})
	.otherwise({templateUrl:'views/home.html'})
})

app.run(function($rootScope,$cookieStore,UserService,$location){
	//alert($cookieStore.get('currentUser'))
	if($rootScope.currentUser==undefined)
	{
		$rootScope.currentUser=$cookieStore.get('currentUser')
	}
		
		
	$rootScope.logout=function(){
		UserService.logout().then(function(response){
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser')
			$location.path('/login')
		},function(response){
			console.log(response.status)
			$location.path('/login')
		})
	}	
})