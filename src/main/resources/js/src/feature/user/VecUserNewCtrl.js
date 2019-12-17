vecchioApp.controller('VecUserNewCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	"vecUserFactory",
	"vecAPIServerService",
	function ($scope, $http, $state, $rootScope,
		vecUserFactory, vecAPIServerService) {
		$rootScope.$state = $state;
		$scope.user = {};
		$scope.isNew = true;
		$scope.$evalAsync($http.get(
			vecAPIServerService.get().concat("/v2/user/model")).then(
				function (response) {
					$scope.user = response.data;
				}));
		$scope.userSave = function () {
			vecUserFactory.save($scope.user, $scope.isNew);
		}

		$scope.addGroups = function () {
			vecUserFactory.addGroups($scope.user);
		}

		$scope.removeGroup = function (index) {
			$scope.user.vecGroups.splice(index, 1);
		}
	}]);