FROM localhost:5000/base

ADD my.cnf /etc/mysql/my.cnf
ADD startmysql.sh /var/startmysql.sh

RUN apk add --update mysql && \
    mkdir -p /etc/mysql/conf.d && \
    mkdir /run/mysqld && \
    rm -rf /var/cache/apk/* && \
    chmod 644 /etc/mysql/my.cnf

VOLUME ["/var/data/mysql"]

EXPOSE 3306

CMD ["/var/startmysql.sh"]
