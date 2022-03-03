resource "google_cloudbuild_trigger" "build-trigger" {
  project     = "canary-project-339802"
  name        = "canary-api-deploy"
  description = "Canary Api deploy pipeline"
  # included_files = ["canary-api/**"]

  github {
    owner = "andriuslima"
    name  = "canary-services"
    push {
      branch = "main"
    }
  }

  build {
    step {
      name       = "gradle:7.3.2-jdk11"
      dir        = "canary-api"
      entrypoint = "gradle"
      args       = ["test"]
    }

    step {
      name       = "gradle:7.3.2-jdk11"
      dir        = "canary-api"
      entrypoint = "gradle"
      args       = ["bootJar"]
    }

    step {
      name       = "docker:20.10.12"
      dir        = "canary-api"
      entrypoint = "docker"
      args = ["build",
        "--tag", "gcr.io/$PROJECT_ID/canary-api:latest",
        "--tag", "gcr.io/$PROJECT_ID/canary-api:$SHORT_SHA",
      "."]
    }

    step {
      name       = "docker:20.10.12"
      dir        = "canary-api"
      entrypoint = "docker"
      args       = ["image", "push", "gcr.io/$PROJECT_ID/canary-api", "--all-tags"]
    }

    step {
      name       = "gcr.io/google.com/cloudsdktool/cloud-sdk:370.0.0"
      dir        = "canary-api"
      entrypoint = "gcloud"
      args = ["run", "deploy", "canary-api",
        "--image", "gcr.io/$PROJECT_ID/canary-api:$SHORT_SHA",
        "--region", "southamerica-west1",
      "--allow-unauthenticated"]
    }

    artifacts {
      images = ["gcr.io/$PROJECT_ID/canary-api:latest", "gcr.io/$PROJECT_ID/canary-api:$SHORT_SHA"]
    }
  }
}
