Это приложение-чат для технотрека. Работает под mac, но, видимо, и с запуском Linux проблем быть не должно.

Чтобы собрать код, установите maven и сделайте

'''
mvn package
'''

Чтобы запустить сервер, запустите

'''
./runserver.sh
'''

Чтобы запустить клиент, запустите

'''
./runclient.sh
'''

**Важно:** скрипты запуска клиента и сервера заработают только после выполнения сборки мавеном!