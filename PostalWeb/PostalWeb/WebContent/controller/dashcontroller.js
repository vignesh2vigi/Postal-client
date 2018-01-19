/**
 * 
 */

app.controller('DashController',function($scope,DashService,$location){

    function newlead (){
		console.log($scope.client)
		DashService.newlead().then(function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.client=response.data
			
			$location.path('/home')
		},function(response){
			
			console.log(response.status)
			console.log(response.data)
				$location.path('/home')
				})
		}
    function leadprocess (){
		console.log($scope.client1)
		DashService.leadprocess().then(function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.client1=response.data
			
			$location.path('/home')
		},function(response){
			
			console.log(response.status)
			console.log(response.data)
				$location.path('/home')
				})
		}
    function leadnotverify (){
		console.log($scope.client2)
		DashService.leadnotverify().then(function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.client2=response.data
			
			$location.path('/home')
		},function(response){
			
			console.log(response.status)
			console.log(response.data)
				$location.path('/home')
				})
		}
    function leadverify (){
		console.log($scope.client3)
		DashService.leadverify().then(function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.client3=response.data
			
			$location.path('/home')
		},function(response){
			
			console.log(response.status)
			console.log(response.data)
				$location.path('/home')
				})
		}
    newlead ()
     leadprocess ()
      leadnotverify ()
       leadverify ()
})