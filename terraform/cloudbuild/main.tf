variable "project" {
  type        = string
  nullable    = false
  description = "Google Cloud project id"
}

variable "region" {
  type     = string
  nullable = false
}

variable "sa_email" {
  type        = string
  nullable    = false
  description = "Cloud Build service account email"
}

resource "google_storage_bucket" "logs_bucket" {
  name          = "${var.project}-cloud-build-logs"
  project       = var.project
  location      = var.region
  force_destroy = true

  lifecycle_rule {
    condition {
      age = 90 # days
    }
    action {
      type = "Delete"
    }
  }
}

resource "google_cloudbuild_trigger" "build-trigger" {
  project     = var.project
  name        = "canary-api-deploy"
  description = "Canary Api deploy pipeline"
  # included_files  = ["canary-api/**"]
  service_account = "projects/${var.project}/serviceAccounts/${var.sa_email}"

  github {
    owner = "andriuslima"
    name  = "canary-services"
    push {
      branch = "main"
    }
  }

  build {
    logs_bucket = google_storage_bucket.logs_bucket.url
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
        "--tag", "gcr.io/${var.project}/canary-api:latest",
        "--tag", "gcr.io/${var.project}/canary-api:$SHORT_SHA",
      "."]
    }

    step {
      name       = "docker:20.10.12"
      dir        = "canary-api"
      entrypoint = "docker"
      args       = ["image", "push", "gcr.io/${var.project}/canary-api", "--all-tags"]
    }

    step {
      name       = "gcr.io/google.com/cloudsdktool/cloud-sdk:370.0.0"
      dir        = "canary-api"
      entrypoint = "gcloud"
      args = ["run", "deploy", "canary-api",
        "--image", "gcr.io/${var.project}/canary-api:$SHORT_SHA",
        "--region", var.region,
      "--allow-unauthenticated"]
    }

    artifacts {
      images = ["gcr.io/${var.project}/canary-api:latest", "gcr.io/${var.project}/canary-api:$SHORT_SHA"]
    }
  }
}
