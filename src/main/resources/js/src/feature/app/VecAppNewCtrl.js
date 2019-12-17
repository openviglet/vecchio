vecchioApp.controller('VecAppNewCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecAppResource",
	"Notification",
	function ($scope, $state, $rootScope, vecAppResource, Notification) {
		$rootScope.$state = $state;
		$scope.app = {};
		$scope.appSave = function () {
			delete $scope.app.id;
			vecAppResource.save($scope.app, function (response) {
				Notification.info('The ' + $scope.app.name + ' App was created.');
				$state.go('console.app');
			});
		}
	}
]);