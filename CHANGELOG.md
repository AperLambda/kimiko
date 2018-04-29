# kimiko - Changelog

## 1.0.0

 - Initial release.

### 1.0.1
 
 - Added `CommandContext#sendMessage(String)`.
 - Renamed `CommandContext#testPermission(String)` to `CommandContext#hasPermission(String)`.
 
### 1.0.2

 - Bug fixes (Replace `Command` by `Command<S>` in `CommandExecutor` and `CommandTabCompleter`)