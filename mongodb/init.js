db = db.getSiblingDB("CanaryViewApiDB");

db.user.insert({
  userId: 1,
  username: "jakeperalta",
  joinedAt: new Date(),
  updatedAt: new Date(),
});

db.user.insert({
  userId: 2,
  username: "charlesboyle",
  joinedAt: new Date(),
  updatedAt: new Date(),
});

db.user.insert({
  userId: 3,
  username: "ginalinetti",
  joinedAt: new Date(),
  updatedAt: new Date(),
});

db.follow.insert({
  follower: 2,
  following: 1,
  createdAt: new Date(),
  updatedAt: new Date(),
});

db.follow.insert({
  follower: 2,
  following: 3,
  createdAt: new Date(),
  updatedAt: new Date(),
});

db.follow.insert({
  follower: 1,
  following: 2,
  createdAt: new Date(),
  updatedAt: new Date(),
});

db.follow.insert({
  follower: 1,
  following: 3,
  createdAt: new Date(),
  updatedAt: new Date(),
});

db.follow.insert({
  follower: 3,
  following: 1,
  createdAt: new Date(),
  updatedAt: new Date(),
});

db.post.insert({
  postId: 1,
  authorId: 1,
  content: "Boyle, they found one of the stolen paintings at her house.",
  type: "Post",
  createdAt: new Date(),
  updatedAt: new Date(),
});

db.post.insert({
  postId: 2,
  authorId: 2,
  content:
    "But she says she didnt know how it ended up there. Shes being set up.",
  parent: 1,
  type: "QuoteRepost",
  createdAt: new Date(),
  updatedAt: new Date(),
});

db.post.insert({
  postId: 3,
  authorId: 1,
  content: "Framed! Art joke. Continue.",
  parent: 2,
  type: "QuoteRepost",
  createdAt: new Date(),
  updatedAt: new Date(),
});

db.post.insert({
  postId: 4,
  authorId: 3,
  content: "If I Die, Turn My Tweets Into A Book",
  type: "Post",
  createdAt: new Date(),
  updatedAt: new Date(),
});
