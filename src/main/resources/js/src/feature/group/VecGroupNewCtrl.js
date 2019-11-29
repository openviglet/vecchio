vecchioApp.controller('VecGroupNewCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	function ($scope, $http, $state, $rootScope) {
		$rootScope.$state = $state;
		$scope.group = {};
		$scope.groupSave = function () {
			var parameter = JSON.stringify($scope.group);
			$http.post("../api/group/",
				parameter).then(
				function (data, status, headers, config) {
					$state.go('organization.group');
				}, function (data, status, headers, config) {
					$state.go('organization.group');
				});
		}
	}
]);
