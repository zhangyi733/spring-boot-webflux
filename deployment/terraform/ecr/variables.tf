variable "aws_default_region" {
  type    = "string"
  default = "us-west-2"
}

variable "profile" {
  type    = "string"
  default = "default"
}

variable "role_arn" {
  type        = "string"
  description = "The role to assume to access the terraform remote state"
}

variable "service_name" {
  type        = "string"
  description = "The name of the service. This will be used for the ecr repository name"
}

variable "tags" {
  type        = "map"
  description = "A map of tags to add to all resources"
  default     = {}
}
