# Spring data seeder

## Objetivo da Spring data seeder

**spring-data-seeder** é uma biblioteca para aplicações **Spring Boot** que facilita a **população automática do banco de dados** através de funções anotadas, sem necessidade de registrar manualmente cada seeder.

### O que a lib faz:

1. **Descoberta automática de seeders**
   - Toda função ou método anotado com `@Seeder` é detectado automaticamente.
   - Funciona com beans Spring ou funções top-level Kotlin.

2. **Execução ordenada**
   - Seeders são executados em ordem crescente usando `@Order`.
   - Se não houver ordem definida, é usada uma ordem padrão (`Ordered.LOWEST_PRECEDENCE`).

3. **Execução automática e manual**
   - Seeders podem ser executados automaticamente no startup da aplicação.
   - Também podem ser executados manualmente via linha de comando ou flag (`--seed`).

4. **Modularidade e escalabilidade**
   - Cada seeder é independente.
   - Novos seeders podem ser adicionados sem tocar em nenhum arquivo central.

5. **Logs claros e rastreáveis**
   - Exibe qual seeder está sendo executado, início/fim da execução e possíveis erros.
   - Permite acompanhamento e depuração da execução.

6. **Suporte a profiles e execução condicional**
   - Pode configurar para rodar apenas em determinados profiles (ex: dev, test, prod).
   - Possibilidade de pular execução automática quando necessário.

## 1️⃣ Planejamento / Arquitetura

- [ ] Definir objetivo da lib: popular banco de dados automaticamente em Spring Boot via annotations.
- [ ] Definir API pública para usuários: `@Seeder`, `@Order`, `SeederLauncher.runAll()`.
- [ ] Definir suporte a:
  - Funções de classe (beans)
  - Funções top-level Kotlin
  - Execução automática no startup
  - Execução manual via linha de comando ou flag (`--seed`)
- [ ] Definir estrutura de pastas da lib e exemplos:
  - `seeders/`, `core/`, `auto/`, `examples/`

## 2️⃣ Implementação do núcleo

### 2.1 Anotações

- [ ] Criar `@Seeder`
- [ ] Configurar `@Order` (usar Spring nativo)
- [ ] Opcional: adicionar parâmetros como `name` ou `description` para logs

### 2.2 Discoverer

- [ ] Criar `SeederDiscoverer`
  - Descobrir funções anotadas com `@Seeder` em beans Spring
  - Descobrir funções top-level Kotlin
  - Capturar ordem via `@Order`
- [ ] Guardar lista de seeders com referência ao método/função e ordem

### 2.3 Executor

- [ ] Criar `SeederExecutor`
  - Receber lista de seeders do Discoverer
  - Executar em ordem crescente de `@Order`
  - Registrar logs detalhados (`Executando: UserSeeder.run()`)
  - Tratar erros sem interromper execução dos outros seeders

### 2.4 Launcher

- [ ] Criar `SeederLauncher`
  - Orquestra discoverer + executor
  - Permite execução **manual ou automática**
  - Opção de execução condicional (ex.: via flag `--seed`)

## 3️⃣ Integração Spring Boot

- [ ] Criar `SeederAutoRunner`
  - Implementar `ApplicationRunner` ou `SmartLifecycle`
  - Chamar `SeederLauncher.runAll()` no startup
  - Condicional via flag para pular execução se necessário
- [ ] Testar execução no startup de um projeto Spring Boot de exemplo

## 4️⃣ Estrutura de seeders

- [ ] Criar exemplos de seeders:
  - `UserSeeder.kt`
  - `ProductSeeder.kt`
  - `DemoSeeder.kt`
- [ ] Garantir que a execução respeite a ordem com `@Order`
- [ ] Testar funções top-level Kotlin e métodos de classes

## 5️⃣ Logging e UX

- [ ] Adicionar logs claros de início/fim de cada seeder
- [ ] Suporte a logs de erros sem interromper os seeders seguintes
- [ ] Mostrar ordem de execução no log
- [ ] Possível modo “dry-run” para listar seeders sem executar

## 6️⃣ Configuração avançada / extras

- [ ] Permitir execução por **profiles** (dev, test, prod)
- [ ] Suporte a **execução condicional** (`--seed` ou `spring.profiles.active`)
- [ ] Possível integração com seeders parametrizados (ex: quantidade de registros)
- [ ] CLI para gerar seeders automaticamente (opcional)

## 7️⃣ Testes

- [ ] Testar discoverer: todas as funções `@Seeder` são encontradas
- [ ] Testar executor: ordem correta, execução completa
- [ ] Testar runner automático (startup)
- [ ] Testar runner manual (linha de comando)
- [ ] Testar logs e tratamento de erros
- [ ] Testar funções top-level Kotlin

## 8️⃣ Documentação

- [ ] README.md completo:
  - Descrição da lib
  - Estrutura de pastas
  - Exemplo de seeder
  - Instruções de uso automático e manual
  - Vantagens e próximos passos
- [ ] Exemplos de seeders em `examples/`
- [ ] Sugestão de badges (build, maven central, licença)

## 9️⃣ Preparação para publicação

- [ ] Configurar `build.gradle.kts` ou `pom.xml`
- [ ] Configurar publicação para Maven Central / GitHub Packages
- [ ] Testar versão release em projeto de exemplo



## Definições técnicas

1. Precisamos definir se vamos usar um arquivo registrador ou fazer tudo a base de annotations
<img width="841" height="368" alt="image" src="https://github.com/user-attachments/assets/d90a3ed1-65bb-4d60-a5b3-9d5fddd01969" />

2. Infra de pastas da lib

```text
src/main/kotlin/com/spring/data/seeder
├─ seeders/                # Todos os seeders criados pelos devs
│   ├─ UserSeeder.kt
│   ├─ ProductSeeder.kt
│   └─ ... outros seeders
├─ core/                   # Núcleo da lib
│   ├─ SeederFunction.kt    # Interface funcional que representa uma função seeder
│   ├─ SeederRunner.kt      # Executor que roda os seeders
│   └─ annotations/         # Anotações da biblioteca
│       ├─ Seeder.kt
│       └─ Order.kt
├─ launcher/               # Responsável por orquestrar a execução
│   ├─ SeederLauncher.kt   # Combina discoverer e executor
│   └─ SeederDiscoverer.kt # Descobre seeders no contexto Spring ou top-level
├─ auto/                   # Integração automática com Spring Boot
│   └─ SeederAutoRunner.kt  # Roda os seeders automaticamente no startup
├─ examples/               # Exemplos de seeders para referência e testes
│   └─ DemoSeeder.kt
├─ config/                 # Configurações da lib (opcional)
│   └─ SeederConfig.kt

 ```
