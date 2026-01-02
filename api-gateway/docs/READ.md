# API Gateway

Gateway de entrada do projeto **MiniTail**.

O **API Gateway** é responsável por centralizar todo o tráfego HTTP síncrono do sistema, atuando como **único ponto de entrada** para os serviços de negócio.  
Este componente **não contém lógica de domínio** e não implementa regras funcionais.

---

## Responsabilidades

- Centralizar o acesso HTTP aos serviços
- Realizar roteamento explícito de requisições
- Resolver instâncias via Service Discovery (Eureka)
- Aplicar políticas transversais (resiliência e fallback)
- Proteger o sistema contra falhas em cascata

---

## Não responsabilidades

- Não contém lógica de negócio
- Não executa autenticação ou regras de domínio
- Não realiza comunicação direta entre serviços de negócio
- Não substitui serviços indisponíveis
- Não inventa respostas funcionais
- Não expõe endpoints internos ou operacionais

---

## Escopo

- Único ponto de entrada HTTP do sistema
- Integração exclusiva com serviços registrados no Eureka
- Comunicação síncrona apenas via Gateway
- Comunicação assíncrona delegada à mensageria
- Execução em ambiente **Docker**
