# Logs

## Script de Execução (execution/run.sh)

Saídas esperadas:
- Indicação clara de início do script
- Indicação de início e fim do build
- Indicação de sucesso ou falha do build
- Indicação de início da execução do container
- Indicação de encerramento do controle do script

Formato:
[evento/item] ::::: STATUS

Exemplo observado:
[script/gateway] ::::: START  
[build/gateway]  ::::: START  
[build/gateway]  ::::: SUCCESS  
[run/gateway]    ::::: START  
[script/gateway] ::::: END  

Observações:
- O STATUS SUCCESS é exibido em verde e negrito
- O STATUS FAIL é exibido em vermelho e negrito
- Apenas o STATUS recebe formatação visual
- O formato textual permanece íntegro para parsing automático

---

## Startup da Aplicação

Saídas esperadas:
- Inicialização do Spring Boot
- Inicialização do servidor web reativo (Netty)
- Inicialização do Service Discovery Client
- Registro do API Gateway no Eureka
- Aplicação em estado operacional

Exemplo observado:
Started ApiGatewayApplication in <tempo> seconds  
Registering application API-GATEWAY with eureka with status UP

---

## Runtime

Saídas esperadas:
- Gateway em execução contínua
- Resolução dinâmica de serviços via Eureka
- Processamento de requisições HTTP
- Atuação de circuit breakers quando aplicável

Exemplo observado:
Registered instance API-GATEWAY with status UP  
Netty started on port 8080 (http)

---

## Fallback e Resiliência

Saídas esperadas:
- Ativação de circuit breaker em caso de falha
- Redirecionamento interno para fallback configurado
- Resposta controlada ao cliente

Exemplo observado:
Auth service unavailable

Observações:
- O fallback é tratado internamente no gateway
- Não há propagação de exceções para o cliente
- Não há tentativa de substituição de lógica de negócio

---

## Shutdown

Saídas esperadas:
- Encerramento do processo Java
- Container finalizado sem erro explícito

Observações:
- O encerramento é realizado via Docker
- Não há log específico de shutdown emitido pela aplicação

---

## Observações Gerais

- O API Gateway não mantém estado persistente
- O comportamento é consistente entre reinicializações
- O gateway permanece operacional mesmo com serviços indisponíveis
- A degradação do sistema ocorre de forma controlada
