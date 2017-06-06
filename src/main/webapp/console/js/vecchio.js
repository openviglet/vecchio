var vecchioApp = angular.module('vecchioApp', [ 'ui.router', 'nvd3' ]);

vecchioApp.config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/home/dashboard');
	$stateProvider.state('home', {
		url : '/home',
		templateUrl : 'home.html'
	})
	.state(
			'home.dashboard',
			{
				url : '/dashboard',
				templateUrl : 'dashboard.html',
				controller : function($scope, $http, $window) {
					$scope.accesses = null;

					$scope.$evalAsync($http.get(
							"http://localhost:8080/api/access/").then(
							function(response) {
								$scope.accesses = response.data;
							}));
				}
			})
			.state('home.config', {
		url : '/config',
		templateUrl : 'config.html'
	}).state(
			'home.config.mapping',
			{
				url : '/mapping',
				templateUrl : 'mapping.html',
				controller : function($scope, $http, $window) {
					$scope.mappings = null;

					$scope.$evalAsync($http.get(
							"http://localhost:8080/api/mapping/").then(
							function(response) {
								$scope.mappings = response.data;
							}));
				}
			}).state(
			'home.mapping-new',
			{
				url : '/mapping/new',
				templateUrl : 'mapping-new.html',
				controller : function($scope, $http, $window) {
					$scope.mapping = {};
					$scope.mappingSave = function() {
						var parameter = JSON.stringify($scope.mapping);
						$http.post("http://localhost:8080/api/mapping/",
								parameter).then(
								function(data, status, headers, config) {
									// this callback will be called
									// asynchronously
									// when the response is available
									console.log(data);
								}, function(data, status, headers, config) {
									// called asynchronously if an error occurs
									// or server returns response with an error
									// status.
								});
					}
				}
			});

});

vecchioApp.controller('VecMappingCtrl', [
		"$scope",
		"$http",
		"$window",
		function($scope, $http, $window) {
			$scope.mappings = null;

			$scope.$evalAsync($http.get("http://localhost:8080/api/mapping/")
					.then(function(response) {
						$scope.mappings = response.data;
					}));
		} ]);


vecchioApp.controller('lineChartCtrl', function($scope, $http, $window){
	
    $scope.options = {
        chart: {
            type: 'lineChart',
            height: 450,
            margin : {
                top: 20,
                right: 20,
                bottom: 40,
                left: 55
            },
            x: function(d){ return d.x; },
            y: function(d){ return d.y; },
            useInteractiveGuideline: true,
            dispatch: {
                stateChange: function(e){ console.log("stateChange"); },
                changeState: function(e){ console.log("changeState"); },
                tooltipShow: function(e){ console.log("tooltipShow"); },
                tooltipHide: function(e){ console.log("tooltipHide"); }
            },
            xAxis: {
                axisLabel: 'Time'
            },
            yAxis: {
                axisLabel: 'Response Time (s)',
                tickFormat: function(d){
                    return d3.format('.02f')(d);
                },
                axisLabelDistance: -10
            },
            callback: function(chart){
                console.log("!!! lineChart callback !!!");
            }
        },
        title: {
            enable: true,
            text: 'Access per Time'
        },
        subtitle: {
            enable: true,
            text: 'Subtitle for simple line chart. Lorem ipsum dolor sit amet, at eam blandit sadipscing, vim adhuc sanctus disputando ex, cu usu affert alienum urbanitas.',
            css: {
                'text-align': 'center',
                'margin': '10px 13px 0px 7px'
            }
        },
        caption: {
            enable: true,
            html: '<b>Figure 1.</b> Lorem ipsum dolor sit amet, at eam blandit sadipscing, <span style="text-decoration: underline;">vim adhuc sanctus disputando ex</span>, cu usu affert alienum urbanitas. <i>Cum in purto erat, mea ne nominavi persecuti reformidans.</i> Docendi blandit abhorreant ea has, minim tantas alterum pro eu. <span style="color: darkred;">Exerci graeci ad vix, elit tacimates ea duo</span>. Id mel eruditi fuisset. Stet vidit patrioque in pro, eum ex veri verterem abhorreant, id unum oportere intellegam nec<sup>[1, <a href="https://github.com/krispo/angular-nvd3" target="_blank">2</a>, 3]</sup>.',
            css: {
                'text-align': 'justify',
                'margin': '10px 13px 0px 7px'
            }
        }
    };
    $scope.accesses = null;

	$scope.$evalAsync($http.get(
			"http://localhost:8080/api/access/").then(
			function(response) {
				$scope.accesses = response.data;
				 $scope.data = sinAndCos();
			}));
	
   

    /*Random Data Generator */
    function sinAndCos() {
        var sin = [],sin2 = [],
            cos = [];
        for (var a = 0; a <  $scope.accesses.length; a++){
            var access = $scope.accesses[a];
            
            sin.push({x: a, y: (access.responseTime/1000)}); 
           
        }
        //Data is represented as an array of {x,y} pairs.
        for (var i = 0; i < 100; i++) {
          //  sin.push({x: i, y: Math.sin(i/10)});
            sin2.push({x: i, y: i % 10 == 5 ? null : Math.sin(i/10) *0.25 + 0.5});
            cos.push({x: i, y: .5 * Math.cos(i/10+ 2) + Math.random() / 10});
        }

        //Line chart data should be sent as an array of series objects.
        return [
            {
                values: sin,      //values - represents the array of {x,y} data points
                key: 'Access', //key  - the name of the series.
                color: '#ff7f0e'  //color - optional: choose your own line color.
            },
            {
                values: cos,
                key: 'Turing',
                color: '#2ca02c'
            },
            {
                values: sin2,
                key: 'GitHub',
                color: '#7777ff',
                area: true      //area - set to true if you want this line to turn into a filled area chart.
            }
        ];
    };
})