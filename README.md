# Тестовое задание от компании ООО ИК «СИБИНТЕК»

> Содержание задания

**1-ый экран:**
SPLASH - проверка кеша, если пустой или нет интернета то выводим сообщение об отсутствии соединения, иначе берём информацию из кеша. 
Если кеш пустой то запрос на *REST API* в интернет и кеширование.
Во всех последующих запусках приложения запросы на обновления данных не должны выполняться - данные должны браться только из кеша.

**2-ой экран:**
Список стран - список всех стран с флагом и названием.

**3-ий экран:** 
Открывается при клике по стране в списке стран. На экране должна отображаться следующая информация: флаг, наименование, валюта ,столица.

- UI\UX - на свое усмотрение.

- Желательно применить паттерны (*MVP* или *MVVM*).

- Желательно использование следующих библиотек: *Retrofit, RxJava, Glide, Dagger, Realm или Room*.

- REST API для использования(интеграции) - https://restcountries.eu/
