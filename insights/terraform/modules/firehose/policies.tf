resource "aws_iam_policy" "firehose" {
  name        = local.full_name
  path        = "/bfd-insights/"
  description = "Allow firehose delivery to the ${var.sensitivity} bucket"
  policy      = <<-EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "",
            "Effect": "Allow",
            "Action": [
                "glue:GetTable",
                "glue:GetTableVersion",
                "glue:GetTableVersions"
            ],
            "Resource": "*"
        },
        {
            "Sid": "",
            "Effect": "Allow",
            "Action": [
                "s3:AbortMultipartUpload",
                "s3:GetBucketLocation",
                "s3:GetObject",
                "s3:ListBucket",
                "s3:ListBucketMultipartUploads",
                "s3:PutObject"
            ],
            "Resource": [
                "${local.bucket_arn}",
                "${local.bucket_arn}/databases/${var.database}/*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "kms:GenerateDataKey",
                "kms:Encrypt",
                "kms:Decrypt"
            ],
            "Resource": [
                "arn:aws:kms:us-east-1:${local.account_id}:key/f1619a71-58e3-4ada-92ed-81ecb7b72a42"
            ],
            "Condition": {
                "StringEquals": {
                    "kms:ViaService": "s3.us-east-1.amazonaws.com"
                },
                "StringLike": {
                    "kms:EncryptionContext:aws:s3:arn": [
                        "${local.bucket_arn}/databases/${var.database}/*"
                    ]
                }
            }
        },
        {
            "Sid": "",
            "Effect": "Allow",
            "Action": [
                "logs:PutLogEvents"
            ],
            "Resource": [
                "arn:aws:logs:us-east-1:${local.account_id}:log-group:/aws/kinesisfirehose/${local.full_name}:log-stream:*"
            ]
        },
        {
            "Sid": "",
            "Effect": "Allow",
            "Action": [
                "kinesis:DescribeStream",
                "kinesis:GetShardIterator",
                "kinesis:GetRecords",
                "kinesis:ListShards"
            ],
            "Resource": "arn:aws:kinesis:us-east-1:${local.account_id}:stream/${local.full_name}"
        }
    ]
}
EOF
}