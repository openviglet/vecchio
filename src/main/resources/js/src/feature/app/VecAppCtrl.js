vecchioApp.controller('VecAppCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.apps = null;

		$scope.$evalAsync($http.get(
			"../api/app/").then(
			function (response) {
				$scope.apps = response.data;
			}));

		$scope.appDelete = function (appId) {
			$http.delete("../api/app/" + appId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/app/").then(
						function (response) {
							$scope.apps = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);