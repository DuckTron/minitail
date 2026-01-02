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
[script/eureka] ::::: START
[build/eureka]  ::::: START
[build/eureka]  ::::: SUCCESS
[run/eureka]    ::::: START
[script/eureka] ::::: END

Observações:
- O STATUS SUCCESS é exibido em verde e negrito
- O STATUS FAIL é exibido em vermelho e negrito
- Apenas o STATUS recebe formatação visual
- O formato textual permanece íntegro para parsing automático

---

## Startup da Aplicação

Saídas esperadas:
- Inicialização do Spring Boot
- Inicialização do servidor web (Tomcat)
- Inicialização do Eureka Server
- Aplicação em estado operacional

Exemplo observado:
Started EurekaServerApplication in <tempo> seconds

---

## Runtime

Saídas esperadas:
- Execução contínua do Eureka Server
- Execução periódica de tarefas internas (eviction)

Exemplo observado:
Running the evict task with compensationTime 0ms

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

- O Eureka Server não mantém estado persistente entre execuções
- O comportamento observado é consistente entre reinicializações
- O registro de instâncias não foi validado neste estágio
