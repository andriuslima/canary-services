variable "image" {
  type        = string
  default     = "latest"
  description = "Canary API docker image to be deployed"
}

variable "project_id" {
  type        = string
  nullable    = false
  description = "Google Cloud project id"
}

variable "region" {
  type        = string
  nullable    = false
  description = "Google Cloud project region"
}

provider "google" {
  project = var.project_id
  region  = var.region
}

resource "google_cloud_run_service" "default" {
  name     = "canary-api"
  location = var.region

  template {
    spec {
      containers {
        image = var.image
        ports {
          container_port = 8080
        }
      }
    }
  }
}
