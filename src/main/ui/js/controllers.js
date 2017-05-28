(function () {
	'use strict';
  
	var app = angular.module('passwordPronunciationApp');
	
	/** HOME Controller **/
	app.controller('HomeController', function($scope) {
		$scope.headline = 'General Information';
	});

	/** MODIFY Controller **/
	app.controller('ModifyController', function($scope, $http) {
		$scope.headline = 'Modify Pronunciation Data';
		$scope.subtitle1 = 'Add new Pronunciation Data';
		$scope.subtitle2 = 'Modify existing Pronunciation Data';

		$scope.addRow = function(newPronunciationKey, newPronunciationValue){
			alert($scope.newPronunciationKey);
			alert($scope.newPronunciationValue);
			$http({
		        method : "POST",
		        url : PluginHelper.getPluginRestUrl('password-pronunciation') + '/data',
		        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		        transformRequest: function(obj) {
		            var str = [];
		            for(var p in obj)
		            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		            return str.join("&");
		        },
	        	params: { 'key' : $scope.newPronunciationKey, 'value': $scope.newPronunciationValue }
		    }).then(function mySuccess(response) {
				$scope.createError = null;
				$scope.createSuccess = 'Data was sucessfully updated';
				populateTable($scope, $http);
		        return;
		    }, function myError(response) {
		    	$scope.createError = 'There was an error updating the entry';
				$scope.createSuccess = null;			
				return;
		    });

		};

		$scope.remove = function(id) {
			$http({
		        method : "DELETE",
		        url : PluginHelper.getPluginRestUrl('password-pronunciation') + '/data/' + id,
		    }).then(function mySuccess(response) {
				$scope.modifyError = null;
				$scope.modifySuccess = 'Data was sucessfully deleted';
				populateTable($scope, $http);
				return;
		    }, function myError(response) {
		    	$scope.modifyError = 'There was an error deleting the entry';
				$scope.modifySuccess = null;
				return;
		    });
		};
		
		$scope.update = function(index, id, key, value) {
			var span = $('span[id^="mappingvalue-' + index + '"]');
		    var spanvalue = span[0].innerHTML;
		    
		    $http({
		        method : "PUT",
		        url : PluginHelper.getPluginRestUrl('password-pronunciation') + '/data',
		        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		        transformRequest: function(obj) {
		            var str = [];
		            for(var p in obj)
		            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		            return str.join("&");
		        },
	        	params: { 'id' : id, 'key' : key, 'value': spanvalue }
		    }).then(function mySuccess(response) {
				$scope.modifyError = null;
				$scope.modifySuccess = 'Data was sucessfully updated';
		        $scope.data1 = populateTable($scope, $http);
		    }, function myError(response) {
		    	$scope.modifyError = 'There was an error updating the entry';
				$scope.modifySuccess = null;
		    });
		};

		function populateTable($scope, $http) {
			$http({
		        method : "GET",
		        url : PluginHelper.getPluginRestUrl('password-pronunciation') + '/data'
		    }).then(function mySuccess(response) {
		        $scope.data1 = response.data;
		    }, function myError(response) {
		        $scope.data1 = response.statusText;
		    });
		};
		
		populateTable($scope, $http);
	});

	/** UPLOAD Controller **/
	app.controller('UploadController', function($scope) {
		$scope.headline = 'Upload new Pronunciation Data';

		$scope.upload = function() {
			var f = document.getElementById('file').files[0],
					r = new FileReader();

			r.onloadend = function(e) {
				var data = e.target.result;
				//send your binary data via $http or $resource or do anything else with it
			}

			r.readAsBinaryString(f);
		};
	});
}());