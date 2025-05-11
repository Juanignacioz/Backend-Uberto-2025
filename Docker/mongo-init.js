db = db.getSiblingDB('uberto_mongo');
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