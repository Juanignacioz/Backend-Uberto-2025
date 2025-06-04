La primera vez en la terminar de bash(en secuencia):
> docker exec -i configsvr1 mongosh --port 27019 < scripts/init-configserver.js

> docker exec -i shard1a mongosh --port 27018 < scripts/init-shard01.js

> docker exec -i shard2a mongosh --port 27020 < scripts/init-shard02.js

> docker exec -i mongos mongosh --port 27017 < scripts/init-router.js

> la uri: mongodb://mongo_app:mongo_app@localhost:27017/uberto_mongo?authSource=uberto_mongo

Para ver la reparticion ,En mongos puerto 27017 router:

>docker exec -it mongos mongosh --port 27017

swicheamos a la base de datos
> use uberto_mongo 

vemos como estan ocupados:

>db.conductores.getShardDistribution()

Resultado:
![img.png](img.png)

> todo junto:
> 
> docker exec -i configsvr1 mongosh --port 27019 < scripts/init-configserver.js && docker exec -i shard1a mongosh --port 27018 < scripts/init-shard01.js && docker exec -i shard2a mongosh --port 27020 < scripts/init-shard02.js && docker exec -i mongos mongosh --port 27017 < scripts/init-router.js