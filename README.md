# Weather Forecast API

A weather forecast API built as part of a software company selection process. This project demonstrates the implementation of backend service using modern Java development practices, integration with external services and use of cache.

## 🚀 Features

- Real-time weather forecasts by postal code
- City name resolution using coordinates
- Temperature information (current, max, and min)
- Precipitation data with probability
- Response caching for improved performance
- Comprehensive unit tests
- Docker containerization
- Ready for cloud deployment

## 🛠️ Technologies

- **Java 17**: Modern Java features and performance improvements
- **Spring Boot**: Framework for building robust and scalable applications
- **Spring Web**: RESTful API development
- **Spring Cache**: Response caching with Caffeine
- **Lombok**: Reduces boilerplate code
- **JUnit 5 & Mockito**: Comprehensive unit testing
- **Docker**: Containerization for consistent deployment
- **Render**: Cloud deployment platform

## 🔌 External APIs

- **Open-Meteo**: Weather forecast data
- **Nominatim**: Geocoding service for location resolution

## 🏗️ Architecture

The project follows clean architecture principles with clear separation of concerns:

- **Domain**: Core business logic and entities
- **Application**: Use cases and business rules
- **Infrastructure**: External services integration and implementations
- **Controller**: API endpoints and controllers

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Docker (optional)

### Running with Docker

1. Build the Docker image:

```bash
docker build -t weather-forecast .
```

2. Run the container:

```bash
docker run -p 8080:8080 weather-forecast
```

## 📝 API Endpoints

### Get Weather Forecast

```
GET /api/forecast?country={country}&postalCode={postalCode}
```

Example response:

```json
{
  "cityName": "São Paulo, Brazil",
  "temperature": "25.0 °C",
  "maxTemperature": "28.0 °C",
  "minTemperature": "22.0 °C",
  "precipitation": "0.0 mm (Probability: 10%)",
  "fromCache": false
}
```

## 🧪 Testing

Run the test suite:

```bash
./mvnw test
```

## 🚢 Deployment

The application is configured for deployment on Render using Docker. The deployment process is automated through the `render.yaml` configuration file.

## 📊 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/tech/weatherforecast/
│   │       ├── application/    # Use cases and business rules
│   │       ├── domain/         # Core business logic
│   │       ├── infrastructure/ # External services
│   │       └── controller/   # API controllers
│   └── resources/
└── test/                       # Test cases
```

## 🎯 Project Goals

This project was developed as part of a software consulting company selection process to demonstrate:

- Clean Architecture implementation
- Modern Java development practices
- API integration capabilities
- Testing best practices
- Containerization and deployment knowledge
- Code organization and maintainability

@lucasnlima
