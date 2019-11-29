vecchioApp.controller('VecRoleEditCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecRoleResource",
	"$stateParams",
	"Notification",
	function ($scope, $state, $rootScope,
		vecRoleResource, $stateParams, Notification) {
		$rootScope.$state = $state;
		$scope.roleId = $stateParams.roleId;

		$scope.role = vecRoleResource.get({
			id: $scope.roleId
		});

		$scope.roleSave = function () {
			$scope.role.$update(function () {
				Notification.warning('The ' + $scope.role.name + ' Role was updated.');
			});
		}
	}]);