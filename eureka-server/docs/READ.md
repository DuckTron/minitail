# Eureka Server

Service Discovery do projeto **MiniTail**.

O **Eureka Server** é responsável exclusivamente por manter o registro das instâncias ativas dos serviços e disponibilizar essas informações para componentes de infraestrutura.  
Este componente **não contém lógica de negócio**.

---

## Responsabilidades

- Registro de instâncias de serviços
- Monitoramento de disponibilidade
- Remoção automática de instâncias indisponíveis

---

## Não responsabilidades

- Não contém lógica de negócio
- Não realiza balanceamento de carga
- Não implementa fallback
- Não expõe funcionalidades ao usuário final
- Não realiza comunicação direta entre serviços

---

## Escopo

- Execução em modo **single-node**
- Ambiente **Docker**
