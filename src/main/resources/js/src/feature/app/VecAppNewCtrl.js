vecchioApp.controller('VecAppNewCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.app = {};
		$scope.appSave = function () {
			var parameter = JSON.stringify($scope.app);
			$http.post("../api/app/",
				parameter).then(
				function (data, status, headers, config) {
					$state.go('app');
				}, function (data, status, headers, config) {
					$state.go('app');
				});
		}
	}
]);