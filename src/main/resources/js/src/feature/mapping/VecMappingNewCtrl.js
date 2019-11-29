vecchioApp.controller('VecMappingNewCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	function ($scope, $http, $state, $rootScope) {
		$rootScope.$state = $state;
		$scope.mapping = {};
		$scope.mappingSave = function () {
			var parameter = JSON.stringify($scope.mapping);
			$http.post("../api/v2/mapping/",
				parameter).then(
				function (data, status, headers, config) {
					$state.go('mapping');
				}, function (data, status, headers, config) {
					$state.go('mapping');

				});
		}
	}
]);