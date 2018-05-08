# kimiko - Changelog

## 1.0.0

 - Initial release.

### 1.0.1
 
 - Added `CommandContext#sendMessage(String)`.
 - Renamed `CommandContext#testPermission(String)` to `CommandContext#hasPermission(String)`.
 
### 1.0.2

 - Bug fixes (Replace `Command` by `Command<S>` in `CommandExecutor` and `CommandTabCompleter`)
 
### 1.0.3

 - Bug fixes: support command executing with no arguments in the String array `args`.
 
### 1.0.4

 - Bug fixes: permission was not handled properly.
 
### 1.0.5

 - Updates lambdajcommon to `1.4.8`.
 
### 1.0.6

 - Updates lambdajcommon to `1.4.9`
 
### 1.0.7

 - Adds toString implementation to `Command<S>`.
 
### 1.0.8

 - HTML 5 javadoc
 - lambdajcommon 1.4.10