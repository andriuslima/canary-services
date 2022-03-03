variable "project" {
  type        = string
  nullable    = false
  description = "Google Cloud project id"
}

resource "google_service_account" "sa" {
  project      = var.project
  display_name = "Cloud Build Service Account"
  description  = "Service account for Cloud Build"
  account_id   = "cloud-build"
}

resource "google_project_iam_member" "project_containerRegistry" {
  project = var.project
  role    = "roles/storage.admin"
  member  = "serviceAccount:${google_service_account.sa.email}"
}

resource "google_project_iam_member" "project_cloudRun" {
  project = var.project
  role    = "roles/run.admin"
  member  = "serviceAccount:${google_service_account.sa.email}"
}

resource "google_project_iam_member" "project_cloudBuild" {
  project = var.project
  role    = "roles/cloudbuild.builds.builder"
  member  = "serviceAccount:${google_service_account.sa.email}"
}

resource "google_project_iam_member" "project_serviceAccountTokenCreator" {
  project = var.project
  role    = "roles/iam.serviceAccountTokenCreator"
  member  = "serviceAccount:${google_service_account.sa.email}"
}

output "sa_email" {
  description = "Cloud Build service account email"
  value       = google_service_account.sa.email
}

