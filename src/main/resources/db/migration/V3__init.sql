ALTER TABLE events DROP COLUMN time;
ALTER TABLE events ADD COLUMN time timestamp;
