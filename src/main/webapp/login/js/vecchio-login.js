var vecchioLogin = angular.module('vecchioLogin', [ 'ui.router',
		'pascalprecht.translate' ]);

vecchioLogin.config(function($locationProvider, $translateProvider) {
	$translateProvider.useSanitizeValueStrategy('escaped');
	$translateProvider.translations('en', {
		LOGIN : 'Log in'
	});
	$translateProvider.translations('pt', {
		LOGIN : 'Entrar'
	});
	$translateProvider.fallbackLanguage('en');
	// use the HTML5 History API
	$locationProvider.html5Mode({
		enabled : true,
		requireBase : false
	});
});

vecchioLogin.controller('VecLoginCtrl', [
		"$scope",
		"$http",
		"$window",
		"$location",
		"$translate",
		function($scope, $http, $window, $location, $translate) {
			$scope.vecError = $location.search().error;
			$scope.vecResponseType = $location.search().response_type;
			$scope.vecClientId = $location.search().client_id;
			$scope.vecRedirectUri = $location.search().redirect_uri;
			$scope.vecUsername = $location.search().username;
			if ($scope.vecError != null) {

				$('.log-status').addClass('wrong-entry');
				$('.alert').fadeIn(500);
				setTimeout("$('.alert').fadeOut(1500);", 3000);
				$('.form-control').keypress(function() {
					$('.log-status').removeClass('wrong-entry');
				});
			}
			$scope.vecAction = '/api/login/?response_type='
					+ $scope.vecResponseType + '&client_id='
					+ $scope.vecClientId + '&redirect_uri='
					+ $scope.vecRedirectUri;
			console.log($location.search().token);
		} ]);
