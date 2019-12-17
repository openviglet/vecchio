vecchioApp.controller('VecRoleEditCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecRoleResource",
	"$stateParams",
	"Notification",
	"vecRoleFactory",
	function ($scope, $state, $rootScope,
		vecRoleResource, $stateParams, Notification, vecRoleFactory) {
		$rootScope.$state = $state;
		$scope.roleId = $stateParams.roleId;

		$scope.role = vecRoleResource.get({
			id: $scope.roleId
		});

		$scope.roleSave = function () {
			$scope.role.$update(function () {
				Notification.info('The ' + $scope.role.name + ' Role was updated.');
			});
		}

		$scope.addGroups = function () {
			vecRoleFactory.addGroups($scope.role);
		}

		$scope.removeGroup = function (index) {
			$scope.role.vecGroups.splice(index, 1);
		}
	}]);