FROM debian:wheezy
MAINTAINER takumon <inoutakumon@gmail.com>

RUN apt-get update && apt-get install -y cowsay fortune
COPY entrypoint.sh /

ENTRYPOINT ["/entrypoint.sh"]
