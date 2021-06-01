#!/bin/sh

systemctl stop fortunetelling

systemctl disable fortunetelling

rm /etc/nginx/conf.d/fortunetelling.nginx.http.conf

systemctl restart nginx