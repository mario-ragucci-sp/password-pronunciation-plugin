jQuery(document).ready(function(){
	var passwordPronunciationPluginUrl = SailPoint.CONTEXT_PATH + '/plugins/pluginPage.jsf?pn=password-pronunciation-plugin';
    jQuery("ul.navbar-right li:first")
        .before(
            '<li class="dropdown">' +
            '		<a href="' + passwordPronunciationPluginUrl + '" tabindex="0" role="menuitem" title="Pronunciation Settings">' +
            '			<i role="presenation" class="fa fa-key fa-lg example"></i>' +
            '		</a>' +
            '</li>'
        );
});