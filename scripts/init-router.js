db = db.getSiblingDB("uberto_mongo");

sh.addShard("shard1rs/shard1a:27018");
sh.addShard("shard2rs/shard2a:27020");

sh.enableSharding("uberto_mongo");

db.createUser({
    user: "mongo_app",
    pwd: "mongo_app",
    roles: [{ role: "readWrite", db: "uberto_mongo" }]
});

sh.shardCollection("uberto_mongo.conductores", { username: "hashed" });

