var vecchioApp = angular.module('vecchioApp', [ 'ui.router' ]);

vecchioApp.config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/home/config');
	$stateProvider.state('home', {
		url : '/home',
		templateUrl : 'home.html'
	}).state(
			'home.configuration',
			{
				url : '/config',
				templateUrl : 'configuration.html',
				controller : function($scope, $http, $window) {
					$scope.mappings = null;

					$scope.$evalAsync($http.get(
							"http://localhost:8080/api/mapping/").then(
							function(response) {
								$scope.mappings = response.data;
							}));
				}
			})

	.state('about', {
	// we'll get to this in a bit
	});

});

vecchioApp.controller('VecMappingCtrl', [
		"$scope",
		"$http",
		"$window",
		function($scope, $http, $window) {
			$scope.mappings = null;

			$scope.$evalAsync($http.get("http://localhost:8080/api/mapping/")
					.then(function(response) {
						$scope.mappings = response.data;
					}));
		} ]);
