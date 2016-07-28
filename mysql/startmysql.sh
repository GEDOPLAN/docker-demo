#!/bin/sh

if [ "$(ls -A /var/data/mysql)" ]; then
  echo ""
else
  mysql_install_db --user=root --datadir=/var/data/mysql
  cat << EOF >> /tmp/script
  FLUSH PRIVILEGES;
  CREATE USER 'root'@'%' IDENTIFIED BY 'root';
  GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
  UPDATE user SET password=PASSWORD("root") WHERE user='root';
  GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' WITH GRANT OPTION;
  UPDATE user SET password=PASSWORD("") WHERE user='root' AND host='localhost';
EOF

 mysqld --user=root --bootstrap < /tmp/script
 rm -f /tmp/script
fi
exec mysqld --user=root
