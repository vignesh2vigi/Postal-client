/**
 * 
 */
app.controller('ClientController',function($scope,ClientService,$location,$rootScope,$cookieStore){
	
	$scope.login=function(){
		console.log($scope.client)
	ClientService.login($scope.client).then(function(response){
		
		console.log(response.data)
		console.log(response.status)
		$rootScope.currentClient=response.data//username
		$cookieStore.put('currentClient',response.data)
		$location.path('/home')
		
	},function(response){
		console.log(response.data)
		/*$scope.error=response.data.message*/
		console.log(response.data)
	console.log(response.status)
	$location.path('/login')
	})
	}
	/*$scope.onSubmit= function(valid){
		if(valid){
			console.log("ghghjh")
		}
		else{
			console.log("failure")
		}
		
	}*/
	function getlist(){
		ClientService.getlist().then(function(response){
             console.log(response.data)
			console.log(response.status)
			
			$scope.newlead=response.data
            },function(response){
			console.log(response.data)
			$location.path('/home')
		})
		
	}
	function getatelist(){
		ClientService.getatelist().then(function(response){
             console.log(response.data)
			console.log(response.status)
			
			$scope.ate=response.data
            },function(response){
			console.log(response.data)
			$location.path('/home')
		})
		
	}
	$scope.update=function(){
		ClientService.update($scope.client).then(function(response){
			alert('Updated successfully')
			$location.path('/home')
		},function(response){
			
			console.log(response.data)
				$location.path('/billingup')
				})
		}
	$scope.exportData = function () {
        var blob = new Blob([document.getElementById('exportable').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Excel.xls");
    };
	function complete(){
		ClientService.complete().then(function(response){
             console.log(response.data)
			console.log(response.status)
			
			$scope.complete=response.data
            },function(response){
			console.log(response.data)
			$location.path('/home')
		})
		
	}
	/*function bill(){
		console.log($scope.client)
		ClientService.bill().then(function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.client=response.data
			
			$location.path('/billup')
		},function(response){
			
			console.log(response.status)
			console.log(response.data)
				$location.path('/billup')
				})
		} 
	bill()*/
	getlist()
	getatelist()
	complete()
})