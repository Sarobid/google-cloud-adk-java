# Configuration ADK

## Étape 1 : Vérifier l'installation de JAVA 17

- verification

```bash
java --version
```
- Si java 17 installer , sauter directement  dans l'etape 2
- Installation java 17 si non :

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```
-  Configurer la version par défaut

```bash
sudo update-alternatives --config java
```
*Saisissez le numéro correspondant et appuyez sur Entrée*
```bash
sudo update-alternatives --config javac
```
*Saisissez le numéro correspondant et appuyez sur Entrée*

- Revéfication du version java

