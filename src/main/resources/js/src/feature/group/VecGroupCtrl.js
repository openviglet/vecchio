vecchioApp.controller('VecGroupCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecGroupResource",
	function ($scope, $http, $state, $rootScope, vecGroupResource) {
		$rootScope.$state = $state;
		$scope.groups = vecGroupResource.query();

		$scope.groupDelete = function (groupId) {
			$http.delete("../api/group/" + groupId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/group/").then(
						function (response) {
							$scope.groups = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);