vecchioApp.controller('VecRoleNewCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecRoleFactory",
	"vecAPIServerService",
	function($scope, $http, $state, $rootScope,
			vecRoleFactory, vecAPIServerService) {
		$rootScope.$state = $state;
		$scope.role = {};
		$scope.$evalAsync($http.get(
				vecAPIServerService.get().concat("/v2/role/model")).then(
				function(response) {
					$scope.role = response.data;
				}));
		$scope.roleSave = function() {
			vecRoleFactory.save($scope.role);
		}
	} ]);