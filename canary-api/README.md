### Build
`./gradlew bootJar`

### Build image
`docker build -t gcr.io/canary-project-339802/canary-api:latest .`

### Deploy
`gcloud run deploy canary-api --image gcr.io/canary-project-339802/canary-api --region southamerica-west1 --allow-unauthenticated --port 8081`
