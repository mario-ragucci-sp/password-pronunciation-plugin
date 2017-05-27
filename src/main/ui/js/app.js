(function () {
	'use strict';
	
	var app = angular.module('passwordPronunciationApp', ['ngRoute']);
	app.config(function($routeProvider) {
		$routeProvider

		.when('/', {
			templateUrl : PluginHelper.getPluginFileUrl('password-pronunciation-plugin', 'ui/partials/home.html'),
			controller  : 'HomeController'
		})

		.when('/home', {
			templateUrl : PluginHelper.getPluginFileUrl('password-pronunciation-plugin', 'ui/partials/home.html'),
			controller  : 'HomeController'
		})

		.when('/modify', {
			templateUrl : PluginHelper.getPluginFileUrl('password-pronunciation-plugin', 'ui/partials/modify.html'),
			controller  : 'ModifyController'
		})

		.when('/upload', {
			templateUrl : PluginHelper.getPluginFileUrl('password-pronunciation-plugin', 'ui/partials/upload.html'),
			controller  : 'UploadController'
		})

		.otherwise({redirectTo: '/'});
	});
}());