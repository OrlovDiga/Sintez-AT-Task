[![Build Status](https://travis-ci.org/OrlovDiga/Sintez-AT-Task.svg?branch=master)](https://travis-ci.org/OrlovDiga/Sintez-AT-Task)
# Sintez-AT-Task

RESTful Web Service on Jersey.
Testing task to Java Developer position for Sintez-AT Company.

<details><summary>Technical assignment:</summary>
Сокращатель ссылок Необходимо разработать HTTP-сервис, который генерирует короткие ссылки.

Описание

В сервисе должна быть реализована следующая функциональность:

Генератор ссылок Ресурс /generate должен обрабатывать POST-запрос, содержащий оригинальную ссылку, и генерировать короткую ссылку. Оригинальная ссылка передается в теле POST-запроса в поле “original” JSON-объекта. Сгенерированная короткая ссылка передается в теле ответа в поле “link” JSON-объекта. Сгенерированная короткая ссылка должна иметь формат /l/{some-short-name}, то есть не должна содержать адрес сервера, где some-short-name - идентификатор ссылки. Алгоритм генерации этого идентификатора и его формат остается на усмотрение автора. Пример запроса: POST /generate Пример тела запроса: { “original”: “https://some.com/tert/url?param=1” } Пример ответа: { “link”: “/l/short-name” }

Редирект Ресурс /l/{short-name} должен осуществлять редирект на оригинальный url. Параметры пути запроса:

short-name - идентификатор ссылки Пример запроса: GET /l/short-name

Предлагаем подумать, каким образом можно оптимизировать работу с ссылками, по которым происходит много переходов.

Статистика 3.1 Статистика по конкретным ссылкам Ресурс /stats/{short-name} должен обрабатывать GET-запрос и возвращать статистику переходов по конкретной ссылке. Параметры пути запроса:

some-short-name - идентификатор ссылки В ответе сервиса должен содержаться JSON-объект со следующими полями:

link - сгенерированная короткая ссылка

original - оригинальная ссылка

rank - место ссылки в топе запросов

count - число запросов по короткой ссылке Пример запроса: GET /stats/some-short-name Пример ответа: { “link”: “/l/short-name”, “original”: “http://some.com/tert/url” “rank”: 1, “count”: 4356 } 3.2 Рейтинг ссылок Ресурс /stats должен обрабатывать GET-запрос и возвращать статистику запросов с сортировкой по частоте запросов по убыванию и возможностью постраничного отображения. Параметры строки запроса:

page - номер страницы

count - число записей, отображаемых на странице, максимальное возможное значение 100 (включительно) В ответе сервиса должен содержаться массив из JSON-объектов, описанных в разделе 3.1 Статистика по конкретным ссылкам. Пример запроса: GET /stats?page=1&count=2 Пример ответа: [ { “link”: “/l/short-name”, “original”: “http://server.com/tert/url” “rank”: 1, “count”: 4356 }, { “link”: “/l/some-another-short-name”, “original”: “http://another-server.com/some/url” “rank”: 2, “count”: 43563 } ]

Требования к сервису

В качестве фреймворка использовать Jersey

Запуск сервера должеен производиться из war файла в вебсервисе томкат(можно использовать докер если так удобней)

Необходимо покрыть сервис Unit-тестами. Полнота покрытия остается на усмотрение автора решения.

Приложение должно собираться с использованием Apache Maven.

По возможности необходимо придерживаться REST-архитектуры.

В качестве бд можно выбрать любую удобную(например postgres)
</details>

## Installation
You can launch this web service in several ways.

## First
You need to install docker on your host. Next, create `docker-compose.yml` with the following content:
```
version: '3'

services:
  short_url_war:
    image: privdim/short_url_service
    ports:
      - 8080:8080
    links:
      - "db"
    depends_on:
      - db
  db:
    image: privdim/short_url_db
    ports:
    - 5432:5432
```

Next, you need to navigate in the terminal to the directory where you created docker-compose.yml.
And enter the command `docker-compose up`. Сongratulations, you have launched the application!


### Second
You need to install [tomcat](https://tomcat.apache.org/download-90.cgi) on your host and [postgreSQL](https://www.postgresql.org/download/). Next, you must download this repository.
Configure tomcat configuration. Configure a database connection in `src/main/resources/hibernate.cfg.xml`.
Now you can run the application.

### Third
You need to install [docker](https://docs.docker.com/get-docker/) on your host. Next, you must download this repository.
Next, you need to enter the command in the terminal while in the root of the project:
`docker-compose up`. and that's it, your application has started.


## Usage
This application has multiple entry points:

* POST */generate* - generate short url.
* GET */l/{short-url}* - redirect to original url.
* GET */stats/{short-url}* - statistics for this short url.
* GET */stats?page={page-number}&count={json-object-count-to-page}* - returns statistics for urls.

<details><summary>Request examples:</summary>

#### */generate*
`POST`
```
  request
{
"original": "https://www.google.com/maps/preview",
}

  response
{
    "link": "/l/3"
}
 ```
#### */l/3*
`GET`
```
redirects you to the address: https://www.google.com/maps/preview
 ```

#### */stats/3*
 `GET`
 ```
   response
{
    "shortUrl": "/l/3",
    "originalUrl": "https://www.google.com/maps/preview",
    "callcount": "1",
    "rank": "1"
}
  ```

#### */stats?page=1&count=2*
 `GET`
 ```
   response
{
    "shortUrl": "/l/3",
    "originalUrl": "https://www.google.com/maps/preview",
    "callcount": "1",
    "rank": "1"
},
{
    "shortUrl": "/l/4",
    "originalUrl": "https://hub.docker.com",
    "callcount": "0",
    "rank": "2"
}
  ```

</details>

## License
[MIT](https://github.com/OrlovDiga/Sintez-AT-Task/blob/master/LICENSE)






