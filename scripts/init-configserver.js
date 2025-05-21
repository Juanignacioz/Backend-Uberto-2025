rs.initiate({
    _id: "configrs",
    configsvr: true,
    members: [
        { _id: 0, host: "configsvr1:27019" }
    ]
});
