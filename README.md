# Elgin Printer Bridge - ESC/POS

Bridge Android simples que recebe Intents do Fully Kiosk e imprime texto em impressoras Bluetooth compatíveis ESC/POS (testado com Elgin I9).

## Como funciona
- O Fully envia um Intent:
  - Action: `com.elgin.bridge.PRINT`
  - Extra: `text` (String) — texto a imprimir
  - Extra opcional: `mac` (String) — endereço MAC da impressora. Se omitido, o app tenta conectar à impressora pareada com nome que contenha `I9`.

## Como obter o APK pronto (sem Android Studio)
1. Crie um repositório no GitHub e suba este projeto.
2. Vá em Settings → Actions e habilite GitHub Actions (padrão).
3. No push para `main`, o workflow `.github/workflows/android-build.yml` fará `assembleRelease` e publicará o APK como artifact.
4. Baixe o APK nas Actions do repositório → Artifacts.

## Como usar no tablet
1. Habilite instalação de fontes desconhecidas.
2. Instale o APK.
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

