calculator app by gemini

used apps: gemini-cli, git, keytool

create android calculator with kotlin, push to github then build with github action for apk signed file

keytool -genkey -v -keystore my-release-key.keystore -alias my-key-alias -keyalg RSA -keysize 2048 -validity 10000

base64 -w 0 your-key-file > your-key-base64.txt


