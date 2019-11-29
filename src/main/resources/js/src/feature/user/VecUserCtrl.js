vecchioApp.controller('VecUserCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecUserResource",
	"vecUserFactory",
	function ($scope, $state, $rootScope, vecUserResource, vecUserFactory) {
		$rootScope.$state = $state;
		$scope.users = null;
		$scope.users = vecUserResource.query();

		$scope.userDelete = function (vecUser) {
			vecUserFactory.delete(vecUser);
		}
	}]);
