vecchioApp.controller('VecAppEditCtrl', [
	"$scope",
	"$http",
	"$window",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $stateParams, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.appId = $stateParams.appId;
		$scope.$evalAsync($http.get(
			"../api/app/" + $scope.appId).then(
			function (response) {
				$scope.app = response.data;
			}));
		$scope.appSave = function () {
			$scope.apps = null;
			var parameter = JSON.stringify($scope.app);
			$http.put("../api/app/" + $scope.appId,
				parameter).then(
				function (data, status, headers, config) {
					$state.go('app');
				}, function (data, status, headers, config) {
					$state.go('app');
				});
		}
	}
]);