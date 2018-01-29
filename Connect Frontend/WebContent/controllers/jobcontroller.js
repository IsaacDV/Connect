/**
 * JobController
 */
app.controller('JobController',function($scope,JobService,$location){
	
	$scope.addJob=function(){
		JobService.addJob($scope.job)
		.then(function(response){
			$scope.job={}
			alert('Job Details Posted Sucessfully')
			$location.path('/alljobs')
			
		},function(response){
			if(response.status==401){
				if(response.dat.code==6){
					alert('Access Denied')
					$location.path('/home')
				}
				else{
					$scope.error=response.data
					$location.path('/login')
				}
			}
			if(response.status==500){
				$scope.error=response.data
				$location.path('/addjob')
			}
			
		})
	}
	
	function getAllJobs(){
		JobService.getAllJobs()
		.then(function(response){
			$scope.jobs=response.data
		},function(response){
			if(response.status==401){
				$scope.error=response.data
				$location.path('/login')
			}
		})
	}
	
	getAllJobs()

	/*
	JobService.getJob(jid)
	.then(function(response){
		$scope.job=response.data
		console.log(response.data)
	},function(response){
		console.log(response.status)
	})*/
})