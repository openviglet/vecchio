vecchioApp.controller('VecMappingCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecMappingResource",
	function ($scope, $http, $state, $rootScope, vecMappingResource) {
		$rootScope.$state = $state;
		$scope.mappings = vecMappingResource.query();

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
