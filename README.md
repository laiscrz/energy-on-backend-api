# 🌱⚡ **TáLigado: EnergyOn**  

**TáLigado: EnergyOn** é uma API inteligente para **monitoramento e gerenciamento de energia elétrica e emissões de carbono**, ajudando empresas e indústrias a otimizar recursos e adotar práticas sustentáveis. 🌍💡

## 🚀 **Funcionalidades**

- **📊 Monitoramento de Consumo de Energia**  
  Obtenha dados em tempo real sobre o consumo energético da sua organização, permitindo identificar padrões e tomar decisões mais assertivas.  

- **🌿 Controle de Emissões de Carbono**  
  Acompanhe as emissões de CO₂ capturadas pelos sensores, promovendo a implementação de práticas mais sustentáveis e alinhadas com objetivos ecológicos.  

- **⏱️ Análises em Tempo Real**  
  Detecte picos de consumo e emissões instantaneamente e receba alertas automatizados, garantindo eficiência no uso dos recursos.  

- **📥 Inserção de Dados Automatizada**  
  A API realiza a inserção de dados de consumo e emissões utilizando **procedures** otimizadas, garantindo integridade e desempenho nas operações do banco de dados.  

- **📤 Exportação de Dados em JSON**  
  Exporte seus dados de consumo e emissões no formato **JSON** através do endpoint dedicado `/export/json`, facilitando a integração com outros sistemas ou análises externas.

---


## 🛠️ **Tecnologias Utilizadas**

- **Java** ☕  
- **Spring Boot** 🌱  
- **Oracle Database** 🗄️  
- **Thymeleaf** 💻  
- **Swagger** 🛠️  
- **Lombok** 🧑‍💻  
- **Maven** ⚙️  
- **Spring Data JPA** 📊  
- **Spring Boot DevTools** 🛠️


---

### 1️⃣ **Clonando o Repositório**  
Clone o repositório para sua máquina local:  
```bash
git clone https://github.com/laiscrz/energy-on-api.git
```

### 2️⃣ **Abrindo o Projeto na IDE de sua Preferência**  
Recomenda-se o uso de **IntelliJ IDEA** ou **Eclipse** para rodar o projeto.  

- Abra a IDE.  
- Clique em **File > Open** e selecione o diretório do repositório clonado.  
- Aguarde a IDE importar as dependências (o Maven fará isso automaticamente).  

### 3️⃣ **Configurando o Ambiente**  
Edite o arquivo `application.properties`, localizado em:  
```
src/main/resources/application.properties
```
Certifique-se de ajustar as credenciais do banco de dados Oracle de acordo com seu ambiente.  

### 4️⃣ **Executando o Projeto na IDE**  
- Localize a classe principal do projeto (`EnergyOnApiApplication.java`) em:  
  ```
  src/main/java/com/energyon/EnergyOnApiApplication.java
  ```
- Clique com o botão direito na classe e selecione **Run 'EnergyOnApiApplication'**.  

### 5️⃣ **Acessando a API e a Documentação**  
Após iniciar a aplicação:  
- Acesse o **Swagger UI** para visualizar a documentação e testar os endpoints:  
  **URL:** `http://localhost:8080/swagger-ui/index.html`  
- Explore os endpoints com ferramentas como **Postman**, **Insomnia** ou diretamente pelo navegador.  

----

## 🫂 Integrantes

Aqui estão os membros do grupo que participaram durante desenvolvimento desta GS.

* **RM 552258 - Laís Alves da Silva Cruz**
  - Turma: 2TDSPH

* **RM 552267 - Bianca Leticia Román Caldeira**
  - Turma: 2TDSPH

* **RM 552252 – Charlene Aparecida Estevam Mendes Fialho**
  - Turma: 2TDSPH

* **RM 97916 – Fabricio Torres Antonio**
  - Turma: 2TDSPH

* **RM 99675 – Lucca Raphael Pereira dos Santos**
  - Turma: 2TDSPZ
  - PROFESSOR: Milton Goya
    
<table>
  <tr>
      <td align="center">
      <a href="https://github.com/laiscrz">
        <img src="https://avatars.githubusercontent.com/u/133046134?v=4" width="100px;" alt="Lais Alves's photo on GitHub"/><br>
        <sub>
          <b>Lais Alves</b>
        </sub>
      </a>
      </td>
      <td align="center">
      <a href="https://github.com/biancaroman">
        <img src="https://avatars.githubusercontent.com/u/128830935?v=4" width="100px;" border-radius='50%' alt="Bianca Román's photo on GitHub"/><br>
        <sub>
          <b>Bianca Román</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/charlenefialho">
        <img src="https://avatars.githubusercontent.com/u/94643076?v=4" width="100px;" border-radius='50%' alt="Charlene Aparecida's photo on GitHub"/><br>
        <sub>
          <b>Charlene Aparecida</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Fabs0602">
        <img src="https://avatars.githubusercontent.com/u/111320639?v=4" width="100px;" border-radius='50%' alt="Fabricio Torres's photo on GitHub"/><br>
        <sub>
          <b>Fabricio Torres</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/LuccaRaphael">
        <img src="https://avatars.githubusercontent.com/u/127765063?v=4" width="100px;" border-radius='50%' alt="Lucca Raphael's photo on GitHub"/><br>
        <sub>
          <b>Lucca Raphael</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
