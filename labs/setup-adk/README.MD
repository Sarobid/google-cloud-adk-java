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

# Etape 4 : preparation api key

```bash
export GOOGLE_API_KEY="YOUR_API_KEY"'
```

# Etape 5 : lancer le projet

```bash
mvn exec:java -Dexec.mainClass="Main"
```

# DOCS : 

* interdit retour vers agent parent
```bash
disallow_transfer_to_parent=True
```

* interdit transfert vers agents frères
```bash
interdit transfert vers agents frères
```

## Temperature

* Température basse (0,0-0,3) - déterministe:
  * Tres coherent et  previsible 

* Température moyenne (0,4-0,7) - équilibré:
  * Équilibre entre créativité et cohérence

* Température élevée (0,8-1,0) - créativité :
  * Reponse varié et creative