Это приложение-чат для технотрека. Работает под mac, но, видимо, и с запуском Linux проблем быть не должно.

Чтобы собрать код, установите maven и сделайте

```
mvn package
```

Чтобы запустить сервер, запустите

```
./runserver.sh
```

Чтобы запустить клиент, запустите

```
./runclient.sh
```

**Важно:** скрипты запуска клиента и сервера заработают только после выполнения сборки мавеном!


TODO
====

Тут несколько пунктов TODO, которые были записаны в блокнот.

+ pom — добавить профили для запуска клиента и сервера
+ добавить команды входа в чат и выхода из чата
+ добавить jdbc