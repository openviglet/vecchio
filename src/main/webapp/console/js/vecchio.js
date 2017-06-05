var vecchioApp = angular.module('vecchioApp', []);

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
