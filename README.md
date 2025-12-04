# Elgin Printer Bridge - ESC/POS

Bridge Android simples que recebe Intents do Fully Kiosk e imprime texto em impressoras Bluetooth compatíveis ESC/POS (testado com Elgin I9).

## Como funciona
- O Fully envia um Intent:
  - Action: `com.elgin.bridge.PRINT`
  - Extra: `text` (String) — texto a imprimir
  - Extra opcional: `mac` (String) — endereço MAC da impressora. Se omitido, o app tenta conectar à impressora pareada com nome que contenha `I9`.

## Gerar APK sem Android Studio (usando GitHub Actions)
1. Crie um repositório no GitHub com o nome **ElginPrinterBridge**.
2. Faça commit de todos os arquivos deste projeto na branch `main`.
3. O workflow `.github/workflows/android-build.yml` usa Gradle 8.2 e rodará automaticamente na chegada do push.
4. O artefato APK de debug estará disponível em Actions -> run -> Artifacts como `apks`.

## Como usar no tablet
1. Habilite instalação de fontes desconhecidas.
2. Instale o APK (debug) baixado do workflow.
3. Pareie a impressora Elgin I9 via **Configurações > Bluetooth** do Android.
4. Configure Fully Kiosk para carregar seu Start URL.
5. No seu web, chame:

```js
fully.sendIntent("com.elgin.bridge.PRINT", "text=Senha 001");
```

ou, enviando MAC (opcional):

```js
fully.sendIntent("com.elgin.bridge.PRINT", "text=Senha 001&mac=AA:BB:CC:11:22:33");
```
