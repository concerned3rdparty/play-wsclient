function MessageCtrl($scope, $http) {

  $http.get('/message').success(function(response) {
  		$scope.message = response;
  	}).error(function() {
  		$scope.error = 'Error loading message';
  	});

}