
# TODO for Making Code Runnable

## Backend Changes
- [x] Switched database from MySQL to H2 in-memory for quick runability
- [x] Updated pom.xml to include H2 dependency and remove Flyway dependencies
- [x] Updated application.yml to use H2 datasource and disable Flyway
- [x] Backend now runs on http://localhost:8080

## Frontend Changes
- [x] Installed npm dependencies
- [x] Frontend runs on http://localhost:5174 (Vite dev server)

## Issues Fixed
- Database connection issue: Switched to H2 to avoid MySQL setup
- Flyway configuration conflict: Removed Flyway dependencies
- CORS configured for frontend ports

## Next Steps
- Test API endpoints via frontend or tools like Postman
- If MySQL is preferred, set up MySQL server and revert changes
- Add Flyway migrations for H2 if needed for data seeding
