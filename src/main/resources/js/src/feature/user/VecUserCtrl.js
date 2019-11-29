vecchioApp.controller('VecUserCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.users = null;

		$scope.$evalAsync($http.get(
			"../api/user/").then(
			function (response) {
				$scope.users = response.data;
			}));

		$scope.userDelete = function (userId) {
			$http.delete("../api/user/" + userId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/user/").then(
						function (response) {
							$scope.users = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);