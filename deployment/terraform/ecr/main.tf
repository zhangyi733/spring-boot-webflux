terraform {
  required_version = ">= 0.12"
  backend "s3" {
    encrypt = true
  }
}

locals {
  common_tags = {
    Owner       = "bravenewcoin"
    Team        = "Market Data"
    Environment = "production"
  }
}

provider "aws" {
  region  = var.aws_default_region
  version = "~> 2.11.0"
  profile = var.profile

  allowed_account_ids = [
    data.terraform_remote_state.bnc_ops.outputs.bnc_account_ids["operations"],
  ]

  assume_role {
    role_arn     = data.terraform_remote_state.bnc_ops.outputs.deployment_role_arn
    session_name = "terraform"
  }
}

data "terraform_remote_state" "bnc_ops" {
  backend = "s3"
  config = {
    bucket   = "terraform.techemy.co"
    key      = "bnc/ops"
    region   = var.aws_default_region
    profile  = var.profile
    role_arn = var.role_arn
  }
}
