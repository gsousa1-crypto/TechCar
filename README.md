# 🚗 TechCar — Marketplace de Veículos Premium

##  1. Sobre o Projeto

**TechCar** é um marketplace de veículos premium desenvolvido como **Projeto 2 da disciplina**.  
A aplicação oferece experiência completa tanto para **administradores** quanto para **clientes**, com autenticação, catálogo dinâmico e API REST documentada.

###  Perfis de Usuário

#### **ADMIN**
- CRUD completo de veículos  
- Upload de imagens  
- Acesso total ao dashboard  
- Gerenciamento de vendas  

#### **USER**
- Navega pela vitrine de veículos  
- Pesquisa, filtra e ordena veículos  
- Realiza compras (gera a entidade `Venda`)

O sistema possui backend robusto em camadas, API REST com Swagger e frontend renderizado com Thymeleaf.

---

##  2. Tecnologias Utilizadas

### **Backend**
- Java 17  
- Spring Boot 3.x  
- Spring Security  
- Spring Data JPA  
- Lombok  

### **Frontend**
- Thymeleaf (com fragmentos de layout)

### **Banco**
- Oracle Database (JDBC)

### **Outros**
- Springdoc OpenAPI  
- Gradle 8+  

---

##  3. Arquitetura

A arquitetura segue o padrão em camadas:

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── nunes
│   │           └── tech_car
│   │               ├── config/
│   │               │   ├── CustomAuthenticationSuccessHandler.java
│   │               │   ├── DataLoader.java
│   │               │   ├── MvcConfig.java       (Para /uploads)
│   │               │   ├── OpenApiConfig.java   (Config. Swagger)
│   │               │   └── SecurityConfig.java  (Config. Spring Security)
│   │               │
│   │               ├── controller
│   │               │   ├── api/
│   │               │   │   ├── VeiculoApiController.java
│   │               │   │   └── VendaApiController.java
│   │               │   └── web/
│   │               │       ├── HomeController.java
│   │               │       ├── VeiculoController.java
│   │               │       └── VendaController.java
│   │               │
│   │               ├── dto/
│   │               │   └── VeiculoDTO.java
│   │               │
│   │               ├── entity/
│   │               │   ├── Usuario.java
│   │               │   ├── Veiculo.java
│   │               │   └── Venda.java
│   │               │
│   │               ├── repository/
│   │               │   ├── UsuarioRepository.java
│   │               │   ├── VeiculoRepository.java
│   │               │   └── VendaRepository.java
│   │               │
│   │               └── service/
│   │                   ├── FileStorageService.java
│   │                   ├── VeiculoService.java
│   │                   └── VendaService.java
│   │
│   └── resources
│       ├── static/
│       │   ├── logo.png
│       │   └── lupa.png
│       ├── templates/
│       │   ├── veiculos/
│       │   │   ├── list.html
│       │   │   ├── form.html
│       │   │   └── detalhes.html
│       │   ├── auth/
│       │   │   └── login.html
│       │   ├── header.html    (Fragmento)
│       │   ├── footer.html    (Fragmento)
│       │   ├── layout.html    (Template Mestre)
│       │   └── home.html
│       └── application.properties
│
└── test
    └── java
        └── com
            └── nunes
                └── tech_car
                    ├── controller/api/
                    │   └── VeiculoApiControllerTest.java
                    └── service/
                        └── VeiculoServiceTest.java

uploads/            (Pasta criada em tempo de execução, ignorada pelo .gitignore)
README.md
build.gradle
```

---

## 🧩 4. Pré-requisitos

- **JDK 17+**  
- **Gradle 8+**  
- **Banco Oracle configurado**

---

##  5. Banco de Dados (Oracle)

O `DataLoader.java` cria automaticamente:

- Usuário **ADMIN**
- Usuário **USER**
- 3 veículos iniciais

---

##  6. Configuração da Aplicação

Edite o arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=SEU_USUARIO_RM
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.paths-to-match=/api/**
```

---

##  7. Instalação e Execução

Clone o repositório:

```bash
git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git
cd tech-car
```

(Build opcional)

```bash
./gradlew clean
```

Executar:

```bash
./gradlew bootRun
```

### Acesso

- **Web App:** http://localhost:8080  
- **Swagger:** http://localhost:8080/swagger-ui.html  

---

## 8. Usuários de Teste

### **Admin**
- Email: **admin@techcar.com**  
- Senha: **admin123**

### **User**
- Email: **user@techcar.com**  
- Senha: **user123**

---

## 9. Segurança & Permissões

### 🔓 Rotas Públicas
- `/`, `/home`, `/login`
- Arquivos estáticos (`/css/**`, `/js/**`, `/images/**`, `/uploads/**`)
- Swagger

### 🔐 Autenticadas (USER ou ADMIN)
- `/app/veiculos`
- `/app/veiculos/{id}`

### Apenas USER
- `/app/comprar/**`

### Apenas ADMIN
- `/app/dashboard`
- `/app/veiculos/novo`
- `/app/veiculos/{id}/editar`
- `/app/veiculos/salvar`
- `/app/veiculos/{id}/excluir`

---

## 10. Rotas Web (Thymeleaf)

| Rota | Descrição |
|------|-----------|
| `/` | Home + vitrine de 3 veículos recentes |
| `/login` | Login |
| `/app/veiculos` | Lista com busca, filtros, ordenação |
| `/app/veiculos/novo` | (Admin) Formulário de criação |
| `/app/veiculos/{id}/editar` | (Admin) Edição |
| `/app/veiculos/salvar` | (Admin) Salvar |
| `/app/veiculos/{id}` | Detalhes |
| `/app/veiculos/{id}/excluir` | (Admin) Deletar |
| `/app/comprar/{id}` | (User) Comprar |
| `/app/dashboard` | (Admin) Dashboard |

---

## 11. API REST (Swagger)

Documentação:  
**http://localhost:8080/swagger-ui.html**

### Veículos — `/api/veiculos`
- `GET /` — Todos  
- `GET /busca` — Filtros + paginação  
- `GET /{id}` — Buscar por ID  
- `POST /` — (Admin) Cria  
- `PUT /{id}` — (Admin) Atualiza  
- `DELETE /{id}` — (Admin) Excluir  

### Vendas — `/api/vendas`
- `GET /minhas-compras` — (User) Histórico do usuário  
- `GET /` — (Admin) Todas as vendas  

---

##  12. Upload de Imagens

- Realizado no formulário de veículos  
- Arquivos armazenados via `FileStorageService`  
- Servidos pela pasta `/uploads` exposta em `MvcConfig`  
- Guarda apenas o path no banco  

---

##  13. Testes Automatizados

### Testes de Unidade
- `VeiculoServiceTest`

### Testes de Integração
- `VeiculoApiControllerTest`

Executar:

```bash
./gradlew test
```

---

##  14. CI (GitHub Actions)

Workflow executa automaticamente:

```bash
./gradlew test
```

em `main` e `develop`.


## 16. Autores

| Integrante                  |  RA            |
|---------------------------- | -------------- |
| Guilherme Sousa dos Santos  | RA: 925114478  |
| Leonardo Cerati do Nasciment| RA: 3025103009 |
| Wagner Henrique de Oliveira | RA: 3025102838 |
| Pedro Kaori Silva Araújo    | RA: 3025104252 |
