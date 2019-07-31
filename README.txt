
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

But one important things is when you run program and try to add to google drive directory, program redirect you to google authentication page
with warning (This app isn't verified) just choose Advanced -> Go to testname (unsafe) and allow everything--- finally it must work.








