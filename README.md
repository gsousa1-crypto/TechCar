# 🚗 TechCar --- Marketplace de Veículos Premium

## 1. Sobre o Projeto

**TechCar** é um marketplace de veículos premium desenvolvido como **Projeto 2 da disciplina**.  
A aplicação oferece experiência completa tanto para **administradores** quanto para **clientes**, com autenticação, catálogo dinâmico e API REST documentada.

### Perfis de Usuário

#### **ADMIN**

-   CRUD completo de veículos
-   Upload de imagens
-   Acesso total ao dashboard
-   Gerenciamento de vendas

#### **USER**

-   Navega pela vitrine de veículos
-   Pesquisa, filtra e ordena veículos
-   Realiza compras (gera a entidade `Venda`)

O sistema possui backend robusto em camadas, API REST com Swagger e
frontend renderizado com Thymeleaf.

------------------------------------------------------------------------

## 2. Tecnologias Utilizadas

-   **Java 17+**, Spring Boot 3.x (Web, Data JPA, Security, Validation)
-   **Frontend:** Thymeleaf (com fragmentos de layout)
-   **Banco:** Oracle Database (JDBC), Maven/Gradle
-   **Outros:** Springdoc OpenAPI (Swagger UI), Lombok

------------------------------------------------------------------------

## 3. Arquitetura (resumo)

-   **Camadas:** controller (web/api) → service → repository
-   **DTOs:** Objetos de transferência para entrada/saída de dados
-   **Upload:** Serviço dedicado (`FileStorageService`) para gerenciar
    imagens

A arquitetura segue o padrão em camadas:

``` text
src/main/java/com/nunes/tech_car/
  ├── config/          (Segurança, Swagger, MVC)
  ├── controller/      (Web e API)
  ├── dto/             (VeiculoDTO)
  ├── entity/          (Usuario, Veiculo, Venda)
  ├── repository/      (Interfaces JPA)
  └── service/         (Regras de negócio e Upload)
```

## 4. Requisitos de Ambiente

-   JDK 17+
-   Gradle 8+
-   Banco Oracle configurado

## 5. Configuração do Banco

O projeto utiliza o Oracle Database. O DataLoader.java popula
automaticamente o banco com: - Usuário ADMIN e USER - 3 veículos de
exemplo

## 6. Configuração da Aplicação

Edite o arquivo `src/main/resources/application.properties`:

    spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
    spring.datasource.username=SEU_USUARIO_RM
    spring.datasource.password=SUA_SENHA
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    springdoc.api-docs.path=/v3/api-docs

## 7. Instalação e Execução

Clone o repositório:

    git clone https://github.com/gsousa1-crypto/TechCar.git
    cd tech-car

Execute a aplicação:

    ./gradlew bootRun

Acesse: - App: http://localhost:8080\
- Swagger UI: http://localhost:8080/swagger-ui.html

## 8. Seeds de Usuários

Criados automaticamente:

-   Admin: `admin@techcar.com` / `admin123`
-   User: `user@techcar.com` / `user123`

## 9. Segurança (Rotas)

**Públicas:** `/`, `/login`, `/home`, `/css`, `/js`, `/images`,
`/uploads`\
**Autenticadas:** `/app/veiculos`, `/app/veiculos/{id}`\
**USER:** `/app/comprar/**`\
**ADMIN:** `/app/dashboard`, CRUD veiculos

## 10. Rotas Web

-   `/` Home
-   `/login`
-   `/app/veiculos`
-   `/app/veiculos/novo`
-   `/app/dashboard`

## 11. API REST

Base OpenAPI: `/v3/api-docs`\
Swagger UI: `/swagger-ui.html`

Exemplo:

    GET /api/veiculos/busca?marca=Honda&page=0&size=10

## 12. Upload

Upload no formulário `/app/veiculos/novo`, salvando em `/uploads`.

## 13. Testes

    ./gradlew test

## 14. CI

GitHub Actions configurado.

## 15. Checklist de Reprodutibilidade

\[x\] Ambiente configurado\
\[x\] Seeds funcionando\
\[x\] CRUD e upload funcionando

## 16. Links

Repositório: https://github.com/gsousa1-crypto/TechCar\
Vídeo: https://www.youtube.com/watch?v=q2MdjPLvAsw

## 17. Autores

| Integrante                   | RA             | Contribuição |
|------------------------------|----------------| ------------ |
| Guilherme Sousa dos Santos   | RA: 925114478  | API REST |
| Leonardo Cerati do Nasciment | RA: 3025103009 | Frontend |
| Wagner Henrique de Oliveira  | RA: 3025102838 | Segurança |
| Pedro Kaori Silva Araújo     | RA: 3025104252 | Veículo + Upload |
| Guilherme da Costa Roberto   |   3024102838   |  Venda + Regras |
