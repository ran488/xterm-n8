var logApp = angular.module('logApp', []);

logApp.controller('logAppController', function($scope, $http) {
	$http.get('api/loglist/').success(function(data) {
		$scope.logentries = data;
	});
	
	$scope.search = function() {
		$http({
			url:'api/loglist/' ,
			method: "GET",
			params: { bodid: $scope.bodid, 
				country: $scope.country, 
				first: $scope.first, 
				last: $scope.last
			}
		}).success(function(data) {
					$scope.logentries = data;
		});		
	}
	
});


