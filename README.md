Сначала склонируйте репозиторий:

**git clone https://github.com/<your-username>/bankcards.git**
**cd bankcards**

Нужно написать свой **.env** файл для работы переменных среды, пример настройки: **env.example**

Далее, для использования этого чудесного сервиса, вам нужны установленные maven, java(21+) и docker.

Соберите jar-файл этого прекрасного проекта:

**mvn clean package -DskipTests**

После этого, все уже написано за вас, зовем докер-композ!

**docker-compose up --build**

Имея наш докер контейнер up and running, заходим на 
  **http://localhost:8080/swagger-ui/index.html**
и наслаждаемся
