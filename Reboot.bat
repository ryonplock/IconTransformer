@echo off
adb kill-server
adb start-server
adb reboot
exit