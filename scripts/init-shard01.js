rs.initiate({
    _id: "shard1rs",
    members: [
        { _id: 0, host: "shard1a:27018" },
        { _id: 1, host: "shard1b:27021" },
        { _id: 2, host: "shard1c:27022" }
    ]
});
