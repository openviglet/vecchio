vecchioApp.controller('VecAppNewCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	function ($scope, $http, $state, $rootScope) {
		$rootScope.$state = $state;
		$scope.app = {};
		$scope.appSave = function () {
			var parameter = JSON.stringify($scope.app);
			$http.post("../api/v2/app/",
				parameter).then(
				function (data, status, headers, config) {
					$state.go('app');
				}, function (data, status, headers, config) {
					$state.go('app');
				});
		}
	}
]);