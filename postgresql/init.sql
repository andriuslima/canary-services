-- CreateTable
CREATE TABLE "User" (
    "id" SERIAL NOT NULL,
    "username" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "User_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "Follow" (
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "followerId" INTEGER NOT NULL,
    "followingId" INTEGER NOT NULL
);

-- CreateTable
CREATE TABLE "Post" (
    "id" SERIAL NOT NULL,
    "content" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "authorId" INTEGER NOT NULL,
    "parentId" INTEGER,

    CONSTRAINT "Post_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "Follow_followerId_followingId_key" ON "Follow"("followerId", "followingId");

-- AddForeignKey
ALTER TABLE "Follow" ADD CONSTRAINT "Follow_followerId_fkey" FOREIGN KEY ("followerId") REFERENCES "User"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Follow" ADD CONSTRAINT "Follow_followingId_fkey" FOREIGN KEY ("followingId") REFERENCES "User"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Post" ADD CONSTRAINT "Post_authorId_fkey" FOREIGN KEY ("authorId") REFERENCES "User"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Post" ADD CONSTRAINT "Post_parentId_fkey" FOREIGN KEY ("parentId") REFERENCES "Post"("id") ON DELETE SET NULL ON UPDATE CASCADE;

insert into "User"("username") values ('jakeperalta');
insert into "User"("username") values ('charlesboyle');
insert into "User"("username") values ('ginalinetti');

INSERT INTO "Follow"("followerId", "followingId") VALUES (2, 1);
INSERT INTO "Follow"("followerId", "followingId") VALUES (2, 3);
INSERT INTO "Follow"("followerId", "followingId") VALUES (1, 2);
INSERT INTO "Follow"("followerId", "followingId") VALUES (1, 3);
INSERT INTO "Follow"("followerId", "followingId") VALUES (3, 1);

insert into "Post"("content", "authorId") values ('Boyle, they found one of the stolen paintings at her house.', 1);
insert into "Post"("content", "authorId", "parentId") values ('But she says she didnt know how it ended up there. Shes being set up.', 2, 1);
insert into "Post"("content", "authorId", "parentId") values ('Framed! Art joke. Continue.', 1, 2);
insert into "Post"("content", "authorId") values ('If I Die, Turn My Tweets Into A Book.', 3);