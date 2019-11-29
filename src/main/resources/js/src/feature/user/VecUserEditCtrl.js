vecchioApp.controller('VecUserEditCtrl', [
	"$scope",
	"$http",
	"$window",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	"vigLocale",
	function ($scope, $http, $window, $stateParams, $state, $rootScope, $translate, vigLocale) {
		$scope.vigLanguage = vigLocale.getLocale().substring(0, 2);
		$translate.use($scope.vigLanguage);
		$rootScope.$state = $state;
		$scope.userId = $stateParams.userId;
		$scope.$evalAsync($http.get(
			"../api/user/" + $scope.userId).then(
			function (response) {
				$scope.user = response.data;
			}));
		$scope.userSave = function () {
			$scope.users = null;
			var parameter = JSON.stringify($scope.user);
			$http.put("../api/user/" + $scope.userId,
				parameter).then(
				function (data, status, headers, config) {
					  $state.go('organization.user');
				}, function (data, status, headers, config) {
					  $state.go('organization.user');
				});
		}
	}
]);