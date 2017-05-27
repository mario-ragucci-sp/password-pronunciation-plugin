(function () {
	'use strict';
  
	var app = angular.module('passwordPronunciationApp');
	
	/** HOME Controller **/
	app.controller('HomeController', function($scope) {
		$scope.headline = 'General Information';
	});

	/** MODIFY Controller **/
	app.controller('ModifyController', function($scope) {
		$scope.headline = 'Modify Pronunciation Data';
		$scope.subtitle1 = 'Add new Pronunciation Data';
		$scope.subtitle2 = 'Modify existing Pronunciation Data';

		$scope.addRow = function(){
			if($scope.newPronunciationKey == null) {
				$scope.createError = 'Key may not be empty';
				$scope.createSuccess = null;
				return;
			}

			if($scope.newPronunciationValue == null) {
				$scope.createError = 'Value may not be empty';
				$scope.createSuccess = null;
				return;
			}

			if($scope.data1[$scope.newPronunciationKey] != null) {
				$scope.createError = 'Key already exists. Please use the update function next to the existing entry';
				$scope.createSuccess = null;
				return;
			}

			$scope.createError = null;
			$scope.createSuccess = 'Key has been successfully added to the Pronunciation Data';
			$scope.data1[$scope.newPronunciationKey] = $scope.newPronunciationValue;
			alert($scope.newPronunciationKey + " : " + $scope.newPronunciationValue);
			$scope.newPronunciationKey = '';
			$scope.newPronunciationValue = '';

		};

		$scope.remove = function(key) {
			alert(key);
			delete $scope.data1[key];
		}

		$scope.data1 = {'a': 'alpha', 'b': 'bravo', 'c': 'charlie', 'd': 'delta', 'e': 'echo', 'f': 'foxtrott', 'g': 'golf', 'h': 'hotel', 'i': 'india', 'j': 'juliet', 'k': 'kilo', 'l': 'lima', 'm': 'mike', 'n': 'november', 'o': 'oskar', 'p': 'papa', 'q': 'quebec', 'r': 'romeo', 's': 'sierra', 't': 'tango', 'u': 'uniform', 'v': 'victor', 'w': 'whiskey', 'x': 'xray', 'y': 'yankee', 'z': 'zulu', 'ä': 'alpha-echo', 'ö': 'oskar-echo', 'ü': 'union-echo', 'ß': 'sierra-sierra', '0': 'Zero', '1': 'One', '2': 'Two', '3': 'Three', '4': 'Four', '5': 'Five', '6': 'Six', '7': 'Seven', '8': 'Eight', '9': 'Nine', '-': '- (Dash)', '.': '. (Point)', ',': ', (Comma)', '!': '! (Exclamation Mark)', '$': '$ (Dollar)', '"': '" (Double Quote)', '§': '§ (Paragraph)', '%': '% (Percent)', '&': '& (Ampersand)', '/': '/ (Slash)', '=': '= (Equals)', '?': '? (Question Mark)', '#': '# (Hash Sign)', ':': ': (Colon)', ';': '; (Semicolon)', '*': '* (Asterisk)', '(': '( (Round Bracket)', ')': ') (Round Bracket)', '[': '[ (Square Bracket)', ']': '] (Square Bracket)', '_': '_ (Underscore)', '\\': '\\ (Backslash) ', '|': '| (Pipe)', '~': '~ (Tilt)', '@': '@ (AT Sign)', '€': '€ (Euro Sign)'};
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