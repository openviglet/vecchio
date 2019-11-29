vecchioApp.controller('VecDashboardCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	function ($scope, $http, $state, $rootScope) {
		$scope.accesses = null;
		$rootScope.$state = $state;
		$scope.$evalAsync($http.get(
			"../api/v2/access/").then(
			function (response) {
				$scope.accesses = response.data;
			}));
	}]);