vecchioApp.controller('VecMappingEditCtrl', [
	"$scope",
	"$stateParams",
	"$state",
	"$rootScope",
	"vecMappingResource",
	"Notification",
	function ($scope, $stateParams, $state, $rootScope, vecMappingResource, Notification) {
		$rootScope.$state = $state;
		$scope.mappingId = $stateParams.mappingId;
		$scope.mapping = vecMappingResource.get({ id: $stateParams.mappingId });
		$scope.mappingSave = function () {
			$scope.mapping
				.$update(function () {
					Notification.warning('The Mapping was updated.');
					$state.go('mapping');
				});
		};
	}
]);