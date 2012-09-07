pushd %~dp0%
hg status
hg pull --insecure
hg update
hg status
hg commit -m "AutoSave"
hg push --insecure
hg status

pause