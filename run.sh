#!/bin/bash

# Naviguer dans le répertoire source
cd "QCM Builder/src" || {
    echo "Erreur : Impossible d'accéder au répertoire source."
    exit 1
}

# Compilation
echo "Compilation en cours..."
javac -encoding utf8 -d ../bin @compil.list
if [ $? -ne 0 ]; then
    echo "Erreur lors de la compilation."
    exit 1
fi
echo "Compilation réussie."

# Exécution
echo "Lancement de l'application..."
java -cp "../bin" Controlleur.Controlleur
if [ $? -ne 0 ]; then
    echo "Erreur lors de l'exécution."
    exit 1
fi
