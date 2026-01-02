# Integration

Este documento descreve como o **API Gateway** integra-se aos demais componentes do projeto **MiniTail**.  
O foco é esclarecer dependências, fluxos e limites de responsabilidade, não detalhar implementação.

---

## Integração com Eureka Server

O API Gateway utiliza o **Eureka Server** exclusivamente para **Service Discovery**.

Comportamento:
- O gateway registra-se automaticamente no Eureka durante o startup
- As instâncias dos serviços são resolvidas dinamicamente
- Não há rotas implícitas ou exposição automática de serviços

Requisitos:
- Eureka Server em estado **UP**
- Comunicação interna via rede Docker

Observação:
- O gateway depende do Eureka apenas para resolução de instâncias
- O Eureka não participa do fluxo de requisições

---

## Integração com Serviços de Negócio

O API Gateway é o **único ponto de entrada HTTP** para os serviços de negócio.

Comportamento:
- Requisições externas entram exclusivamente pelo gateway
- O roteamento é explícito e controlado
- Os serviços não se comunicam diretamente entre si

Padrão:
- Comunicação síncrona sempre via Gateway
- Comunicação assíncrona delegada à mensageria

Observação:
- Serviços não expõem portas externamente
- O gateway não contém lógica de domínio

---

## Integração com Mensageria

O API Gateway **não publica nem consome mensagens** diretamente.

Comportamento:
- A mensageria é utilizada apenas pelos serviços de negócio
- O gateway não participa de fluxos assíncronos

Justificativa:
- Evita acoplamento indevido
- Mantém o gateway focado em tráfego síncrono

---

## Integração com Resiliência e Fallback

O API Gateway centraliza políticas de resiliência.

Comportamento:
- Circuit breakers são aplicados por rota
- Fallbacks são internos ao gateway
- Não há substituição de lógica de negócio

Observação:
- O fallback protege o sistema contra falhas em cascata
- A degradação ocorre de forma controlada

---

## Integração com Observabilidade

O gateway expõe endpoints operacionais apenas internamente.

Comportamento:
- Actuator isolado por porta não publicada
- Endpoints operacionais não fazem parte da API pública

Observação:
- Observabilidade não é utilizada como canal de comunicação entre serviços

---

## Limitações Conhecidas

- O gateway não valida regras de negócio
- Não há autenticação implementada no gateway neste estágio
- A mensageria não é mediada pelo gateway
- As integrações foram validadas em ambiente Docker

---

## Resumo de Integração

- Gateway integra-se ao Eureka para discovery
- Gateway expõe serviços de forma controlada
- Gateway aplica resiliência centralizada
- Gateway não participa do domínio
- Gateway não cria dependências funcionais entre serviços
