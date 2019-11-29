vecchioApp.controller('VecUserEditCtrl', [
	"$scope",
	"$http",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	"vigLocale",
	"vecUserResource",
	function ($scope, $http, $stateParams, $state, $rootScope, $translate, vigLocale, vecUserResource) {
		$scope.vigLanguage = vigLocale.getLocale().substring(0, 2);
		$translate.use($scope.vigLanguage);
		$rootScope.$state = $state;
		$scope.userId = $stateParams.userId;
		$scope.user = vecUserResource.get({ id: $stateParams.userId });
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