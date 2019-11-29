vecchioApp.controller('VecGroupCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecGroupResource",
	"vecGroupFactory",
	function ($scope, $state, $rootScope, vecGroupResource, vecGroupFactory) {
		$rootScope.$state = $state;
		$scope.groups = null;
		$scope.groups = vecGroupResource.query();

		$scope.groupDelete = function (vecGroup) {
			vecGroupFactory.delete(vecGroup);
		}
	}]);
