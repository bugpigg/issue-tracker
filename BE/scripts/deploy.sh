#!/bin/bash

cd /home/ec2-user/issue-tracker/be

CONTAINER_NAME="issue-tracker-be"
IMAGE_NAME="ghcr.io/bugpigg/issue-tracker-be:latest"

# remove prev image
sudo docker stop $CONTAINER_NAME
sudo docker rm $CONTAINER_NAME
sudo docker image rm $IMAGE_NAME

# pull latest image
sudo docker pull $IMAGE_NAME

# start container
sudo docker run --env-file ./env -p 8080:8080 -d -it --name $CONTAINER_NAME $IMAGE_NAME
