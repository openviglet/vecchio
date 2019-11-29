vecchioApp.controller('VecRoleCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecRoleResource",
	"vecRoleFactory",
	function ($scope, $state, $rootScope, vecRoleResource, vecRoleFactory) {
		$rootScope.$state = $state;
		$scope.roles = null;
		$scope.roles = vecRoleResource.query();

		$scope.roleDelete = function (vecRole) {
			vecRoleFactory.delete(vecRole);
		}
	}]);
