### Start MySQL Container
```
docker compose up -d
```


### Docker MySQL manual table creation

```d
docker exec -it mysql-parking-lot mysql -u admin -p
```

### After that enter 

```
admin123
```

```
USE parking_lot;

SHOW TABLES;
```
