vecchioApp.controller('VecDashboardCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$scope.accesses = null;
		$rootScope.$state = $state;
		$scope.$evalAsync($http.get(
			"../api/access/").then(
			function (response) {
				$scope.accesses = response.data;
			}));
	}]);