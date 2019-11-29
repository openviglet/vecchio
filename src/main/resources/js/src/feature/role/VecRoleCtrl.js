vecchioApp.controller('VecRoleCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecRoleResource",
	function ($scope, $http, $state, $rootScope, vecRoleResource) {
		$rootScope.$state = $state;
		$scope.roles = vecRoleResource.query();
		$scope.roleDelete = function (roleId) {
			$http.delete("../api/role/" + roleId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/role/").then(
						function (response) {
							$scope.roles = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);