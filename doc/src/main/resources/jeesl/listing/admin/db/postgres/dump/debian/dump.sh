#!/bin/sh

file=$(date +"@@@APP@@@.%Y-%m-%d.%H.sql")
pg_dump --create --blobs --format=c --verbose --file=/home/dev/db/dump/@@@APP@@@/$file @@@APP@@@