#!/bin/sh

## build the jar ##
gradle bootJar

## add sscon to service ##
echo y | cp ./fortunetelling.service /etc/systemd/system/
systemctl daemon-reload
systemctl enable fortunetelling
#systemctl start fortunetelling
systemctl restart fortunetelling

## add config to nginx ##
echo y | cp ./fortunetelling.nginx.http.conf /etc/nginx/conf.d/
systemctl restart nginx ## please make sure nginx installed ##
