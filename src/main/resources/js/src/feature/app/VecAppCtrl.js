vecchioApp.controller('VecAppCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecAppResource",
	function ($scope, $http, $state, $rootScope, vecAppResource) {
		$rootScope.$state = $state;
		$scope.apps = vecAppResource.query();
		$scope.appDelete = function (appId) {
			$http.delete("../api/v2/app/" + appId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/v2/app/").then(
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