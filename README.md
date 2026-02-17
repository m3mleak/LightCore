# RevisCore plugin for RevisCube

## Build: 
```./gradlew jar```
## Run:
```./gradlew runServer```

## Changelog:
### Command Block:
- /sethome -> установка точек дома с проработанной логикой сохранения данных
- /home -> телепортация на установленные точки дома 
- /setspawn -> установка точки спавна с хранением всех параметров точки (admin+)
- /spawn -> тепортация на установленную точку спавна
- /reload -> перезагрузка конфигурации плагина (admin+)
- /setwarp -> установка точки варпа (admin+)
- /warp -> телепортация на точку варпа
- /delhome -> удаление точки дома
- /fly -> включить режим полета (admin+)
- /gm -> смена режима (admin+)
- /ec -> просмотр эндерчеста
- /invsee -> просмотр инвентаря игрока
- /tpa -> телепортация к игроку
- /tpaccept -> принять телепортацию
- /tpdeny -> отклонить телепортацию

### Function Block:
- Телепортация на установленную точку спавна при смерти и для новых игроков

### Persmissions:
- sethome.set -> установка точки дома
- sethome.unlimited -> установка точек дома без лимита
- reviscore.setspawn -> установка точки спавна
- reviscore.reload -> перезагрузка конфигурации
- reviscore.setwarp -> установка точки варпа
- reviscore.fly -> включить полет
- reviscore.gamemode -> смена режима игры
- reviscore.noncooldown-tpa -> телепортация без кд
- reviscore.cooldown-tpa.* -> телепортация с определенным кд