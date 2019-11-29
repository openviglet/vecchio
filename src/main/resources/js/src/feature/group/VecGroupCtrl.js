vecchioApp.controller('VecGroupCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	function ($scope, $http, $state, $rootScope) {
		$rootScope.$state = $state;
		$scope.groups = null;

		$scope.$evalAsync($http.get(
			"../api/group/").then(
			function (response) {
				$scope.groups = response.data;
			}));

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