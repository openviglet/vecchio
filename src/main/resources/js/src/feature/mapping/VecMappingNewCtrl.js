vecchioApp.controller('VecMappingNewCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"Notification",
	"vecMappingResource",
	function ($scope, $state, $rootScope, Notification, vecMappingResource) {
		$rootScope.$state = $state;
		$scope.mapping = {};
		$scope.mappingSave = function () {
			delete $scope.mapping.id;
			vecMappingResource.save($scope.mapping, function (response) {
				Notification.warning('The Mapping was created.');
				$state.go('mapping');
			});
		}
	}
]);