rs.initiate({
    _id: "configrs",
    configsvr: true,
    members: [
        { _id: 0, host: "configsvr1:27019" },
        { _id: 1, host: "configsvr2:27019" },
        { _id: 2, host: "configsvr3:27019" }
    ]
});
