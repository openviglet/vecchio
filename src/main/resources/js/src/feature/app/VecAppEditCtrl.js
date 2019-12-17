vecchioApp.controller('VecAppEditCtrl', [
	"$scope",
	"$stateParams",
	"$state",
	"$rootScope",
	"vecAppResource",
	"Notification",
	function ($scope, $stateParams, $state, $rootScope, vecAppResource, Notification) {
		$rootScope.$state = $state;
		$scope.appId = $stateParams.appId;
		$scope.app = vecAppResource.get({ id: $stateParams.appId });
		$scope.appSave = function () {
			$scope.app
				.$update(function () {
					Notification.info('The ' + $scope.app.name + '  App was updated.');
				});
		}
	}
]);