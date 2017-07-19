var vecchioApp = angular.module('vecchioApp', ['ui.router', 'nvd3', 'pascalprecht.translate']);

vecchioApp.factory('vigLocale', ['$window', function ($window) {
    return {
        getLocale: function () {
            var nav = $window.navigator;
            if (angular.isArray(nav.languages)) {
                if (nav.languages.length > 0) {
                    return nav.languages[0].split('-').join('_');
                }
            }
            return ((nav.language ||
                nav.browserLanguage ||
                nav.systemLanguage ||
                nav.userLanguage
            ) || '').split('-').join('_');
        }
    }
}]);

vecchioApp.config(function ($stateProvider, $urlRouterProvider,$locationProvider, $translateProvider) {
	$translateProvider.useSanitizeValueStrategy('escaped');
	$translateProvider
			.translations(
					'en',
					{
						
						USERNAME: "Username",
						FIRST_NAME: "First Name",
						LAST_NAME: "Last Name",
						PASSWORD: "Password",
						SETTINGS_ACCOUNT_TITLE : "Account",
						SETTINGS_ACCOUNT_SUBTITLE : "Change your basic account and language settings.",
						SETTINGS_SAVE_CHANGES : "Save Changes"						
					});
	$translateProvider
			.translations(
					'pt',
					{
						USERNAME: "Nome do Usuário",
						FIRST_NAME: "Nome",
						LAST_NAME: "Sobrenome",
						PASSWORD: "Senha",		
						SETTINGS_ACCOUNT_TITLE : "Conta",
						SETTINGS_ACCOUNT_SUBTITLE : "Altere suas configurações básicas da conta e de idioma.",
						SETTINGS_SAVE_CHANGES : "Salvar Alterações"
					});
	$translateProvider.fallbackLanguage('en');
	
	$urlRouterProvider.otherwise('/dashboard');
	$stateProvider
		.state('dashboard', {
			url: '/dashboard',
			templateUrl: 'dashboard.html',
			controller: 'VecDashboardCtrl',
			data : { pageTitle: 'Dashboard | Viglet Vecchio' }
		})
		.state('organization', {
			url: '/org',
			templateUrl: 'organization.html',
			data : { pageTitle: 'Organization | Viglet Vecchio' }
		})
		.state('mapping', {
			url: '/mapping',
			templateUrl: 'mapping.html',
			controller: 'VecMappingCtrl',
			data : { pageTitle: 'Mapping | Viglet Vecchio' }
		})
		.state('mapping-new', {
			url: '/mapping/new',
			templateUrl: 'mapping-item.html',
			controller: 'VecMappingNewCtrl',
			data : { pageTitle: 'New Mapping | Viglet Vecchio' }
		})
		.state('mapping-edit', {
			url: '/mapping/:mappingId',
			templateUrl: 'mapping-item.html',
			controller: 'VecMappingEditCtrl',
			data : { pageTitle: 'Edit Mapping | Viglet Vecchio' }
		})
		.state('organization.user', {
			url: '/user',
			templateUrl: 'user.html',
			controller: 'VecUserCtrl',
			data : { pageTitle: 'Users | Viglet Vecchio' }
		})
		.state('organization.user-new', {
			url: '/user/new',
			templateUrl: 'user-item.html',
			controller: 'VecUserNewCtrl',
			data : { pageTitle: 'New User | Viglet Vecchio' }
		})
		.state('organization.user-edit', {
			url: '/user/:userId',
			templateUrl: 'user-item.html',
			controller: 'VecUserEditCtrl',
			data : { pageTitle: 'Edit User | Viglet Vecchio' }
		})
		.state('organization.role', {
			url: '/role',
			templateUrl: 'role.html',
			controller: 'VecRoleCtrl',
			data : { pageTitle: 'Roles | Viglet Vecchio' }
		})
		.state('organization.role-new', {
			url: '/role/new',
			templateUrl: 'role-item.html',
			controller: 'VecRoleNewCtrl',
			data : { pageTitle: 'New Role | Viglet Vecchio' }
		})
		.state('organization.role-edit', {
			url: '/role/:roleId',
			templateUrl: 'role-item.html',
			controller: 'VecRoleEditCtrl',
			data : { pageTitle: 'Edit Role | Viglet Vecchio' }
		})
		.state('organization.group', {
			url: '/group',
			templateUrl: 'group.html',
			controller: 'VecGroupCtrl',
			data : { pageTitle: 'Groups | Viglet Vecchio' }
		})
		.state('organization.group-new', {
			url: '/group/new',
			templateUrl: 'group-item.html',
			controller: 'VecGroupNewCtrl',
			data : { pageTitle: 'New Group | Viglet Vecchio' }
		})
		.state('organization.group-edit', {
			url: '/group/:groupId',
			templateUrl: 'group-item.html',
			controller: 'VecGroupEditCtrl',
			data : { pageTitle: 'Edit Group | Viglet Vecchio' }
		})
		.state('app', {
			url: '/app',
			templateUrl: 'app.html',
			controller: 'VecAppCtrl',
			data : { pageTitle: 'Apps | Viglet Vecchio'}
		})
		.state('app-new', {
			url: '/app/new',
			templateUrl: 'app-item.html',
			controller: 'VecAppNewCtrl',
			data : { pageTitle: 'New App | Viglet Vecchio', saveButton: 'Save'}
		})
		.state('app-edit', {
			url: '/app/:appId',
			templateUrl: 'app-item.html',
			controller: 'VecAppEditCtrl',
			data : { pageTitle: 'Edit App | Viglet Vecchio', saveButton: 'Update Settings' }
		})	
		.state('app-edit.keys', {
			url: '/keys',
			templateUrl: 'app-item-keys.html',
			controller: 'VecAppEditCtrl',
			data : { pageTitle: 'Edit App Keys | Viglet Vecchio' }
		});

});

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

vecchioApp.controller('VecMappingCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$scope.mappings = null;
		$rootScope.$state = $state;
		$scope.$evalAsync($http.get(
			"../api/mapping/").then(
			function (response) {
				$scope.mappings = response.data;
			}));

		$scope.mappingDelete = function (mappingId) {
			$http.delete("../api/mapping/" + mappingId).then(
				function (data, status, headers, config) {
					$http.get(
							"../api/mapping/").then(
						function (response) {
							$scope.mappings = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);

vecchioApp.controller('VecMappingNewCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.mapping = {};
		$scope.mappingSave = function () {
			var parameter = JSON.stringify($scope.mapping);
			$http.post("../api/mapping/",
				parameter).then(
				function (data, status, headers, config) {
					$state.go('mapping');
				}, function (data, status, headers, config) {
					$state.go('mapping');

				});
		}
	}
]);

vecchioApp.controller('VecMappingEditCtrl', [
	"$scope",
	"$http",
	"$window",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $stateParams, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.mappingId = $stateParams.mappingId;
		$scope.$evalAsync($http.get(
			"../api/mapping/" + $scope.mappingId).then(
			function (response) {
				$scope.mapping = response.data;
			}));
		$scope.mappingSave = function () {
			$scope.mappings = null;
			var parameter = JSON.stringify($scope.mapping);
			$http.put("../api/mapping/" + $scope.mappingId,
				parameter).then(
				function (data, status, headers, config) {
					   $state.go('mapping');
				}, function (data, status, headers, config) {
					   $state.go('mapping');
				});
		}
	}
]);

vecchioApp.controller('VecUserCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.users = null;

		$scope.$evalAsync($http.get(
			"../api/user/").then(
			function (response) {
				$scope.users = response.data;
			}));

		$scope.userDelete = function (userId) {
			$http.delete("../api/user/" + userId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/user/").then(
						function (response) {
							$scope.users = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);

vecchioApp.controller('VecUserNewCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.user = {};
		$scope.userSave = function () {
			var parameter = JSON.stringify($scope.user);
			$http.post("../api/user/",
				parameter).then(
				function (data, status, headers, config) {					
			          $state.go('organization.user');
				}, function (data, status, headers, config) {
			          $state.go('organization.user');
				});
		}
	}
]);

vecchioApp.controller('VecUserEditCtrl', [
	"$scope",
	"$http",
	"$window",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	"vigLocale",
	function ($scope, $http, $window, $stateParams, $state, $rootScope, $translate, vigLocale) {
		$scope.vigLanguage = vigLocale.getLocale().substring(0, 2);
		$translate.use($scope.vigLanguage);
		$rootScope.$state = $state;
		$scope.userId = $stateParams.userId;
		$scope.$evalAsync($http.get(
			"../api/user/" + $scope.userId).then(
			function (response) {
				$scope.user = response.data;
			}));
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

vecchioApp.controller('VecRoleCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.roles = null;

		$scope.$evalAsync($http.get(
			"../api/role/").then(
			function (response) {
				$scope.roles = response.data;
			}));

		$scope.roleDelete = function (roleId) {
			$http.delete("../api/role/" + roleId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/role/").then(
						function (response) {
							$scope.roles = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);

vecchioApp.controller('VecRoleNewCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.role = {};
		$scope.roleSave = function () {
			var parameter = JSON.stringify($scope.role);
			$http.post("../api/role/",
				parameter).then(
				function (data, status, headers, config) {
					  $state.go('organization.role');
				}, function (data, status, headers, config) {
					  $state.go('organization.role');
				});
		}
	}
]);

vecchioApp.controller('VecRoleEditCtrl', [
	"$scope",
	"$http",
	"$window",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $stateParams, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.roleId = $stateParams.roleId;
		$scope.$evalAsync($http.get(
			"../api/role/" + $scope.roleId).then(
			function (response) {
				$scope.role = response.data;
			}));
		$scope.roleSave = function () {
			$scope.roles = null;
			var parameter = JSON.stringify($scope.role);
			$http.put("../api/role/" + $scope.roleId,
				parameter).then(
				function (data, status, headers, config) {
					  $state.go('organization.role');
				}, function (data, status, headers, config) {
					  $state.go('organization.role');
				});
		}
	}
]);

vecchioApp.controller('VecGroupCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.groups = null;

		$scope.$evalAsync($http.get(
			"../api/group/").then(
			function (response) {
				$scope.groups = response.data;
			}));

		$scope.groupDelete = function (groupId) {
			$http.delete("../api/group/" + groupId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/group/").then(
						function (response) {
							$scope.groups = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);

vecchioApp.controller('VecGroupNewCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.group = {};
		$scope.groupSave = function () {
			var parameter = JSON.stringify($scope.group);
			$http.post("../api/group/",
				parameter).then(
				function (data, status, headers, config) {
					$state.go('organization.group');
				}, function (data, status, headers, config) {
					$state.go('organization.group');
				});
		}
	}
]);

vecchioApp.controller('VecGroupEditCtrl', [
	"$scope",
	"$http",
	"$window",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $stateParams, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.groupId = $stateParams.groupId;
		$scope.$evalAsync($http.get(
			"../api/group/" + $scope.groupId).then(
			function (response) {
				$scope.group = response.data;
			}));
		$scope.groupSave = function () {
			$scope.groups = null;
			var parameter = JSON.stringify($scope.group);
			$http.put("../api/group/" + $scope.groupId,
				parameter).then(
				function (data, status, headers, config) {
					$state.go('organization.group');
				}, function (data, status, headers, config) {
					$state.go('organization.group');
				});
		}
	}
]);

vecchioApp.controller('VecAppCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.apps = null;

		$scope.$evalAsync($http.get(
			"../api/app/").then(
			function (response) {
				$scope.apps = response.data;
			}));

		$scope.appDelete = function (appId) {
			$http.delete("../api/app/" + appId).then(
				function (data, status, headers, config) {
					$http.get(
						"../api/app/").then(
						function (response) {
							$scope.apps = response.data;
						});
				}, function (data, status, headers, config) {
					// called asynchronously if an error occurs
					// or server returns response with an error
					// status.
				});
		}
	}]);

vecchioApp.controller('VecAppNewCtrl', [
	"$scope",
	"$http",
	"$window",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.app = {};
		$scope.appSave = function () {
			var parameter = JSON.stringify($scope.app);
			$http.post("../api/app/",
				parameter).then(
				function (data, status, headers, config) {
					$state.go('app');
				}, function (data, status, headers, config) {
					$state.go('app');
				});
		}
	}
]);

vecchioApp.controller('VecAppEditCtrl', [
	"$scope",
	"$http",
	"$window",
	"$stateParams",
	"$state",
	"$rootScope",
	"$translate",
	function ($scope, $http, $window, $stateParams, $state, $rootScope, $translate) {
		$rootScope.$state = $state;
		$scope.appId = $stateParams.appId;
		$scope.$evalAsync($http.get(
			"../api/app/" + $scope.appId).then(
			function (response) {
				$scope.app = response.data;
			}));
		$scope.appSave = function () {
			$scope.apps = null;
			var parameter = JSON.stringify($scope.app);
			$http.put("../api/app/" + $scope.appId,
				parameter).then(
				function (data, status, headers, config) {
					$state.go('app');
				}, function (data, status, headers, config) {
					$state.go('app');
				});
		}
	}
]);

vecchioApp.controller('lineChartCtrl', function ($scope, $http, $window, $translate) {

	$scope.options = {
		chart: {
			type: 'lineChart',
			height: 450,
			margin: {
				top: 20,
				right: 20,
				bottom: 40,
				left: 55
			},
			x: function (d) { return d.x; },
			y: function (d) { return d.y; },
			useInteractiveGuideline: true,
			dispatch: {
				stateChange: function (e) { console.log("stateChange"); },
				changeState: function (e) { console.log("changeState"); },
				tooltipShow: function (e) { console.log("tooltipShow"); },
				tooltipHide: function (e) { console.log("tooltipHide"); }
			},
			xAxis: {
				axisLabel: 'Time'
			},
			yAxis: {
				axisLabel: 'Response Time (s)',
				tickFormat: function (d) {
					return d3.format('.02f')(d);
				},
				axisLabelDistance: -10
			},
			callback: function (chart) {
				console.log("!!! lineChart callback !!!");
			}
		},
		title: {
			enable: true,
			text: 'Response Time'
		},
		subtitle: {
			enable: false,
			text: 'Subtitle for simple line chart. Lorem ipsum dolor sit amet, at eam blandit sadipscing, vim adhuc sanctus disputando ex, cu usu affert alienum urbanitas.',
			css: {
				'text-align': 'center',
				'margin': '10px 13px 0px 7px'
			}
		},
		caption: {
			enable: false,
			html: '<b>Figure 1.</b> Lorem ipsum dolor sit amet, at eam blandit sadipscing, <span style="text-decoration: underline;">vim adhuc sanctus disputando ex</span>, cu usu affert alienum urbanitas. <i>Cum in purto erat, mea ne nominavi persecuti reformidans.</i> Docendi blandit abhorreant ea has, minim tantas alterum pro eu. <span style="color: darkred;">Exerci graeci ad vix, elit tacimates ea duo</span>. Id mel eruditi fuisset. Stet vidit patrioque in pro, eum ex veri verterem abhorreant, id unum oportere intellegam nec<sup>[1, <a href="https://github.com/krispo/angular-nvd3" target="_blank">2</a>, 3]</sup>.',
			css: {
				'text-align': 'justify',
				'margin': '10px 13px 0px 7px'
			}
		}
	};
	$scope.accesses = null;

	$scope.$evalAsync($http.get(
		"../api/access/response_time").then(
		function (response) {
			$scope.data = response.data;
		// $scope.data = sinAndCos();
		}));



	/* Random Data Generator */
	function sinAndCos() {
		var sin = [], sin2 = [],
			cos = [];
		for (var a = 0; a < $scope.accesses.length; a++) {
			var access = $scope.accesses[a];

			sin.push({ x: a, y: (access.responseTime / 1000) });

		}
		// Data is represented as an array of {x,y} pairs.
		for (var i = 0; i < 100; i++) {
			// sin.push({x: i, y: Math.sin(i/10)});
			sin2.push({ x: i, y: i % 10 == 5 ? null : Math.sin(i / 10) * 0.25 + 0.5 });
			cos.push({ x: i, y: .5 * Math.cos(i / 10 + 2) + Math.random() / 10 });
		}

		// Line chart data should be sent as an array of series objects.
		return [
			{
				values: sin,      // values - represents the array of {x,y}
				// data points
				key: 'Access', // key - the name of the series.
				color: '#ff7f0e',  // color - optional: choose your own line
				// color.
				area: true
			}
		];
	};
})