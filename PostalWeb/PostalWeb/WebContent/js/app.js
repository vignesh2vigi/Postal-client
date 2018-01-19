/**
 * 
 */
var app = angular.module("app", [ 'ngRoute','ngCookies'])
app.config(function($routeProvider) {

	$routeProvider
	.when("/login", {
        templateUrl : 'login.html',
        controller :'ClientController'	
        })
    .when("/home",{
    	 templateUrl : 'home.html',
    	 controller:'DashController'
    })
     .when("/getlist", {
        templateUrl : 'newlead.html',
        	controller:'ClientController'
    })
    .when("/getatelist", {
        templateUrl : 'allocated.html',
        	controller:'ClientController'
    })
    .when("/complete", {
        templateUrl : 'complete.html',
        	controller:'ClientController'
    })
    .when("/bill", {
        templateUrl : 'billing.html',
        	controller:'ClientController'
    })
     .when("/billup", {
        templateUrl : 'billingup.html',
        	controller:'ClientController'
    })
     
	
    .otherwise("/login",{templateurl:"login.html",controller :'ClientController'})
    
    
	
})
app.run(function($rootScope,$cookieStore,$location,ClientService){
	
		if($rootScope.currentClient==undefined){
			$rootScope.currentClient=$cookieStore.get('currentClient')
		}
		$rootScope.logout=function(){
		ClientService.logout().then(function(response){
			delete $rootScope.currentClient;
			$cookieStore.remove('currentClient')
			$location.path('/login')
			
		},function(response){
			console.log(response.status)
			
				delete $rootScope.currentUser;
				$cookieStore.remove('currentUser')
				$location.path('/login')

			
			$location.path('/login')
				
		})
	}

})