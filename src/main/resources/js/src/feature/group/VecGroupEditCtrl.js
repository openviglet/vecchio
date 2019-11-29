vecchioApp.controller('VecGroupEditCtrl', [
	"$scope",
	"$state",
	"$rootScope",
	"vecGroupResource",
	"vecGroupFactory",
	"$stateParams",
	"Notification",
	function ($scope, $state, $rootScope,
		vecGroupResource, vecGroupFactory, $stateParams, Notification) {
		$rootScope.$state = $state;
		$scope.groupId = $stateParams.groupId;

		$scope.group = vecGroupResource.get({
			id: $scope.groupId
		});

		$scope.groupSave = function () {
			angular.forEach($scope.group.vecUsers, function (vecUser, key) {
				console.log("removendo atributo: " + vecUser.username);
				delete vecUser.vecGroups;									
			});
			$scope.group.$update(function () {
				Notification.warning('The ' + $scope.group.name + ' Group was updated.');
			});
		}

		$scope.addUsers = function () {
			vecGroupFactory.addUsers($scope.group);
		}

		$scope.removeUser = function (index) {
			$scope.group.vecUsers.splice(index, 1);
		}
	}]);