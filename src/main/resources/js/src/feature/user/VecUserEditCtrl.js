vecchioApp.controller('VecUserEditCtrl', [
	"$scope",
	"$http",
	"$window",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	"vigLocale",
	"vecUserResource",
	"vecUserFactory",
	function ($scope, $http, $window, $stateParams, $state, $rootScope, $translate, vigLocale, vecUserResource, vecUserFactory) {
		$scope.vigLanguage = vigLocale.getLocale().substring(0, 2);
		$translate.use($scope.vigLanguage);
		$rootScope.$state = $state;
		$scope.userId = $stateParams.userId;

		$scope.user = vecUserResource.get({ id: $stateParams.userId });

		$scope.userSave = function () {
			vecUserFactory.save($scope.user, false);
		}

		$scope.addGroups = function () {
			vecUserFactory.addGroups($scope.user);
		}

		$scope.removeGroup = function (index) {
			$scope.user.vecGroups.splice(index, 1);
		}
	}
]);