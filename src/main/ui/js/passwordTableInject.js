// URL where we will find our RESTful logout service    
function translateChar(character, charCode) {
  mapping = {
    'a': 'alpha',
    'b': 'bravo',
    'c': 'charlie',
    'd': 'delta',
    'e': 'echo',
    'f': 'foxtrott',
    'g': 'golf',
    'h': 'hotel',
    'i': 'india',
    'j': 'juliet',
    'k': 'kilo',
    'l': 'lima',
    'm': 'mike',
    'n': 'november',
    'o': 'oskar',
    'p': 'papa',
    'q': 'quebec',
    'r': 'romeo',
    's': 'sierra',
    't': 'tango',
    'u': 'uniform',
    'v': 'victor',
    'w': 'whiskey',
    'x': 'xray',
    'y': 'yankee',
    'z': 'zulu',
    'ä': 'alpha-echo',
    'ö': 'oskar-echo',
    'ü': 'union-echo',
    'ß': 'sierra-sierra',
    '0': 'Zero',
    '1': 'One',
    '2': 'Two',
    '3': 'Three',
    '4': 'Four',
    '5': 'Five',
    '6': 'Six',
    '7': 'Seven',
    '8': 'Eight',
    '9': 'Nine',
    '-': '- (Dash)',
    '.': '. (Point)',
    ',': ', (Comma)',
    '!': '! (Exclamation Mark)',
    '$': '$ (Dollar)',
    '"': '" (Double Quote)',
    '§': '§ (Paragraph)',
    '%': '% (Percent)',
    '&': '& (Ampersand)',
    '/': '/ (Slash)',
    '=': '= (Equals)',
    '?': '? (Question Mark)',
    '#': '# (Hash Sign)',
    ':': ': (Colon)',
    ';': '; (Semicolon)',
    '*': '* (Asterisk)',
    '(': '( (Round Bracket)',
    ')': ') (Round Bracket)',
    '[': '[ (Square Bracket)',
    ']': '] (Square Bracket)',
    '_': '_ (Underscore)',
    '\\': '\\ (Backslash) ',
    '|': '| (Pipe)',
    '~': '~ (Tilt)',
    '@': '@ (AT Sign)',
    '€': '€ (Euro Sign)'
  };
  
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

function getTableRow(position, character, charCode) {
  var translation = translateChar(character, charCode);
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
      
      // iterate over all characters
      for (i = 0; i < passwordStr.length; i++) {
        var character = passwordStr.charAt(i);
        var charcode = passwordStr.charCodeAt(i);
        tableStr += getTableRow((i + 1), character, charcode);
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
