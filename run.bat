@echo off

:: Naviguer dans le répertoire source
cd "QCM Builder\src" || (
    echo Erreur : Impossible d'accéder au répertoire source.
    exit /b 1
)

:: Compilation
echo Compilation en cours...
javac -encoding utf8 -d ../bin @compil.list
if %ERRORLEVEL% NEQ 0 (
    echo Erreur lors de la compilation.
    exit /b 1
)
echo Compilation réussie.

:: Exécution
echo Lancement de l'application...
java -cp "../bin" Controlleur.Controlleur
if %ERRORLEVEL% NEQ 0 (
    echo Erreur lors de l'exécution.
    exit /b 1
)
