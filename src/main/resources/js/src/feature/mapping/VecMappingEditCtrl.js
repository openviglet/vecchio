vecchioApp.controller('VecMappingEditCtrl', [
	"$scope",
	"$http",
	"$stateParams",
	"$state",
	"$rootScope",
	function ($scope, $http, $stateParams, $state, $rootScope) {
		$rootScope.$state = $state;
		$scope.mappingId = $stateParams.mappingId;
		$scope.$evalAsync($http.get(
			"../api/mapping/" + $scope.mappingId).then(
			function (response) {
				$scope.mapping = response.data;
			}));
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