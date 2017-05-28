# Password Pronunciation Plugin
## What is this plugin for?
this plugin injects a table to the change password dialog, assisting the helpdesk when pronuncing the letters of the generated password.

## Installation
This plugin can be installed using the standard procedure. Visit the plugin page and click the button for a new plugin. Then, drag and drop the plugin zipfile onto the upload field. No further configuration is required.

## Usage
Upon generating a new password via "Manage Passwords" page in IdentityIQ 7.1, click on the generated password. This will generate a new table that shows pronunciation information

### Known issues
When generating a new password, then showing pronunciation information, then generating a password again, the pronunciation table is not shown when clicking on the password. This is due to an internal variable that holds information of whether the table is shown. 
Just click the password again, the table will be generated.

## Extending the translation mapping
The plugin is now shipped with database scripts and a UI. To modify pronunciation information or add new keys, click on the newly added key-icon in the upper right. This will bring up a UI where you can modify the existing information according to your requirements.