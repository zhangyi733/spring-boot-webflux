module "ecs_service" {
  source = "git::https://github.com/bnc-projects/terraform-ecs-service.git?ref=1.1.0"
  alarm_actions = [
    data.terraform_remote_state.market_data.outputs.alert_topic_arn
  ]
  application_path         = "/sbjb"
  cluster_name             = data.terraform_remote_state.market_data.outputs.ecs_cluster_name
  docker_image             = format("%s:%s", data.terraform_remote_state.ecr.outputs.repository_url, var.service_version)
  external_lb_listener_arn = data.terraform_remote_state.market_data.outputs.external_lb_https_listener_arn
  external_lb_name         = data.terraform_remote_state.market_data.outputs.external_lb_name
  internal_lb_listener_arn = data.terraform_remote_state.market_data.outputs.internal_lb_https_listener_arn
  internal_lb_name         = data.terraform_remote_state.market_data.outputs.internal_lb_name
  java_options             = format("-javaagent:newrelic/newrelic.jar -Dnewrelic.environment=%s -Dnewrelic.config.file=newrelic/newrelic.yml", terraform.workspace)
  is_exposed_externally    = false
  priority                 = 50
  service_name             = var.service_name
  splunk_token             = var.splunk_token
  splunk_url               = var.splunk_url
  spring_profile           = terraform.workspace
  vpc_id                   = data.terraform_remote_state.market_data.outputs.vpc_id
  tags                     = merge(local.common_tags, var.tags)
}

