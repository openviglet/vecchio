vecchioApp.controller('VecUserNewCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
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