# coding: utf-8
# -*- mode: ruby -*-
# vi: set ft=ruby :


Vagrant.require_version ">= 1.7.2"

# Über benötigte Plugins informieren
["vagrant-vbguest"].each do |plugin|

  if not Vagrant.has_plugin?(plugin)
    raise "#{plugin} is required. Please run `vagrant plugin install #{plugin}`"
  end
end

# Provisions-Skript inline definieren

$provisionScript = <<SCRIPT

export JAVA_VERSION_MAJOR=8
export JAVA_VERSION_MINOR=91
export JAVA_VERSION_BUILD=14
export MYSQL_USER=demouser
export MYSQL_PASSWORD=demopassword
export MYSQL_DB=demo
export JAVA_HOME=/opt/jdk
export M2_HOME=/opt/maven
export PATH=$PATH:"$JAVA_HOME"/bin:"$M2_HOME"/bin

cat <<EOF >> /etc/profile.d/env.sh
export JAVA_HOME=/opt/jdk
export M2_HOME=/opt/maven
export PATH=$PATH:"$JAVA_HOME"/bin:"$M2_HOME"/bin
EOF

apt-get update
apt-get install -y apt-transport-https ca-certificates
apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D

cat <<EOF >> /etc/apt/sources.list.d/docker.list
deb https://apt.dockerproject.org/repo debian-jessie main
EOF

apt-get update
apt-get install -y docker-engine
service docker start

groupadd docker
gpasswd -a vagrant docker
service docker restart

docker run -d -p 5000:5000 --name registry registry:2

docker build -t localhost:5000/base /vagrant/base
docker push localhost:5000/base
docker build -t localhost:5000/jdk8 /vagrant/jdk8
docker push localhost:5000/jdk8
docker build -t localhost:5000/wildfly /vagrant/wildfly
docker push localhost:5000/wildfly
docker build -t localhost:5000/mysql /vagrant/mysql
docker push localhost:5000/mysql

mkdir -p /data/mysql
docker run -d -p=3306:3306 --name=mysql_server -v /data/mysql:/var/data/mysql -t localhost:5000/mysql

apt-get install -y mysql-client

sleep 30

mysql -h 127.0.0.1 -P 3306 -u root -p'root' -e "create user '$MYSQL_USER'@'%' identified by '$MYSQL_PASSWORD'"
mysql -h 127.0.0.1 -P 3306 -u root -p'root' -e "CREATE DATABASE $MYSQL_DB"
mysql -h 127.0.0.1 -P 3306 -u root -p'root' -e "grant all privileges on $MYSQL_DB.* to '$MYSQL_USER'@'%' identified by '$MYSQL_PASSWORD'"

docker stop mysql_server
docker rm mysql_server

apt-get install python-pip -y
pip install docker-compose

cd /vagrant/demo-project
docker-compose up -d

cd $HOME
curl -s -S -O -jksSLH "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/"$JAVA_VERSION_MAJOR"u"$JAVA_VERSION_MINOR"-b"$JAVA_VERSION_BUILD"/jdk-"$JAVA_VERSION_MAJOR"u"$JAVA_VERSION_MINOR"-linux-x64.tar.gz
 tar xf jdk-"$JAVA_VERSION_MAJOR"u"$JAVA_VERSION_MINOR"-linux-x64.tar.gz
 mv $HOME/jdk1."$JAVA_VERSION_MAJOR".0_"$JAVA_VERSION_MINOR" "$JAVA_HOME"
 rm jdk-"$JAVA_VERSION_MAJOR"u"$JAVA_VERSION_MINOR"-linux-x64.tar.gz
 java -version

curl  -s -S -O http://ftp.halifax.rwth-aachen.de/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
tar xf apache-maven-3.3.9-bin.tar.gz
mv ./apache-maven-3.3.9 "$M2_HOME"
rm apache-maven-3.3.9-bin.tar.gz

cd /vagrant/demo-project

mvn clean install -q
docker-compose up -d --build

SCRIPT


Vagrant.configure("2") do |config|

  config.vm.box = "debian/jessie64"
  config.vm.box_version = "= 8.4.0"

  config.vm.network "private_network", ip: "172.28.128.4"

  config.vm.hostname = "docker-demo.local"

  config.vm.provider "virtualbox" do |v|
    v.memory =  4000
    v.cpus = 2
    v.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
  end

  config.vm.synced_folder ".", "/vagrant", type: "virtualbox"
  config.vm.provision "shell", inline: $provisionScript, keep_color: true

end
