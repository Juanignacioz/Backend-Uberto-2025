db = db.getSiblingDB('uberto_mongo')
db.conductores.drop();
print('Colecciones reiniciadas para desarrollo');
db.createUser({
    user: 'mongo',
    pwd: 'mongo',
    roles: [
        {
            role: 'admin',
            db: 'uberto_mongo'
        }
    ]
});
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