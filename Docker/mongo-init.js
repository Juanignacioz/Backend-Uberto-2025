// db = db.getSiblingDB("uberto_mongo");
//
// db.createUser({
//     user: "mongo_app",
//     pwd: "mongo_app",
//     roles: [{ role: "readWrite", db: "uberto_mongo" }]
// });
//
// sh.addShard("shard1rs/shard1a:27018")
// sh.addShard("shard2rs/shard2a:27020")
// sh.enableSharding("uberto_mongo")
// sh.shardCollection("uberto_mongo.conductores", { username: "hashed" })

/*
* // 1. Crear la BD y usuario admin PRIMERO (en 'admin')
db = db.getSiblingDB('admin');
db.createUser({
  user: 'mongo',
  pwd: 'mongo',
  roles: [{
    role: 'dbAdminAnyDatabase',
    db: 'admin'
  }, {
    role: 'readWriteAnyDatabase',
    db: 'admin'
  }]
});

// 2. Luego trabajar con la BD 'uberto_mongo'
db = db.getSiblingDB('uberto_mongo');
db.conductores.drop();
print("✅ Colección 'conductores' eliminada.");

// 3. Opcional: Crear usuario específico para esta BD
db.createUser({
  user: 'mongo_app',
  pwd: 'mongo_app',
  roles: [{
    role: 'readWrite',
    db: 'uberto_mongo'
  }]
});
*
* */

// function sleep(ms) {
//     return new Promise(resolve => setTimeout(resolve, ms));
// }
//
// async function init() {
//     print("Iniciando configuración...");
//
//     // Inicializar replicaciom
//     rs.initiate({
//         _id: "configrs",
//         configsvr: true,
//         members: [
//             { _id: 0, host: "configsvr1:27019" },
//             { _id: 1, host: "configsvr2:27019" },
//             { _id: 2, host: "configsvr3:27019" }
//         ]
//     });
//
//     await sleep(10000); // Espera para que se estabilice
//
//     // Conecta a Shard1a para iniciar replicacion
//     let shard1Conn = new Mongo("mongodb://shard1a:27018");
//     shard1Conn.getDB("admin").runCommand({
//         replSetInitiate: {
//             _id: "shard1rs",
//             members: [
//                 { _id: 0, host: "shard1a:27018" },
//                 { _id: 1, host: "shard1b:27021" },
//                 { _id: 2, host: "shard1c:27022" }
//             ]
//         }
//     });
//
//     await sleep(10000);
//
//     // Conecta a Shard1b para iniciar replicacion
//     let shard2Conn = new Mongo("mongodb://shard2a:27020");
//     shard2Conn.getDB("admin").runCommand({
//         replSetInitiate: {
//             _id: "shard2rs",
//             members: [
//                 { _id: 0, host: "shard2a:27020" },
//                 { _id: 1, host: "shard2b:27023" },
//                 { _id: 2, host: "shard2c:27024" }
//             ]
//         }
//     });
//
//     await sleep(10000);
//
//     // Agregar los shards al mongos
//     let mongosConn = new Mongo("mongodb://mongos:27017");
//     let admin = mongosConn.getDB("admin");
//
//     admin.runCommand({ addshard: "shard1rs/shard1a:27018,shard1b:27021,shard1c:27022" });
//     admin.runCommand({ addshard: "shard2rs/shard2a:27020,shard2b:27023,shard2c:27024" });
//
//     print("✅ Configuración completa");
// }
//
// init();
