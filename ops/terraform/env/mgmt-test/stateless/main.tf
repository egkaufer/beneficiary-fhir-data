terraform {
  required_version = "~> 0.12"
}

provider "aws" {
  version = "~> 2.25"
  region = "us-east-1"
}

module "stateless" {
  source = "../../../modules/mgmt_stateless"

  env_config = {
    env               = "mgmt-test"
    tags              = {application="bfd", business="oeda", stack="mgmt", Environment="mgmt-test"}
  }  

  jenkins_ami            = var.jenkins_ami
  jenkins_key_name       = var.jenkins_key_name
  jenkins_instance_size  = var.jenkins_instance_size
  azs                    = var.azs
}
