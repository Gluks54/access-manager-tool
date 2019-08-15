This program allow you add,delete and get status of members GitLab projects, Trello board and GoogleDrive directory;

0.Download project from GitLab and run 'maven package'.
Maven move there jar,scripts,and configuration files to 'dist' folder in your root of project.
Go to the folder and run script;

1. Trello settings:
'key' and 'token' - you can get by next steps:
You should visit next page 'https://developers.trello.com/page/authorization' and press
on the link 'https://trelloUser.com/app-key' copy 'key' after that press again on the link 'Token'
and copy your token...
'projectId'- visit page with your board and add '.json' to URL address and find field 'id';

2. GitLab settings:
'projectId'(your projectId),'userName'(your userName),'pass'(your password) - all of that you can find in your GitLab page;
If you want share more repositories just impute number of repositories, and write data respectively;

3. GoogleDrive settings:
FileId - that is Id of the folder which should be share.
'https://developers.google.com/drive/api/v3/reference/files/list' - just use that page for getting list of 'id' your files
(you don't should impute any param for request);

4.GoogleDrive client settings:
You can download all credentials for your GoogleDrive client there:
'https://console.developers.google.com/apis/api/drive/overview'
But first of all you should create your own client.More information you can find there:'https://developers.google.com/drive'
Attention!!!! Your 'Authorised redirect URIs' must be 'http://localhost:8888/Callback';

But one important things is when you run program and try to add to google drive directory, program redirect you to google authentication page
with warning (This app isn't verified) just choose Advanced -> Go to testname (unsafe) and allow everything--- finally it must work;








