vecchioApp.controller('VecDashboardCtrl', [
	"$scope",
	"$http",
	"$state",
	"$rootScope",
	function ($scope, $http, $state, $rootScope) {
		$scope.accesses = null;
		$rootScope.$state = $state;
		$scope.$evalAsync($http.get(
			"../api/access/").then(
			function (response) {
				$scope.accesses = response.data;
			}));
	}]);