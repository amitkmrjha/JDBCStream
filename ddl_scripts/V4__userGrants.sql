GRANT SELECT, INSERT ON event_journal TO supermo;
GRANT SELECT, INSERT, UPDATE, DELETE ON event_tag TO supermo;
GRANT SELECT, INSERT, UPDATE, DELETE ON snapshot TO supermo;
GRANT SELECT, INSERT, UPDATE, DELETE ON durable_state TO supermo;

GRANT SELECT, INSERT, UPDATE, DELETE ON akka_projection_offset_store TO supermo;
GRANT SELECT, INSERT, UPDATE, DELETE ON akka_projection_management TO supermo;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO supermo;