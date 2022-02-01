terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "4.8.0"
    }
  }
}

variable "image" {
  type        = string
  default     = "latest"
  description = "Canary API docker image to be deployed"
}

variable "gcp_project_id" {
  type        = string
  nullable    = false
  description = "Google Cloud project id"
}

variable "region" {
  type        = string
  nullable    = false
  description = "Google Cloud project id"
}

provider "google" {
  project = var.gcp_project_id
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