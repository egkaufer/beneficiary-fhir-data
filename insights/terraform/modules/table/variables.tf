variable "table" {
  description = "Name of the table"
  type        = string
}

variable "database" {
  description = "Name of the database that holds the table"
  type        = string
}

variable "bucket" {
  description = "the bucket that holds the database and table"
  type        = string
}

variable "tags" {
  description = "tags"
  type        = map(string)
}

variable "partitions" {
  description = "list of column objects"
  type        = list(object({name=string, type=string, comment=string}))
}

variable "columns" {
  description = "list of column objects"
  type        = list(object({name=string, type=string, comment=string}))
}




