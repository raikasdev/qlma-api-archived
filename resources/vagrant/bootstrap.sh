#!/usr/bin/env bash

#
#general set-up
#
if [ ! -f /root/setup-base ]
then

  aptitude update

  # java
  aptitude install -y default-jre

  # common tools
  aptitude install -y vim curl git

  # Leiningen
  curl -sL https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein > /usr/local/bin/lein
  chmod a+x /usr/local/bin/lein


  touch /root/setup-base
else
    echo "Basic stuff already installed"
fi
