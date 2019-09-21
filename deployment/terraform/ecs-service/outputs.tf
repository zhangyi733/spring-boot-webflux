output "deployment_role_arn" {
  sensitive = true
  value     = data.terraform_remote_state.market_data.outputs.deployment_role_arn
}

output "ecs_cluster_name" {
  sensitive = true
  value     = data.terraform_remote_state.market_data.outputs.ecs_cluster_name
}
