# Mikä tämä on?

Löysin tietokoneeltani QLMAn alkuperäisen palvelimen lähdekoodeja (qlma/qlma-api).
:)

# README

[![Slack](https://qlma-slackin.herokuapp.com/badge.svg)](https://qlma-slackin.herokuapp.com/)
[![Stories in Ready](https://badge.waffle.io/qlma/server.png?label=ready&title=Ready)](https://waffle.io/qlma/server)
[![Dependencies Status](http://jarkeeper.com/qlma/server/status.png)](http://jarkeeper.com/qlma/server)
![Build status](https://travis-ci.org/qlma/qlma-api.svg)
![QLMA logo](https://raw.githubusercontent.com/qlma/media/master/qlma.png)

QLMA on oppilaitoksille tarkoitettu palvelu jonka tarkoitus on mullistaa oppilaitosten viestintä. Lisätietoa sivustolta: https://storify.com/iiuusit/qlma-n-synty


Tällä hetkellä projekti on kehityksen alla.

# Kehittämään

1. Asenna Java, jos ei ole ennestään
2. Asenna Leiningen - http://leiningen.org/
3. Kloonaa Qlma:n palvelin
4. Käynnistä palvelin

    ```bash
    $ cd $PROJECT_ROOT
    $ lein ring server
    ```

Voit myös asentaa koneellesi Vagrant-ympäristön ja hyödyntää valmiiksi konfiguroitua virtuaalikonetta.

1. Asenna Vagrant (https://www.vagrantup.com/)
2. Kloonaa Qlma:n palvelin GitHubista
3. Siirry komentokehoitteessa projektihakemistosi juureen ja käynnistä Vagrant-virtuaalikone (jälkimmäinen komento avaa SSH-terminaalin virtuaalikoneeseen)

    ```bash
    $ cd $PROJECT_ROOT
    $ vagrant up && vagrant ssh
    ```

4. Siirry virtuaalikoneessa `/vagrant`-hakemistoon ja käynnistä palvelinprosessi

    ```bash
    $ cd /vagrant
    $ lein ring server-headless
    ```

5. Kutsu viestirajapintaa komentoriviltä

    ```bash
    curl -I http://localhost:3000/messages
    ```

    ```bash
    HTTP/1.1 200 OK
    Date: Fri, 26 Feb 2016 20:59:07 GMT
    Content-Type: text/html; charset=UTF-8
    Content-Length: 0
    Server: Jetty(9.2.10.v20150310)
    ```
