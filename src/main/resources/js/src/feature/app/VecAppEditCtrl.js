vecchioApp.controller('VecAppEditCtrl', [
	"$scope",
	"$http",
	"$stateParams",
	"$state",
	"$rootScope",
	"vecAppResource",
	function ($scope, $http, $stateParams, $state, $rootScope, vecAppResource) {
		$rootScope.$state = $state;
		$scope.appId = $stateParams.appId;
		$scope.app = vecAppResource.get({ id: $stateParams.appId });
		$scope.appSave = function () {
			$scope.apps = null;
			var parameter = JSON.stringify($scope.app);
			$http.put("../api/v2/app/" + $scope.appId,
				parameter).then(
				function (data, status, headers, config) {
					$state.go('app');
				}, function (data, status, headers, config) {
					$state.go('app');
				});
		}
	}
]);