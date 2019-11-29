vecchioApp.controller('VecMappingCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$scope.mappings = null;
		$rootScope.$state = $state;
		$scope.$evalAsync($http.get(
			"../api/mapping/").then(
			function (response) {
				$scope.mappings = response.data;
			}));

		$scope.mappingDelete = function (mappingId) {
			$http.delete("../api/mapping/" + mappingId).then(
				function (data, status, headers, config) {
					$http.get(
							"../api/mapping/").then(
						function (response) {
							$scope.mappings = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);
