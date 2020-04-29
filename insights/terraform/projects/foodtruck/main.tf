locals {
  tags      = {business = "OEDA", application = "bfd-insights", project="foodtruck"}
  database  = "foodtruck"
  table     = "purchases"
}

## Bucket for the project
module "bucket" {
  source          = "../../modules/bucket"
  name            = "foodtruck"
  sensitivity     = "moderate"
  tags            = local.tags
}

## Firehose for gathering purchase data

module "firehose" {
  source          = "../../modules/firehose"
  stream          = local.table
  database        = local.database
  bucket          = module.bucket.id
  buffer_interval = 60
  tags            = local.tags
}

## Database for the purchase data

module "database" {
  source          = "../../modules/database"
  database        = local.database
  bucket          = module.bucket.id
  tags            = local.tags
}

module "table" {
  source          = "../../modules/table"
  database        = module.database.name          # adds a dependency
  table           = local.table
  bucket          = module.bucket.id
  tags            = local.tags
  partitions      = module.firehose.partitions
  columns = [
    {name="timestamp", type="timestamp", comment="Time of purchase ISO format"},
    {name="truck", type="string", comment="Food truck"},
    {name="state", type="string", comment="State 2-letter abbreviation"},
    {name="hamburgers", type="int", comment="Number of hamburgers"},
    {name="hot_dogs", type="int", comment="Number of hot dogs"},
    {name="curly_fries", type="int", comment="Number of curly fries"},
    {name="cokes", type="int", comment="Number of cokes"},
    {name="shakes", type="int", comment="Number of shakes"}
  ] 
}