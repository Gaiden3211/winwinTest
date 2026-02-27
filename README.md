Mini Test: Service Architecture with Auth & Data Processing
Цей проєкт є виконанням тестового завдання для WinWin Travel. Він демонструє мікросервісну архітектуру з двох сервісів на Spring Boot, які взаємодіють між собою через REST, використовують PostgreSQL для зберігання даних та розгортаються за допомогою Docker Compose.

🏗 Архітектура системи
Система складається з трьох основних компонентів:

auth-api (Service A):

Відповідає за реєстрацію та автентифікацію користувачів.

Використовує JWT (JSON Web Token) для захисту ендпоінтів.

Зберігає користувачів та логі запитів у PostgreSQL.

Виступає клієнтом для Сервісу Б.

data-api (Service B):

Внутрішній сервіс для трансформації тексту.

Захищений на рівні Server-to-Server за допомогою секретного заголовка X-Internal-Token.

Реалізує логіку: переведення тексту у верхній регістр та реверс.

PostgreSQL:

Використовується для надійного зберігання даних.

Ідентифікатори реалізовані через UUID для забезпечення унікальності в розподіленій системі.

🛠 Технологічний стек
Java 23 (найновіша версія для максимальної продуктивності).

Spring Boot 4.0.3 (Web, Security, Data JPA).

Lombok (для чистоти коду).

PostgreSQL 17.

Docker & Docker Compose.

JJWT (для роботи з токенами).

🚀 Як запустити проект

Для роботи сервісів та бд потрібний .env файл в якому буде прописано:

POSTGRES_USER= імя користувача
POSTGRES_PASSWORD= праоль бд
POSTGRES_DB= назва бд

INTERNAL_TOKEN= токен
JWT_SECRET= ключ
JWT_EXPIRATION_MS=час у мс

1. Попередня збірка (Maven)
   Оскільки сервіси запаковані в Docker-контейнери, спочатку потрібно зібрати jar файли. Використовуйте Maven Wrapper (додано в кожну папку):


# Збірка Сервісу А
cd auth-api

.\mvnw.cmd clean package -DskipTests

cd ..

# Збірка Сервісу Б
cd data-api

.\mvnw.cmd clean package -DskipTests

cd ..




2. Запуск інфраструктури
   У корені проєкту виконайте команду для підняття всіх контейнерів:


docker compose up -d --build



🧪 Тестування API
1. Реєстрація нового користувача (POST)
   URL: http://localhost:8080/api/auth/register



JSON
{
"email": "engineer@cherkasy.ua",
"password": "secure_password"
}


2. Вхід та отримання токена (POST)
   URL: http://localhost:8080/api/auth/login

JSON
{
"email": "engineer@cherkasy.ua",
"password": "secure_password"
}


Збережіть отриманий token.

3. Обробка тексту (POST - Захищено JWT)
   URL: http://localhost:8080/api/process

Header: Authorization: Bearer <ВАШ_ТОКЕН>

JSON
{
"text": "WinWin Travel"
}
Очікуваний результат: {"result": "LEVART NIWNIW"}.
