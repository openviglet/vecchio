vecchioApp.controller('VecGroupNewCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecGroupFactory",
	"vecAPIServerService",
	function($scope, $http, $state, $rootScope,
			vecGroupFactory, vecAPIServerService) {
		$rootScope.$state = $state;
		$scope.group = {};
		$scope.$evalAsync($http.get(
				vecAPIServerService.get().concat("/v2/group/model")).then(
				function(response) {
					$scope.group = response.data;
				}));
		$scope.groupSave = function() {
			vecGroupFactory.save($scope.group);
		}
	} ]);