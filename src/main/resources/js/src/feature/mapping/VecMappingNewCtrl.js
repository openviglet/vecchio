vecchioApp.controller('VecMappingNewCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.mapping = {};
		$scope.mappingSave = function () {
			var parameter = JSON.stringify($scope.mapping);
			$http.post("../api/mapping/",
				parameter).then(
				function (data, status, headers, config) {
					$state.go('mapping');
				}, function (data, status, headers, config) {
					$state.go('mapping');

				});
		}
	}
]);