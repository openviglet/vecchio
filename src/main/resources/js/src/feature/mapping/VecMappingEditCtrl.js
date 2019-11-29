vecchioApp.controller('VecMappingEditCtrl', [
	"$scope",
	"$http",
	"$stateParams",
	"$state",
	"$rootScope",
	"vecMappingResource",
	function ($scope, $http, $stateParams, $state, $rootScope, vecMappingResource) {
		$rootScope.$state = $state;
		$scope.mappingId = $stateParams.mappingId;
		$scope.mapping = vecMappingResource.get({ id: $stateParams.mappingId });
		$scope.mappingSave = function () {
			$scope.mappings = null;
			var parameter = JSON.stringify($scope.mapping);
			$http.put("../api/mapping/" + $scope.mappingId,
				parameter).then(
				function (data, status, headers, config) {
					   $state.go('mapping');
				}, function (data, status, headers, config) {
					   $state.go('mapping');
				});
		}
	}
]);