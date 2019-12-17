vecchioApp.controller('VecAppCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecAppResource",
	"vecAppFactory",
	function ($scope, $state, $rootScope, vecAppResource, vecAppFactory) {
		$rootScope.$state = $state;
		$scope.apps = vecAppResource.query();
		$scope.appDelete = function (app) {
			vecAppFactory.deleteFromList(app, $scope.apps);
		}
	}]);