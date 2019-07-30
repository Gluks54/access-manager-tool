
This program allow you add members of team to GitLab projects, Trello board and share GoogleDrive directory;

How to setting up project? You should just follow next instruction...

1. Trello settings:
Look at configuration.json file. The important things is 'projectId' and 'key'.
You can check 'key' there: https://trello.com/app-key,
'projectId'(or id of the Board) you can get by Get request:
https://api.trello.com/1/members/{usrName}?key={your key}

2. GitLab settings:
Again come back to configuration.json file...
projectId,userName,pass(your password) - all of that you can find in your GitLab page;
If you want add more just add to gitlab array same fields and program handle it;

3. GoogleDrive settings:
fileId - that is Id of the folder which should be share
https://developers.google.com/drive/api/v3/reference/files/list - just use that page for getting list of id your files
(you don't should impute any param for request);

Go to google console(https://console.developers.google.com) create a new project after that choose create  credentials-> OAith clientId->Configure consent screen->
write app name-> you should add scope if you want add missing scope go to Google API Library -> find Google Drive API(enable Api)
After that again come back to credentials->Configure consent screen-> add scope and you can find (/auth/drive) scope choose it. -> save it;
That is come back us to previous page now press button "create credentials"->OAith clientId-> web app. In Authorised redirect URIs impute (http://localhost:8888/Callback) - create button.

Now you can download json file and copy/paste to your credential.json file - It should be work;
But one important things is when you run program and try to add to google drive directory program redirect you to google authentication page
with warning (This app isn't verified) just choose Advanced -> Go to testname (unsafe) and allow everything--- finally it must work.








