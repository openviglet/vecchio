var vecWelcome = angular.module('vecWelcome',
		[ 'pascalprecht.translate','ngCookies' ]);

vecWelcome
		.config(function($httpProvider) {
			$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		});

vecWelcome.service('vecAPIServerService', [
	'$http',
	'$location',
	'$cookies',
	function($http, $location, $cookies) {
		var vecProtocol = $location.protocol();
		var vecHostname = $location.host();
		var vecPort = $location.port();
		var vecAPIContext = "/api";
		var vecEmbServer = vecProtocol + "://" + vecHostname + ":"
				+ vecPort;
		
		var vecEmbAPIServer = vecEmbServer + vecAPIContext;
		console.log(vecEmbServer);

		this.server = function() {

			if ($cookies.get('vecServer') != null)
				return $cookies.get('vecServer');
			else {
				$http({
					method : 'GET',
					url : vecEmbAPIServer + "/v2"
				}).then(function successCallback(response) {
					$cookies.put('vecServer', vecEmbServer);
				}, function errorCallback(response) {
					$cookies.put('vecServer', vecEmbServer);
				});
				return vecEmbServer;
			}
		}
		this.get = function() {

			if ($cookies.get('vecAPIServer') != null)
				return $cookies.get('vecAPIServer');
			else {
				$http({
					method : 'GET',
					url : vecEmbAPIServer + "/v2"
				}).then(function successCallback(response) {
					$cookies.put('vecAPIServer', vecEmbAPIServer);
				}, function errorCallback(response) {				
					$cookies.put('vecAPIServer', vecEmbAPIServer);
				});
				return vecEmbAPIServer;
			}
		}
	} ]);


vecWelcome.controller('VecWelcomeCtrl', [
		"$scope",
		"$http",
		"$window",
		"vecAPIServerService",
		function($scope, $http, $window, vecAPIServerService) {

			$scope.showLogin = false;

			var errorUI = function() {
				$('.log-status').addClass('wrong-entry');
				$('.alert').fadeIn(500);
				setTimeout("$('.alert').fadeOut(1500);", 3000);
				$('.form-control').keypress(function() {
					$('.log-status').removeClass('wrong-entry');
				});
			}

			var authenticate = function(credentials) {

				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};

				$http.get(vecAPIServerService
						.get()
						.concat("/v2"), {
					headers : headers
				}).then(function(response) {
					if (response.data.product) {
						$scope.showLogin = false;
						$window.location.href = "/console";
					} else {
						$scope.showLogin = true;
						errorUI();
					}
				}, function() {
					$scope.showLogin = true;
					errorUI();
				});
			}

			$scope.credentials = {};
			$scope.login = function() {
				authenticate($scope.credentials);
			};

			// Check Auth
			$http.get(vecAPIServerService
					.get()
					.concat("/v2")).then(function(response) {
				if (response.data.product) {
					$scope.showLogin = false;
					$window.location.href = "/console";
				} else {
					$scope.showLogin = true;
				}
			}, function() {
				$scope.showLogin = true;
			});
		} ]);
