vecchioApp.controller('VecUserNewCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	function ($scope, $http, $state, $rootScope) {
		$rootScope.$state = $state;
		$scope.user = {};
		$scope.userSave = function () {
			var parameter = JSON.stringify($scope.user);
			$http.post("../api/user/",
				parameter).then(
				function (data, status, headers, config) {					
			          $state.go('organization.user');
				}, function (data, status, headers, config) {
			          $state.go('organization.user');
				});
		}
	}
]);