# Contributing

Thanks for considering a contribution to `spring-data-seeder`.

## What is useful

Contributions do not need to be large to be valuable. Good contributions include:

- bug reports with a minimal reproduction
- focused pull requests
- documentation improvements
- example improvements
- tests for existing behavior
- small API refinements that fit the current project direction

## Before opening a pull request

Please make sure your change is aligned with the current scope of the project:

- keep the API small
- prefer explicit Spring Boot integration over hidden behavior
- avoid unnecessary abstractions
- do not add features that are not supported by the current architecture unless the change is discussed first

If the change is significant, open an issue first so the direction can be aligned before implementation.

## Reporting bugs

When opening a bug report, include:

- what you expected to happen
- what actually happened
- relevant stack traces or logs
- a minimal sample or reproduction steps when possible

## Proposing improvements

Feature requests are welcome, especially when they are:

- small and concrete
- consistent with the existing design
- supported by a clear use case

Please keep proposals practical. The project intentionally avoids large implicit frameworks around seeding.

## Sending a pull request

When sending a PR:

- keep the change focused
- update documentation when behavior changes
- add or adjust tests when appropriate
- follow the existing Kotlin and Gradle style in the repository

Examples and documentation are valid contributions, not just code.

## Development notes

Useful commands:

```bash
./gradlew clean build
./gradlew :example:seedRun
./gradlew :example:seedRun -Pseeder=UserSeeder
```

## Review expectations

Pull requests may be asked to:

- reduce scope
- simplify the implementation
- align naming or project structure
- remove behavior that goes beyond the intended scope of the library

That is normal for a project that aims to stay small and predictable.
