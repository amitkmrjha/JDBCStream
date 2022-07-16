GRANT SELECT, INSERT ON event_journal TO "migration-onboarding";
GRANT SELECT, INSERT, UPDATE, DELETE ON event_tag TO "migration-onboarding";
GRANT SELECT, INSERT, UPDATE, DELETE ON snapshot TO "migration-onboarding";
GRANT SELECT, INSERT, UPDATE, DELETE ON durable_state TO "migration-onboarding";

GRANT SELECT, INSERT, UPDATE, DELETE ON akka_projection_offset_store TO "migration-onboarding";
GRANT SELECT, INSERT, UPDATE, DELETE ON akka_projection_management TO "migration-onboarding";

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO "migration-onboarding";