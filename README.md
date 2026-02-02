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

### Function Block:
- Телепортация на установленную точку спавна при смерти и для новых игроков

### Persmissions:
- sethome.set -> установка точки дома
- sethome.unlimited -> установка точек дома без лимита
- reviscore.setspawn -> установка точки спавна
- reviscore.reload -> перезагрузка конфигурации 
