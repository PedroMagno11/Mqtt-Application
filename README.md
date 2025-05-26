# MQTT Application

> Um cliente MQTT simples e funcional desenvolvido em Java com JavaFX e Eclipse Paho MQTT.

## 📌 Descrição

Este projeto é uma aplicação desktop construída com **JavaFX** que permite:
- Conectar-se a um broker MQTT (com ou sem autenticação)
- Assinar tópicos
- Publicar mensagens
- Visualizar mensagens recebidas em tempo real
- Manter logs da comunicação
- Limpar os logs manualmente

---

## 🧰 Tecnologias Utilizadas

- Java 21
- JavaFX 21
- Eclipse Paho MQTT (v1.2.5)
- Maven

---

## ▶️ Como Executar

1. Certifique-se de ter o Java 21 instalado.
2. Clone o repositório:

```bash
git clone https://github.com/PedroMagno11/mqtt-application.git
cd mqtt-application
```

3. Execute com Maven:
```bash
mvn javafx:run
```

Ou, via IntelliJ:
- Marque o SDK como Java 21
- Configure a execução com `--module-path` e os jars do JavaFX

---

## 💡 Funcionalidades

- 🔌 **Conectar/Desconectar** ao broker (ex: Mosquitto)
- 📨 **Receber mensagens** em tempo real
- 📤 **Publicar mensagens** em qualquer tópico
- 🔍 Visualização de mensagens com histórico
- 🧹 **Botão de limpar log**
- 🛍 Evita ações incorretas (ex: publicar sem conectar ou sem tópico)

---

## ✅ Melhorias Futuras

- Suporte a múltiplas conexões simultâneas
- Criptografia SSL/TLS
- Histórico de mensagens com timestamp
- Filtro de tópicos e QoS
- Exportação dos logs

---

## 🧚 Exemplo de uso

1. Abra o programa
2. Digite:
    - `Server URI`: `localhost`
    - `Port`: `1883`
    - `Topic`: `test/topic`
3. Clique em **Conectar**
4. Publique algo no tópico e veja o resultado no log

---

## 🤝 Contribuições

Contribuições são bem-vindas! Para contribuir:

1. Fork este repositório
2. Crie sua branch: `git checkout -b minha-feature`
3. Commit suas mudanças: `git commit -m 'feat: minha nova feature'`
4. Envie para o repositório remoto: `git push origin minha-feature`
5. Abra um Pull Request

---

## 👤 Autor

**Pedro Magno**  
[GitHub](https://github.com/PedroMagno11)

---

## 📝 Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
