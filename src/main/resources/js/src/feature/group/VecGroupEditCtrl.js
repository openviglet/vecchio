vecchioApp.controller('VecGroupEditCtrl', [
	"$scope",
	"$http",
	"$stateParams",
	"$state",
	"$rootScope",
	"vecGroupResource",
	function ($scope, $http, $stateParams, $state, $rootScope, vecGroupResource) {
		$rootScope.$state = $state;
		$scope.groupId = $stateParams.groupId;
		$scope.group = vecGroupResource.get({ id: $stateParams.groupId });

		$scope.groupSave = function () {
			$scope.groups = null;
			var parameter = JSON.stringify($scope.group);
			$http.put("../api/group/" + $scope.groupId,
				parameter).then(
				function (data, status, headers, config) {
					$state.go('organization.group');
				}, function (data, status, headers, config) {
					$state.go('organization.group');
				});
		}
	}
]);