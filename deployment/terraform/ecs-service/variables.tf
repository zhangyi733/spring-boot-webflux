variable "aws_default_region" {
  type    = string
  default = "us-west-2"
}

variable "profile" {
  type    = string
  default = "default"
}

variable "role_arn" {
  type        = string
  description = "The role to assume to access the terraform remote state"
}

variable "tags" {
  type        = map(string)
  description = "A map of tags to add to all resources"
  default     = {}
}

variable "service_name" {
  type        = string
  description = "The name of the ECS service"
}

variable "service_version" {
  type        = string
  description = "The Travis build number"
}

variable "splunk_url" {
  type        = string
  description = "The URL of Splunk"
}

variable "splunk_token" {
  type        = string
  description = "The token used to send log to Splunk collector"
}
