# Подробнее о проекте
Проект реализует систему управления товарами для площадки электронной коммерции.  
При помощи проекта можно заниматься реализацией решений, которые помогают разработчикам и издателям игр. Платформа предоставляет возможность осуществлять управление товарами, таких как, как игры, мерч, виртуальная валюта и др.   
  
По умолчанию на платформе представлено три вида товаров (но список типов товаров может быть легко расширен): Монеты, Мерч, Игры.  
Товар определяется уникальным идентификатором, а также обязательно должен иметь уникальный SKU, имя, тип, стоимость.

Реализованные REST методы:   
* **Создание товара.**  
  Метод генерирует и возвращает уникальный идентификатор товара.
* **Редактирование товара.**   
  Метод изменяет все данные о товаре по его идентификатору или SKU.
* **Удаление товара.**  
  Метод удаляет товар по его идентификатору или SKU.
* **Получение информации о товаре.**   
  Метод возвращает товар по его идентификатору или SKU.
* **Получение каталога товаров.**  
  Метод возвращает список всех добавленных товаров.  
  Предусмотрена возможность получения товаров постранично.  
  Также предусмотрена возможность сортировки товаров по типу товара и стоимости (как совместно, так и раздельно) - по умолчанию сортировка по идентификатору.

# Heroku
Проект развернут на публичном хостинге Heroku: https://estore-xsolla-prod.herokuapp.com  
С API можно ознакомиться тут: https://estore-xsolla-prod.herokuapp.com/swagger-ui/index.html

# Локальный запуск
Перед запуском необходимо убедиться, что на ПК установлено: Java 11 (JAVA_HOME должно быть указано), Maven.  
В командной строке, находясь в директории приложения, запустить:  
```mvnw -DskipTests clean dependency:list install```
После запуска приложение доступно по адресу: http://localhost:8080
С API можно ознакомиться тут: http://localhost:8080/swagger-ui/index.html
