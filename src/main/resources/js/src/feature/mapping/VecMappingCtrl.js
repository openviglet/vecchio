vecchioApp.controller('VecMappingCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecMappingResource",
	function ($scope, $http, $state, $rootScope, vecMappingResource) {
		$rootScope.$state = $state;
		$scope.mappings = vecMappingResource.query();

		$scope.mappingDelete = function (mappingId) {

			shFolderFactory.deleteFromList(shFolder, $scope.shFolders);
		}
	}]);
