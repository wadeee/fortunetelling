#!/bin/sh

## make this file runnable
chmod +x ./*.sh

systemctl stop fortunetelling

systemctl disable fortunetelling

rm /etc/systemd/system/fortunetelling.service

rm /etc/nginx/conf.d/fortunetelling.nginx.http.conf

systemctl restart nginx
