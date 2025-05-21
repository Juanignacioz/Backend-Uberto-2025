rs.initiate({
    _id: "shard1rs",
    members: [
        { _id: 0, host: "shard1a:27018" }
    ]
});
