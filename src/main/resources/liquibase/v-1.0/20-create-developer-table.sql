create table "developer" (
 "id" SERIAL PRIMARY KEY,
 "firstname" text not null,
 "lastname" text not null,
 "team_id" INT4 not null,

 CONSTRAINT "developer_team_fkey"
 FOREIGN KEY ("team_id")
 REFERENCES "team" ("id")
 ON DELETE CASCADE
);