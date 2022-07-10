# java-filmorate
Template repository for Filmorate project.
![Database schema](https://github.com/hpki/java-filmorate/blob/add-friends-likes/src/main/resources/Untitled.png) 
#### Основная информация о таблицах.
1. **users** - информация о пользователях
2. **friends** - информация о друзьях пользователя. Поле status: true - если дружба между пользователями подтверждена, false - не подтверждена.
3. **films** - информация о фильмах
4. **films_genres** - содержит перечень всех жанров кино
5. **films_mpa** - содержит перечень возрастных рейтингов Ассоциации кинокомпаний (Motion Picture Association, сокращённо МРА)
6. **films_genres** - соотнесение фильма с жанрами.
7. **likes** - перечень лайков, поставленных пользователями фильму.

Примеры запросов к БД:

Получить всех пользователей: SELECT * from users 

Получить пользователя по идентефикатоу SELECT *FROM users WHERE id = ?;

Получить фильм по идентификатору: SELECT * FROM film where id = ?

Получить первые N самых популярных фильмов select * , count(user_id) from film right join likes on (id = film_id) group by film_id order by 2 desc limit N
