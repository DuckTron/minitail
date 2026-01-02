##  Integração

### Registro de serviços

- Os serviços devem registrar suas instâncias automaticamente no Eureka
- Múltiplas instâncias devem utilizar o mesmo `spring.application.name`
- Cada instância deve possuir um `instance-id` único

### Descoberta de serviços

- Apenas componentes de infraestrutura realizam *service discovery*
- Serviços de domínio não descobrem nem consomem outros serviços

---

## Restrições

- Comunicação direta entre serviços é proibida
- Serviços não devem acessar o Eureka manualmente
- O Eureka não deve se registrar como cliente
- Não há comunicação ponto-a-ponto entre serviços
