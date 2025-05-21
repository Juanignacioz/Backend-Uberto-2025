rs.initiate({
    _id: "shard2rs",
    members: [
        { _id: 0, host: "shard2a:27020" }
    ]
});
