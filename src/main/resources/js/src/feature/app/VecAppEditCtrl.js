vecchioApp.controller('VecAppEditCtrl', [
	"$scope",
	"$stateParams",
	"$state",
	"$rootScope",
	"vecAppResource",
	"Notification",
	"$http",
	"vecAPIServerService",
	"$location",
	function ($scope, $stateParams, $state, $rootScope, vecAppResource, Notification, $http, vecAPIServerService, $location) {
		$rootScope.$state = $state;
		$scope.appId = $stateParams.appId;
		$scope.app = vecAppResource.get({ id: $stateParams.appId });

		$scope.protocol = $location.protocol();
		$scope.host = $location.host();
		$scope.port = ($location.port() === 80 || $location.port() === 443)? "" : $location.port();
		$scope.serviceURL = $scope.protocol + "://" + $scope.host + ":" + $scope.port;

		$scope.appSave = function () {
			$scope.app
				.$update(function () {
					Notification.info('The ' + $scope.app.name + '  App was updated.');
				});
		}

		$scope.genKey = function () {
			$http.put(
				vecAPIServerService.get().concat("/v2/app/" + $scope.app.id + "/gen_key")).then(
					function (response) {
						$scope.app = response.data;
						Notification.info('Regenerated Customer Key and Secret.');
					});
		}

		$scope.genToken = function () {
			$http.put(
				vecAPIServerService.get().concat("/v2/app/" + $scope.app.id + "/gen_token")).then(
					function (response) {
						$scope.app = response.data;
						Notification.info('Regenerated My Access Token and Token Secret.');
					});
		}

		$scope.revokeAccessTokens = function () {
			$http.delete(
				vecAPIServerService.get().concat("/v2/app/" + $scope.app.id + "/accessTokens")).then(
					function (response) {
						$scope.app = response.data;
						Notification.info('Acess Tokens were revoked');
					});
		}
	}
]);