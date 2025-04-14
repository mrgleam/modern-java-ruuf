# How to run
1. start both ftp and redis by using podman-compose or docker-compose.

    `podman-compose up -d`

2. run main program

# Upgrade podman ubuntu 22.04
ubuntu_version='22.04'
key_url="https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/unstable/xUbuntu_${ubuntu_version}/Release.key"
sources_url="https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/unstable/xUbuntu_${ubuntu_version}"

echo "deb $sources_url/ /" | sudo tee /etc/apt/sources.list.d/devel:kubic:libcontainers:unstable.list
curl -fsSL $key_url | sudo gpg --dearmor | sudo tee /etc/apt/trusted.gpg.d/devel_kubic_libcontainers_unstable.gpg > /dev/null

sudo apt update
sudo apt install podman

podman run -p 6379:6379 --name some-redis -d redis
podman run -d --name vsftpd -p 2121:21 -p 21100-21110:21100-21110 -e FTP_USER=one -e FTP_PASS=1234 fauria/vsftpd

# Notes (for podman user)
1. lsof -i :6379
