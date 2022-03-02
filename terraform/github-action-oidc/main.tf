variable "project_id" {
  type        = string
  nullable    = false
  description = "Google Cloud project id"
}

resource "google_service_account" "sa" {
  project      = var.project_id
  display_name = "Canary Github Actions Service Account"
  description  = "Canary services account for GitHub Actions"
  account_id   = "canary-gh-actions"
}

resource "google_project_iam_member" "project_containerRegistry" {
  project = var.project_id
  role    = "roles/storage.admin"
  member  = "serviceAccount:${google_service_account.sa.email}"
}

resource "google_project_iam_member" "project_cloudRun" {
  project = var.project_id
  role    = "roles/run.admin"
  member  = "serviceAccount:${google_service_account.sa.email}"
}

resource "google_project_iam_member" "project_serviceAccountTokenCreator" {
  project = var.project_id
  role    = "roles/iam.serviceAccountTokenCreator"
  member  = "serviceAccount:${google_service_account.sa.email}"
}

module "oidc" {
  source      = "terraform-google-modules/github-actions-runners/google//modules/gh-oidc"
  project_id  = var.project_id
  pool_id     = "canary-gh-actions-pool"
  provider_id = "canary-gh-actions-provider"
  sa_mapping = {
    (google_service_account.sa.account_id) = {
      sa_name   = google_service_account.sa.name
      attribute = "attribute.repository/user/repo"
    }
  }
}
