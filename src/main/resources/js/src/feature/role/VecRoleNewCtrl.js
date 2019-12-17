vecchioApp.controller('VecRoleNewCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecRoleFactory",
	"vecAPIServerService",
	"vecRoleFactory",
	function ($scope, $http, $state, $rootScope,
		vecRoleFactory, vecAPIServerService, vecRoleFactory) {
		$rootScope.$state = $state;
		$scope.role = {};
		$scope.$evalAsync($http.get(
			vecAPIServerService.get().concat("/v2/role/model")).then(
				function (response) {
					$scope.role = response.data;
				}));
		$scope.roleSave = function () {
			vecRoleFactory.save($scope.role);
		}

		$scope.addGroups = function () {
			vecRoleFactory.addGroups($scope.role);
		}

		$scope.removeGroup = function (index) {
			$scope.role.vecGroups.splice(index, 1);
		}
	}]);