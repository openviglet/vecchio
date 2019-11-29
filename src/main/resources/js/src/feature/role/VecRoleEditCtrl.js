vecchioApp.controller('VecRoleEditCtrl', [
	"$scope",
	"$http",
	"$stateParams",
	"$state",
	"$rootScope",
	"vecRoleResource",
	function ($scope, $http, $stateParams, $state, $rootScope, vecRoleResource) {
		$rootScope.$state = $state;
		$scope.roleId = $stateParams.roleId;
		$scope.role = vecRoleResource.get({ id: $stateParams.roleId });

		$scope.roleSave = function () {
			$scope.roles = null;
			var parameter = JSON.stringify($scope.role);
			$http.put("../api/role/" + $scope.roleId,
				parameter).then(
				function (data, status, headers, config) {
					  $state.go('organization.role');
				}, function (data, status, headers, config) {
					  $state.go('organization.role');
				});
		}
	}
]);
