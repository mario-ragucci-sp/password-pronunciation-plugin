function returnPronunciationMapping(url) {
	var result = {};
	$.ajax({
		'url': url,
		'type': 'GET',
		'dataType' : 'json',
		'async': false,
		beforeSend: function (request) {
			request.setRequestHeader('X-XSRF-TOKEN', PluginHelper.getCsrfToken());
		},
		//The response from the server
		'success': function (response) {
			result = response;
		}
	});

	return result;
}

function translateChar(mapping, character, charCode) {
	var result = '';
	var translation = mapping[character.toLowerCase()];
	if (translation == null) {
		translation = character;
	}
	result += translation;

	if (charCode >= 65 && charCode <= 90) {
		return result.toUpperCase();
	}
	if(charCode >= 97 && charCode <= 122) {
		return result.toLowerCase();
	}
  
	return result;
}

function isCapital(charCode) {
	var isCapital = false;
	if (charCode >= 65 && charCode <= 90) {
	    isCapital = true;
	}
	
	return isCapital;
}

function removeTable() {
    var tbl = document.getElementById("ppp-table");
    if(tbl) tbl.parentNode.removeChild(tbl);
}

function isCapitalHumanBoolean(charCode) {
	var isCapital = 'No';
	if (charCode >= 65 && charCode <= 90) {
	    isCapital = 'Yes';
	}
	
	return isCapital;
}

function getTableRow(mapping, position, character, charCode) {
	var translation = translateChar(mapping, character, charCode);
	var isCapital   = isCapitalHumanBoolean(charCode);
	var result =  '<tr>' +
		'<td width=\'20px\' align=\'right\'>'+ position +'.</td>' +
		'<td width=\'60px\'>Character</td>' +
		'<td width=\'200px\'><b>'+ translation +'</b></td>' +
		'<td width=\'80px\'>'+ isCapital +'</td>' +
	'</tr>';
  
	return result;
}

jQuery(document).ready(function () {
	var wasclicked = false;
	
	// Create a listener for the future element
	$('body').on('click', 'span[id^="generatedAccountPassword-"]', function () {
		if (!wasclicked) {
			wasclicked = true;
			var span = $('span[id^="generatedAccountPassword-"]');
			var passwordStr = span[0].innerHTML;
	      
	    	// create table
	    	var tableStr = '<table class=\'table\' id=\'ppp-table\'>';
	    	tableStr += '<tr><th>&nbsp;</th><th align=\'right\'>index</th><th>pronunciaton</th><th>capital letter</th></tr>';
	      
	    	var mapping   = returnPronunciationMapping(PluginHelper.getPluginRestUrl('password-pronunciation' + '/mappingtable'));
	    	// iterate over all characters
	    	for (i = 0; i < passwordStr.length; i++) {
	    		var character = passwordStr.charAt(i);
	    		var charcode = passwordStr.charCodeAt(i);
	    		tableStr += getTableRow(mapping, (i + 1), character, charcode);
	    	}
	      
	    	// finish table
	    	tableStr += '</table>';
	      
	    	// append table
	    	span.parent().append(tableStr);
	    } else {
	    	// remove table
	    	wasclicked = false;
	    	removeTable();
	    }
	});
});
