vecchioApp.controller('VecMappingCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecMappingResource",
	"vecMappingFactory",
	function ($scope, $state, $rootScope, vecMappingResource, vecMappingFactory) {
		$rootScope.$state = $state;
		$scope.mappings = vecMappingResource.query();

		$scope.mappingDelete = function (mapping) {
			vecMappingFactory.deleteFromList(mapping, $scope.mappings);
		}
	}]);
