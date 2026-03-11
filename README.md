# Liberty + React Starter

A single-deployment starter project using **Open Liberty** (Jakarta EE 10 + MicroProfile 6.1) for the backend and **React + Vite** for the frontend. One `mvn package` produces one WAR with both the API and the UI.

## Architecture

```
Browser → Open Liberty (port 9080)
           ├── /api/*  → JAX-RS REST endpoints (Java)
           └── /*      → React SPA (static files from Vite build)
```

## Tech Stack

| Layer | Technology |
|-------|-----------|
| UI | React 18 + Vite |
| API | JAX-RS 3.1 (restfulWS) |
| JSON | JSON-B 3.0 |
| DI | CDI 4.0 |
| Config | MicroProfile Config 3.1 |
| Health | MicroProfile Health 4.0 |
| API Docs | MicroProfile OpenAPI 3.1 |
| Server | Open Liberty |
| Build | Maven + frontend-maven-plugin |
| Packaging | WAR |

## Quick Start

### Prerequisites

- Java 17+
- Maven 3.9+
- (Node.js is auto-installed by Maven — no global install needed)

### Development (two servers, hot reload)

```bash
# Terminal 1: Start Liberty in dev mode
./mvnw liberty:dev
# → API running on http://localhost:9080/api

# Terminal 2: Start Vite dev server
cd src/main/frontend
npm install
npm run dev
# → UI running on http://localhost:5173
# → API calls proxied to Liberty automatically
```

### Production (single WAR)

```bash
# Build everything (Java + React) into one WAR
./mvnw clean package

# Run it
./mvnw liberty:run
# → http://localhost:9080 serves both API + UI
```

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/claims` | List all claims |
| GET | `/api/claims/{id}` | Get single claim |
| POST | `/api/claims` | Create claim |
| PUT | `/api/claims/{id}` | Update claim |
| DELETE | `/api/claims/{id}` | Delete claim |
| GET | `/api/health` | Health check |
| GET | `/openapi` | OpenAPI spec |
| GET | `/openapi/ui` | Swagger UI |

## Project Structure

```
liberty-react-starter/
├── pom.xml                              # Maven config + frontend-maven-plugin
├── src/main/
│   ├── java/com/example/
│   │   ├── RestApplication.java         # JAX-RS application path (/api)
│   │   ├── model/
│   │   │   └── Claim.java              # Data model
│   │   └── rest/
│   │       ├── ClaimResource.java       # CRUD REST endpoint
│   │       └── HealthResource.java      # Health check
│   ├── frontend/                        # React + Vite app
│   │   ├── package.json
│   │   ├── vite.config.js               # Vite config with API proxy
│   │   ├── index.html
│   │   └── src/
│   │       ├── main.jsx                 # Entry point
│   │       ├── App.jsx                  # Main app component
│   │       ├── index.css                # Styles
│   │       └── components/
│   │           ├── ClaimsTable.jsx      # Claims list table
│   │           └── ClaimForm.jsx        # New claim form
│   ├── liberty/config/
│   │   └── server.xml                   # Liberty server configuration
│   └── webapp/                          # Vite build output copied here
└── target/
    └── liberty-react-starter.war        # Final deployable artifact
```

## How the Build Works

1. `frontend-maven-plugin` installs Node.js locally (no global install)
2. Runs `npm install` in `src/main/frontend`
3. Runs `npm run build` (which runs `vite build`)
4. `maven-resources-plugin` copies `dist/` output to `src/main/webapp/`
5. Maven packages everything into a WAR
6. Liberty serves the WAR — static files at root, API at `/api`

## Sample Claim JSON

```json
{
  "claimant": "Jane Doe",
  "type": "Medical",
  "status": "Pending",
  "amount": 2500.00,
  "dateSubmitted": "2026-03-11",
  "description": "Emergency room visit"
}
```

## Next Steps

- Add a database (JPA + Derby/PostgreSQL)
- Add authentication (MicroProfile JWT or Jakarta Security)
- Add form validation (Bean Validation 3.0)
- Add tests (JUnit 5 + MicroShed Testing)
- Containerize with Docker (`FROM icr.io/appcafe/open-liberty:kernel-slim-java17-openj9-ubi`)
