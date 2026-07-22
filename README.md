# SplitH — Sistema de Controle Financeiro Compartilhado

Projeto de TCC (Trabalho de Conclusão de Curso — ADS/UniCesumar). Aplicação para controle financeiro compartilhado entre usuários e grupos, permitindo registrar despesas, dividir valores entre participantes e acompanhar saldos de forma colaborativa.

Desenvolvido em dupla por [CrisHeinzen](https://github.com/CrisHeinzen) e [LuisRuediger](https://github.com/LuisRuediger).

## Visão geral

O SplitH é dividido em duas partes:

- **Front/** — aplicação frontend em Angular
- **Back/splith/** — API backend em Java com Spring Boot

## Status do projeto

🚧 Em desenvolvimento. Já implementado:
- Estrutura inicial do backend (Spring Boot, camadas de autenticação)
- Tela de login no frontend

Em construção:
- Fluxos de cadastro de despesas e divisão de valores entre participantes
- Autenticação completa e controle de permissões por grupo

## Tecnologias

| Camada | Stack |
|---|---|
| Backend | Java, Spring Boot, Maven/Gradle, Lombok |
| Frontend | Angular, TypeScript, Tailwind CSS, PrimeNG |
| Banco de Dados | PostgreSQL |

## Estrutura do repositório

```
SplitH/
├── Front/          # Aplicação Angular
├── Back/splith/    # API Java (pacote com.tcc.splith)
└── README.md
```

## Pré-requisitos

- Node.js 16+
- npm, yarn ou pnpm
- Java JDK 17+
- Maven ou Gradle
- PostgreSQL (local ou via Docker)

## Rodando em desenvolvimento

### Frontend

```bash
cd Front
npm install
npx ng serve
```
Acesse em `http://localhost:4200`.

### Backend

```bash
cd Back/splith
./mvnw spring-boot:run
# ou, se usar Gradle:
./gradlew bootRun
```
API disponível em `http://localhost:8080` (confira a porta em `application.properties`/`application.yml`).

## Configuração de ambiente

O backend espera variáveis de configuração de banco de dados e de autenticação. Crie um arquivo `.env` ou configure diretamente em `application.properties`, por exemplo:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/splith
SPRING_DATASOURCE_USERNAME=<seu_usuario>
SPRING_DATASOURCE_PASSWORD=<sua_senha>
JWT_SECRET=<gere_uma_chave_segura>
SERVER_PORT=8080
```

> Nunca faça commit de credenciais reais — use `.env` ou variáveis de ambiente do sistema, e adicione esses arquivos ao `.gitignore`.

## Build para produção

**Frontend:**
```bash
cd Front
npx ng build --configuration production
```
Artefatos gerados em `Front/dist/`.

**Backend:**
```bash
cd Back/splith
mvn clean package
java -jar target/splith.jar
```

## Testes

```bash
# Frontend
cd Front && npx ng test

# Backend
cd Back/splith && mvn test
```

## Roadmap

- [ ] Documentar endpoints com Swagger/OpenAPI
- [ ] Adicionar `docker-compose` para subir frontend, backend e banco juntos
- [ ] Implementar autenticação completa e permissões por grupo
- [ ] Testes automatizados de ponta a ponta

## Contribuindo

1. Faça um fork do repositório
2. Crie uma branch: `feature/nome-da-feature`
3. Adicione testes e documentação quando aplicável
4. Abra um Pull Request descrevendo as mudanças

## Autores

- [Cristiano Heinzen](https://github.com/CrisHeinzen)
- [Luis Ruediger](https://github.com/LuisRuediger)
