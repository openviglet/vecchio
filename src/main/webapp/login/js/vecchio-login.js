var vecchioLogin = angular.module('vecchioLogin', [ 'ui.router' ]);

vecchioLogin.config(function($locationProvider) {
	// use the HTML5 History API
	$locationProvider.html5Mode({
		enabled : true,
		requireBase : false
	});
});

vecchioLogin.controller('VecLoginCtrl', [ "$scope", "$http", "$window",
		"$location", function($scope, $http, $window, $location) {
			$scope.vecResponseType = $location.search().response_type;
			$scope.vecClientId = $location.search().client_id;
			$scope.vecRedirectUri = $location.search().redirect_uri;
			$scope.vecAction = '/api/login/?response_type=' + $scope.vecResponseType + '&client_id=' + $scope.vecClientId  + '&redirect_uri=' +   $scope.vecRedirectUri;
			console.log($location.search().token);
		} ]);
