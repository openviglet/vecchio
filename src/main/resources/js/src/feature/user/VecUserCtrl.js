vecchioApp.controller('VecUserCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecUserResource",
	function ($scope, $http, $state, $rootScope, vecUserResource) {
		$rootScope.$state = $state;
		$scope.users = vecUserResource.query();
		
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