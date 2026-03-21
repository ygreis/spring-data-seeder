# spring-data-seeder

`spring-data-seeder` is a small Kotlin-first library for organizing and running database seeders in Spring Boot applications.

It focuses on a simple model:

- define seeders as Spring beans
- orchestrate them explicitly from one aggregate seeder
- run everything through Spring, not custom reflection-heavy infrastructure
- use small factories when test or demo data needs structure

## What problem it solves

Seeding data in Spring Boot projects often drifts into ad hoc scripts, startup-only code, or large one-off bootstrap classes.

This project keeps seeding explicit and readable:

- each seeder is a normal Spring bean
- one aggregate seeder controls execution flow
- seeders can be executed from Gradle without turning seeding into always-on startup behavior
- factories keep repetitive entity creation concise

## Benefits

- Small API surface
- Idiomatic Kotlin
- Works with standard Spring Boot beans and repositories
- Clear separation between orchestration and individual seed logic
- Supports targeted execution of a single seeder
- Includes a practical `example` module with Spring Boot, JPA, H2, and factories

## How it works

At a high level, the library provides three building blocks:

1. `Seeder`
   A minimal contract for executable seeders.

2. `AbstractDatabaseSeeder`
   A base class for an aggregate seeder that orchestrates other seeders with `call<T>()`.

3. Factory support
   `AbstractFactory<T>` and `FactoryCollectionBuilder<T>` provide a small API for generating entities with `make()` and `times(n).make()`.

Runtime execution is controlled by Spring Boot properties:

- `spring.data.seeder.enabled`
- `spring.data.seeder.target`

When seeding is enabled:

- if `target` is set, the library runs the matching `Seeder`
- if `target` is not set, the library runs the single bean that extends `AbstractDatabaseSeeder`

## Installation and initial setup

The repository already includes a working multi-module setup:

- `spring-data-seeder`: the library
- `spring-data-seeder-gradle-plugin`: the Gradle plugin that adds `seedRun`
- `example`: a runnable Spring Boot application using both

The current repository is the primary integration reference. Public distribution coordinates are not documented yet, so the most accurate setup is the one used by `example`.

### Example Gradle setup

```kotlin
plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("io.github.ygreis.spring-data-seeder")
}

extra["springBootMainClass"] = "com.example.ApplicationKt"

dependencies {
    implementation(project(":spring-data-seeder"))
}
```

### Spring Boot property

Keep seeding disabled by default:

```properties
spring.data.seeder.enabled=false
```

The Gradle task enables it only for seeding runs.

## Quick example

### Define a seeder

```kotlin
interface Seeder {
    val name: String
        get() = this::class.simpleName
            ?: throw IllegalStateException("Seeder must have a simple name")

    fun run()
}
```

### Create an aggregate seeder

```kotlin
@Component
class DatabaseSeeder(
    applicationContext: ApplicationContext,
) : AbstractDatabaseSeeder(applicationContext) {

    override fun run() {
        call<UserSeeder>()
        call<ProductSeeder>()
    }
}
```

### Create a simple seeder

```kotlin
@Component
class UserSeeder(
    private val userFactory: UserFactory,
    private val userRepository: UserRepository,
) : Seeder {

    override fun run() {
        val users = userFactory.times(10).make()
        userRepository.saveAll(users)
    }
}
```

## Running seeders

Run the aggregate seeder:

```bash
./gradlew :example:seedRun
```

This uses the Gradle plugin task based on `JavaExec` and starts the Spring Boot application in non-web mode for seeding only.

## Running a specific seeder

Run one seeder by name:

```bash
./gradlew :example:seedRun -Pseeder=UserSeeder
```

The target matches the `Seeder.name` value, which defaults to the class simple name.

## Factories and seeders

The current factory API is intentionally small:

```kotlin
abstract class AbstractFactory<T> : Factory<T> {
    fun make(): T
    fun make(transform: (T) -> T): T
    fun times(total: Int): FactoryCollectionBuilder<T>
}
```

This supports:

- `make()`
- `make { ... }`
- `times(n).make()`
- `times(n).make { ... }`

In the `example` module:

- `UserFactory` creates fake users
- `ProductFactory` creates fake products
- `UserSeeder` persists users
- `ProductSeeder` loads users and generates two products per user

Example from `ProductSeeder`:

```kotlin
val products = productFactory.times(2).make { product ->
    product.copy(
        userId = user.id,
        name = "${user.name} ${product.name}",
    )
}
```

The library does not persist entities for you. Factories create objects; seeders decide how to save them.

## Reference example

The `example` module is the main source of truth for practical usage. It demonstrates:

- Spring Boot integration
- H2 configuration
- a single aggregate seeder
- targeted seeders
- simple factories
- Gradle execution with `seedRun`

If you are evaluating the project for adoption, start there.

## Current status

The project is functional and already supports the core seeding workflow used in the example application.

Current scope:

- explicit seeder orchestration with `AbstractDatabaseSeeder`
- targeted or aggregate execution through Spring Boot properties
- Gradle task for running seeders
- basic factory support for object generation

What is intentionally not included:

- automatic seeder ordering
- annotation scanning beyond standard Spring beans
- automatic persistence in factories
- complex factory DSLs

## Roadmap

Short-term improvements that make sense for the current scope:

- more test coverage around runtime and plugin behavior
- clearer publishing story for external consumption
- more example scenarios and documentation

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE).

## Contributing

Contributions are welcome. See [CONTRIBUTING.md](CONTRIBUTING.md).

## Code of Conduct

Please read [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before participating in issues or pull requests.
